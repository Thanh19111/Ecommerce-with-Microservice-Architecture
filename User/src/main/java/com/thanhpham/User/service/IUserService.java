package com.thanhpham.User.service;

import com.thanhpham.User.dto.request.UserCreateRequest;
import com.thanhpham.User.dto.request.UserUpdateRequest;
import com.thanhpham.User.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    UserResponse getUserById(String id);

    UserResponse createUserProfile(UserCreateRequest request);

    UserResponse getCurrentUserProfile(String userId);

    UserResponse updateCurrentUserProfile(String userId, UserUpdateRequest request);

    String uploadAvatar(String userId, MultipartFile avatar);

    String deactivateAccount(String userId);

    String banAccount(String userId);

    List<UserResponse> searchUsers(String keyword, String value);

    UserResponse findUserByPhoneNumber(String phoneNumber);
    boolean doesUserExist(String userId);
}
