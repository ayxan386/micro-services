package org.aykhan.dataprovider.service.impl;

import org.aykhan.dataprovider.client.ContentClient;
import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.user.UserRequest;
import org.aykhan.dataprovider.dto.user.UserResponse;
import org.aykhan.dataprovider.entity.UserDM;
import org.aykhan.dataprovider.mapper.UserMapper;
import org.aykhan.dataprovider.repository.UserDMRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.aykhan.dataprovider.UserPrincipalProvider.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

  @Autowired
  private UserServiceImpl service;
  @MockBean
  private UserDMRepository userRepository;
  @MockBean
  private ContentClient contentClient;
  @MockBean
  private UserMapper userMapper;
  @MockBean
  private UserPrincipialInjectorConfig injectorConfig;
  private UserDM userDM;
  private UserRequest userRequest;
  private UserResponse userResponse;

  @BeforeEach
  void setUp() {
    userDM = UserDM
        .builder()
        .nickname("aykhan")
        .name("Aykhan")
        .surname("Hasanov")
        .email("someone@nowhere.com")
        .id(1L)
        .profilePicture("#")
        .build();
    userRequest = UserRequest
        .builder()
        .nickname("aykhan")
        .name("Aykhan")
        .surname("Hasanov")
        .email("someone@nowhere.com")
        .profilePicture("#")
        .build();
    userResponse = UserResponse
        .builder()
        .nickname("aykhan")
        .name("Aykhan")
        .surname("Hasanov")
        .email("someone@nowhere.com")
        .profilePicture("#")
        .build();
    when(userMapper.modelToResponse(any())).thenReturn(userResponse);
    when(userRepository.save(any())).thenReturn(userDM);
    when(userRepository.getByNickname(any()))
        .thenReturn(Optional.of(userDM));
    when(userMapper.requestToModel(any())).thenReturn(userDM);
    when(injectorConfig.getUser()).thenReturn(USER);
  }

  @Test
  void get() {
    UserResponse userResponse = service.get(userRequest);
    assertThat(userResponse).isNotNull();
    assertThat(userResponse.getNickname()).isEqualTo(userRequest.getNickname());
  }

  @Test
  void add() {
    UserResponse userResponse = service.add(userRequest);
    assertThat(userResponse).isNotNull();
    assertThat(userResponse.getNickname()).isEqualTo(userRequest.getNickname());
  }

  @Test
  void update() {
    UserResponse userResponse = service.update(userRequest);
    assertThat(userResponse).isNotNull();
    assertThat(userResponse.getNickname()).isEqualTo(userRequest.getNickname());
  }

  @Test
  void delete() {
    doNothing().when(userRepository).deleteById(any());
    String response = service.delete(userRequest);
    assertThat(response).isNotBlank();
    assertThat(response).isEqualTo("success");
  }

  @Test
  void clearCache() {
    service.clearCache(userRequest);
    assertThat(userResponse).isNotNull();
  }

  @Test
  void savePicture() {
    when(contentClient.addFile(any(), any())).thenReturn(
        ResponseEntity.of(Optional.of("http://localhost:6666")));
    String s = service.savePicture(new MockMultipartFile("name.png", "content".getBytes()));
    assertThat(s).isNotBlank();
    assertThat(s).isEqualTo("success");
  }
}