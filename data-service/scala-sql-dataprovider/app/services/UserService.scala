package services

import com.google.inject.ImplementedBy
import dtos.{UserRequest, UserResponseDTO}
import services.impl.UserServiceImpl

import scala.concurrent.Future

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {

  def add(req: UserRequest): Future[UserResponseDTO]

  def getByNickname(username: String): Future[UserResponseDTO]
}
