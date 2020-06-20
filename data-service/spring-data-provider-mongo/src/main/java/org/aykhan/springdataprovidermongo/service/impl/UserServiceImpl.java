package org.aykhan.springdataprovidermongo.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.springdataprovidermongo.dto.user.UserRequest;
import org.aykhan.springdataprovidermongo.dto.user.UserResponse;
import org.aykhan.springdataprovidermongo.entity.UserDM;
import org.aykhan.springdataprovidermongo.exception.notfound.UserNotFound;
import org.aykhan.springdataprovidermongo.mapper.UserMapper;
import org.aykhan.springdataprovidermongo.repository.UserDMRepository;
import org.aykhan.springdataprovidermongo.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = {"users"})
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDMRepository userRepository;
    private final UserMapper userMapper;

    @Cacheable(key = "#request.nickname")
    @Override
    public UserResponse get(UserRequest request) {
        log.info("loading from database");
        return userMapper.modelToResponse(userRepository
                .findByNickname(request.getNickname())
                .orElseThrow(UserNotFound::new)
        );
    }

    @Cacheable(key = "#request.nickname")
    @Override
    public UserResponse add(UserRequest request) {
        log.info("loading to database");
        return userMapper.modelToResponse(userRepository.save(
                userMapper.requestToModel(request)));
    }

    @CachePut(key = "#request.nickname")
    @Override
    public UserResponse update(UserRequest request) {
        log.info("updating database");
        UserDM userDM = userMapper.requestToModel(request);
        userRepository
                .findByNickname(request.getNickname())
                .ifPresent(model -> userDM.setId(model.getId()));
        return userMapper.modelToResponse(userRepository.save(userDM));
    }

    @Override
    public String delete(UserRequest request) {
        log.info("deleting from database");
        String id = userRepository
                .findByNickname(request.getNickname())
                .map(UserDM::getId)
                .orElseThrow(UserNotFound::new);
        userRepository.deleteById(id);
        clearCache(request);
        return "success";
    }

    @CacheEvict(key = "#request.nickname")
    public void clearCache(UserRequest request) {
    }
}
