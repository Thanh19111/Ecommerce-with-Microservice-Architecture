package com.thanhpham.User.mapper;

import ch.qos.logback.core.util.StringUtil;
import com.thanhpham.User.dto.request.UserCreateRequest;
import com.thanhpham.User.dto.request.UserUpdateRequest;
import com.thanhpham.User.entity.User;

public class UserMapper {
    public static void toEntity(User user ,UserUpdateRequest request)
    {
        if(StringUtil.notNullNorEmpty(request.getFullName()))
        {
            user.setFullName(request.getFullName());
        }

        if(StringUtil.notNullNorEmpty(request.getAddress()))
        {
            user.setAddress(request.getAddress());
        }

        if(request.getDateOfBirth() != null)
        {
            user.setDateOfBirth(request.getDateOfBirth());
        }

        if(StringUtil.notNullNorEmpty(request.getGender().toString()))
        {
            user.setGender(request.getGender());
        }

        if(StringUtil.notNullNorEmpty(request.getAvatarUrl()))
        {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        if(StringUtil.notNullNorEmpty(request.getPhoneNumber()))
        {
            user.setPhoneNumber(request.getPhoneNumber());
        }
    }

    public static User toEntity(UserCreateRequest request){
        return User.builder()
                .id(request.getId())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .fullName(request.getFullName())
                .avatarUrl(request.getAvatarUrl())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender()).build();
    }
}
