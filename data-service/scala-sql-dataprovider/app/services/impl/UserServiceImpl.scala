package services.impl

import java.nio.file.Files

import akka.stream.scaladsl.{FileIO, Source}
import dtos.user.{UserRequest, UserResponseDTO}
import error.UserNotFoundException
import javax.inject.{Inject, Singleton}
import models.User
import play.api.Logger
import play.api.libs.ws.WSClient
import play.api.mvc.MultipartFormData
import repositories.UserRepository
import services.UserService
import com.typesafe.config.ConfigFactory
import play.api.libs.Files.TemporaryFile
import play.api.libs.Files.TemporaryFile.temporaryFileToPath
import play.api.mvc.MultipartFormData.{DataPart, FilePart}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserServiceImpl @Inject()(userRepository: UserRepository, ws: WSClient)(
    implicit ex: ExecutionContext)
    extends UserService {

  val logger: Logger = Logger(this.getClass)
  val contentUrl: String = ConfigFactory.load.getString("ms.urls.content")

  override def getByNickname(username: String): Future[UserResponseDTO] = {
    logger.info(s"getting user with $username nickname")
    userRepository
      .findFirstByNickname(username)
      .map(op => op.getOrElse(throw UserNotFoundException()))
      .map(userToResponseDTO)
  }

  override def add(req: UserRequest): Future[UserResponseDTO] = {
    logger.info(s"adding new user with ${req.nickname} nickname")
    userRepository
      .save(requestToDM(req))
      .map(dm => userToResponseDTO(dm))
  }

  override def update(req: UserRequest): Future[UserResponseDTO] = {
    userRepository
      .findFirstByNickname(req.nickname)
      .map(op => op.getOrElse(throw UserNotFoundException()))
      .map(user => copyRequestOntoDM(req, user))
      .map(user => userRepository.update(user))
      .flatMap(f => f)
      .map(userToResponseDTO)
  }

  override def delete(req: UserRequest): Future[String] =
    userRepository.deleteByNickname(req.nickname)

  def requestToDM(req: UserRequest): User =
    User(id = -1,
         email = req.email,
         name = req.name,
         nickname = req.nickname,
         profilePicture = req.profilePicture,
         surname = req.surname)

  def copyRequestOntoDM(req: UserRequest, user: User): User =
    user.copy(email = req.email,
              name = req.name,
              profilePicture = req.profilePicture,
              surname = req.surname)

  def userToResponseDTO(user: User): UserResponseDTO =
    UserResponseDTO(name = user.name,
                    email = user.email,
                    surname = user.surname,
                    profilePicture = user.profilePicture,
                    nickname = user.nickname)

  override def getById(id: Long): Future[UserResponseDTO] = {
    userRepository
      .findFirstById(id)
      .map(userToResponseDTO)
  }

  override def updateProfile(file: MultipartFormData.FilePart[TemporaryFile],
                             nickname: Option[String]): Future[String] =
    nickname
      .map(userRepository.findFirstByNickname)
      .getOrElse(throw UserNotFoundException())
      .map(a => a.getOrElse(throw UserNotFoundException()))
      .map(
        user =>
          ws.url(contentUrl + "/add")
            .post(
              Source(
                FilePart(
                  "file",
                  s"${user.nickname}_profile_picture",
                  file.contentType,
                  FileIO.fromPath(temporaryFileToPath(file.ref))) :: DataPart(
                  "name",
                  s"${user.nickname}_profile_picture"
                ) :: List()
              ))
            .map(res => res.body)
            .map(url => user.copy(profilePicture = Some(url)))
            .map(user => userRepository.update(user))
            .flatMap(f => f)
            .map(u => "success"))
      .flatMap(f => f)
}
