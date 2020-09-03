package services

import com.google.inject.ImplementedBy
import dtos.user.{UserRequest, UserResponseDTO}
import javax.annotation.meta.TypeQualifierNickname
import play.api.libs.Files
import play.api.mvc.MultipartFormData
import services.impl.UserServiceImpl

import scala.concurrent.Future

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {
  def updateProfile(file: MultipartFormData.FilePart[Files.TemporaryFile],
                    nickname: Option[String]): Future[String]

  def delete(req: UserRequest): Future[String]

  def update(req: UserRequest): Future[UserResponseDTO]

  def add(req: UserRequest): Future[UserResponseDTO]

  def getByNickname(username: String): Future[UserResponseDTO]

  def getById(id: Long): Future[UserResponseDTO]
}
