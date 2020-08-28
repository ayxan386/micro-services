package services.impl

import dtos.{UserRequest, UserResponseDTO}
import models.User
import org.mockito.ArgumentMatchers.{any, anyString}
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

import scala.concurrent.ExecutionContext.Implicits.global
import repositories.UserRepository
import services.UserService

import scala.concurrent.Future

class UserServiceImplTest extends PlaySpec with MockitoSugar {

  val mockUser: User = User(id = 1,
                            email = Some("test@tester.com"),
                            name = Some("Test"),
                            nickname = "test",
                            profilePicture = Some("#"),
                            surname = Some("Tester"))

  val mockUserResponse: UserResponseDTO =
    UserResponseDTO(email = Some("test@tester.com"),
                    name = Some("Test"),
                    nickname = "test",
                    profilePicture = Some("#"),
                    surname = Some("Tester"))

  val mockUserRequest: UserRequest =
    UserRequest(email = Some("test@tester.com"),
                name = Some("Test"),
                nickname = "test",
                profilePicture = Some("#"),
                surname = Some("Tester"))

  "UserServiceImpl" must {
    val userRepository = mock[UserRepository]
    "be correctly initialized" in {
      val service = getInstance(userRepository)

      service.isInstanceOf[UserServiceImpl] mustBe true
      service.isInstanceOf[UserService] mustBe true
    }
    "return user response when getByNickname is called with existing username" in {
      when(userRepository.findFirstByNickname(anyString))
        .thenReturn(Future.successful(Some(mockUser)))
      val service = getInstance(userRepository)
      val res = service.getByNickname(mockUserResponse.nickname)
      res.map(f => Option(f).isDefined mustBe true)
      res.map(f => f.nickname mustBe mockUserResponse.nickname)
      res.map(f => f.nickname mustBe mockUser.nickname)
    }
    "save user request when add is called" in {
      when(userRepository.save(any))
        .thenReturn(Future.successful(mockUser))
      val service = getInstance(userRepository)
      val res = service.add(mockUserRequest)
      res.map(f => Option(f).isDefined mustBe true)
      res.map(f => f.nickname mustBe mockUser.nickname)
    }
    "update user when update is called" in {
      when(userRepository.save(any))
        .thenReturn(Future.successful(mockUser))
      when(userRepository.findFirstByNickname(anyString))
        .thenReturn(Future.successful(Some(mockUser)))
      val service = getInstance(userRepository)
      val res = service.update(mockUserRequest)
      res.map(f => Option(f).isDefined mustBe true)
      res.map(f => f.nickname mustBe mockUser.nickname)
    }
    "delete user when delete is called" in {
      when(userRepository.deleteByNickname(any))
        .thenReturn(Future.successful("success"))
      val service = getInstance(userRepository)
      val res = service.delete(mockUserRequest)
      res.map(f => Option(f).isDefined mustBe true)
      res.map(f => f mustBe "success")
    }
  }

  private def getInstance(userRepository: UserRepository) =
    new UserServiceImpl(userRepository)
}
