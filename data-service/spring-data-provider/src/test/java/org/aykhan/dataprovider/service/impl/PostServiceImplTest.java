package org.aykhan.dataprovider.service.impl;

import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.post.PostRequest;
import org.aykhan.dataprovider.dto.post.PostResponse;
import org.aykhan.dataprovider.dto.user.UserResponse;
import org.aykhan.dataprovider.entity.CommentsDM;
import org.aykhan.dataprovider.entity.PostDM;
import org.aykhan.dataprovider.entity.UserDM;
import org.aykhan.dataprovider.mapper.PostMapper;
import org.aykhan.dataprovider.repository.PostDMRepository;
import org.aykhan.dataprovider.repository.UserDMRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.aykhan.dataprovider.UserPrincipalProvider.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PostServiceImplTest {
  @Autowired
  private PostServiceImpl service;

  @MockBean
  private PostDMRepository postDMRepository;
  @MockBean
  private PostMapper postMapper;
  @MockBean
  private UserPrincipialInjectorConfig userInjector;
  @MockBean
  private UserDMRepository userDMRepository;
  private PostDM postDM;
  private UserDM userDM;
  private PostRequest postRequest;

  @Before
  public void setUp() throws Exception {
    userDM = UserDM
        .builder()
        .nickname("aykhan")
        .name("Aykhan")
        .surname("Hasanov")
        .email("someone@nowhere.com")
        .id(1L)
        .profilePicture("#")
        .build();
    postDM = PostDM
        .builder()
        .author(userDM)
        .body("Hello World!")
        .id(1L)
        .title("Greetings")
        .build();
    CommentsDM commentsDM = CommentsDM
        .builder()
        .author(userDM)
        .id(1L)
        .body("HI")
        .post(postDM)
        .build();
    postDM.setComments(singletonList(commentsDM));
    postRequest = PostRequest
        .builder()
        .body("Hello World!")
        .id(1L)
        .title("Greetings")
        .build();
    UserResponse userResponse = UserResponse.builder().nickname("aykhan")
        .name("Aykhan")
        .surname("Hasanov")
        .email("someone@nowhere.com")
        .profilePicture("#").build();
    PostResponse postResponse = PostResponse
        .builder()
        .author(userResponse)
        .body("Hello World!")
        .id(1L)
        .title("Greetings")
        .build();
    when(postMapper.postDMtoResponse(any())).thenReturn(postResponse);
    when(postMapper.postReqToDM(any())).thenReturn(postDM);
    when(userDMRepository.getByNickname(anyString())).thenReturn(ofNullable(userDM));
    when(userInjector.getUser()).thenReturn(USER);
    when(postDMRepository.findById(any())).thenReturn(ofNullable(postDM));
  }

  @Test
  public void get() {
    PostResponse response = service.get(postRequest);
    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo(postRequest.getId());
  }

  @Test
  public void add() {
    when(postDMRepository.save(any())).thenReturn(postDM);
    PostResponse response = service.add(postRequest);
    assertThat(response).isNotNull();
    assertThat(response.getBody()).isEqualTo(postRequest.getBody());
  }

  @Test
  public void update() {
    when(postDMRepository.save(any())).thenReturn(postDM);
    PostResponse response = service.update(postRequest);
    assertThat(response).isNotNull();
    assertThat(response.getBody()).isEqualTo(postRequest.getBody());
  }

  @Test
  public void delete() {
    doNothing().when(postDMRepository).delete(any());
    String response = service.delete(postRequest);
    assertThat(response).isNotEmpty();
    assertThat(response).isEqualTo("success");
  }

  @Test
  public void getAll() {
    when(postDMRepository.findAllByAuthor_Nickname(anyString())).thenReturn(singletonList(postDM));
    List<PostResponse> response = service.getAll();
    assertThat(response).isNotEmpty();
  }
}