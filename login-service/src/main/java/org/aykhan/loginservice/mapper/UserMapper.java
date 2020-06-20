package org.aykhan.loginservice.mapper;

import org.aykhan.loginservice.dto.LoginDTO;
import org.aykhan.loginservice.dto.RegisterRequest;
import org.aykhan.loginservice.entity.MyUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
public interface UserMapper {
    MyUserDetails loginRequestToEntity(LoginDTO loginDTO);

    @Mapping(target = "roles", source = "roles")
    MyUserDetails registerRequestToEntity(RegisterRequest registerRequest);
}
