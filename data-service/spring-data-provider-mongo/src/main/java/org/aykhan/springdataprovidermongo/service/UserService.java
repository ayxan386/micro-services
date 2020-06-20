package org.aykhan.springdataprovidermongo.service;

import org.aykhan.springdataprovidermongo.dto.user.UserRequest;
import org.aykhan.springdataprovidermongo.dto.user.UserResponse;

public interface UserService extends CrudService<UserRequest, UserResponse> {
    void clearCache(UserRequest request);
}
