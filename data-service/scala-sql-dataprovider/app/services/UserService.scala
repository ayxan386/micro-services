package services

import com.google.inject.ImplementedBy
import dtos.user.{UserRequest, UserResponseDTO}
import services.impl.UserServiceImpl

import scala.concurrent.Future

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {
  def delete(req: UserRequest): Future[String]

  def update(req: UserRequest): Future[UserResponseDTO]

  def add(req: UserRequest): Future[UserResponseDTO]

  def getByNickname(username: String): Future[UserResponseDTO]

  def getById(id: Long): Future[UserResponseDTO]
}
