package org.example.apiservice.mapper;

import org.example.apiservice.model.response.AuthResponse;
import org.example.apiservice.model.response.UserDetailResponse;
import org.example.apiservice.model.response.UserResponse;
import org.example.commonservice.core.entity.UserEntity;
import org.mapstruct.*;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface AuthControllerMapper {

    AuthResponse from(UserEntity response);

    @Mapping(target = "phone", source = "phoneNumber")
    UserResponse to(UserEntity response);

    UserDetailResponse view(UserEntity response);


}
