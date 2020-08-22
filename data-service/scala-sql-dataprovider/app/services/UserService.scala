package services

import com.google.inject.ImplementedBy
import dtos.UserResponseDTO
import services.impl.UserServiceImpl

import scala.concurrent.Future

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {
  def getByNickname(username: String): Future[UserResponseDTO]
}
