package services

import dtos.UserResponseDTO

import scala.concurrent.Future

trait UserService {
  def getByNickname(username: String): Future[UserResponseDTO]
}
