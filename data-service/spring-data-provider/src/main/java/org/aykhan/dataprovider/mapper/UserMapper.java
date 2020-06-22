package org.aykhan.dataprovider.mapper;

import org.aykhan.dataprovider.dto.user.UserRequest;
import org.aykhan.dataprovider.dto.user.UserResponse;
import org.aykhan.dataprovider.entity.UserDM;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface UserMapper {
  UserResponse modelToResponse(UserDM model);

  UserDM requestToModel(UserRequest request);
}
