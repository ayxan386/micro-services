package org.aykhan.dataprovider.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.client.ContentClient;
import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.user.UserRequest;
import org.aykhan.dataprovider.dto.user.UserResponse;
import org.aykhan.dataprovider.entity.UserDM;
import org.aykhan.dataprovider.exception.notfound.UserNotFound;
import org.aykhan.dataprovider.mapper.UserMapper;
import org.aykhan.dataprovider.repository.UserDMRepository;
import org.aykhan.dataprovider.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = {"users"})
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserDMRepository userRepository;
  private final ContentClient contentClient;
  private final UserMapper userMapper;
  private final UserPrincipialInjectorConfig injectorConfig;

  @Cacheable(key = "#request.nickname.hashCode()")
  @Override
  public UserResponse get(UserRequest request) {
    log.info("loading from database");
    return userMapper.modelToResponse(userRepository
        .getByNickname(request.getNickname())
        .orElseThrow(UserNotFound::new)
    );
  }

  @Override
  @CachePut(key = "#request.nickname.hashCode()")
  public UserResponse add(UserRequest request) {
    log.info("loading to database");
    return userMapper.modelToResponse(userRepository.save(
        userMapper.requestToModel(request)));
  }

  @CachePut(key = "#request.nickname.hashCode()")
  @Override
  public UserResponse update(UserRequest request) {
    log.info("updating database");
    UserDM userDM = userMapper.requestToModel(request);
    userRepository
        .getByNickname(request.getNickname())
        .ifPresent(model -> userDM.setId(model.getId()));
    return userMapper.modelToResponse(userRepository.save(userDM));
  }

  @Override
  public String delete(UserRequest request) {
    log.info("deleting from database");
    clearCache(request);
    Long id = userRepository
        .getByNickname(request.getNickname())
        .map(UserDM::getId)
        .orElse(-1L);
    userRepository.deleteById(id);
    return "success";
  }

  @CacheEvict(key = "#request.nickname.hashCode()")
  public void clearCache(UserRequest request) {
    //Trigger methods does not need a body
  }

  @Override
  public String savePicture(MultipartFile file) {
    String name = injectorConfig.getUser().getUsername() + "_profile_picture";
    String url = contentClient.addFile(file, name).getBody();
    userRepository
        .getByNickname(injectorConfig.getUser().getUsername())
        .ifPresent(dm -> {
          dm.setProfilePicture(url);
          userRepository.save(dm);
          //TODO figure better caching method
          clearCache(UserRequest.builder().nickname(injectorConfig.getUser().getUsername()).build());
        });
    return "success";
  }
}
