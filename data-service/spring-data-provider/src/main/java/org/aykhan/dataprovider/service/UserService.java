package org.aykhan.dataprovider.service;

import org.aykhan.dataprovider.dto.user.UserRequest;
import org.aykhan.dataprovider.dto.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends CrudService<UserRequest, UserResponse> {
  void clearCache(UserRequest request);

  String savePicture(MultipartFile file);
}
