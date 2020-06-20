package org.aykhan.springdataprovidermongo.mapper;

import org.aykhan.springdataprovidermongo.dto.user.UserRequest;
import org.aykhan.springdataprovidermongo.dto.user.UserResponse;
import org.aykhan.springdataprovidermongo.entity.UserDM;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface UserMapper {
    UserResponse modelToResponse(UserDM model);

    UserDM requestToModel(UserRequest request);
}
