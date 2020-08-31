package services.impl

import dtos.user.{UserRequest, UserResponseDTO}
import error.UserNotFoundException
import javax.inject.{Inject, Singleton}
import models.User
import play.api.Logger
import repositories.UserRepository
import services.UserService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserServiceImpl @Inject()(userRepository: UserRepository)(
    implicit ex: ExecutionContext)
    extends UserService {

  val logger: Logger = Logger(this.getClass)

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
}
