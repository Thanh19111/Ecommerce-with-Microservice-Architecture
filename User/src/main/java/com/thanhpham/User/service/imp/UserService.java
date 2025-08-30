package com.thanhpham.User.service.imp;

import com.thanhpham.User.dto.request.UserCreateRequest;
import com.thanhpham.User.dto.request.UserUpdateRequest;
import com.thanhpham.User.dto.response.UserResponse;
import com.thanhpham.User.entity.Status;
import com.thanhpham.User.entity.User;
import com.thanhpham.User.exception.BadRequestException;
import com.thanhpham.User.exception.ResourceNotFoundException;
import com.thanhpham.User.exception.UserAlreadyExistException;
import com.thanhpham.User.mapper.UserMapper;
import com.thanhpham.User.repository.UserRepository;
import com.thanhpham.User.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createUserProfile(UserCreateRequest request) {

        boolean exists = userRepository.existsByPhoneNumber(request.getPhoneNumber());
        if(exists){
            throw new UserAlreadyExistException("The user registered with the phone number is: " + request.getPhoneNumber());
        }

        User user = UserMapper.toEntity(request);
        user.setStatus(Status.ACTIVE);
        user = userRepository.save(user);
        return UserResponse.fromEntity(user);
    }

    @Transactional
    @Override
    public UserResponse getCurrentUserProfile(String userId) {
        User user = findUserById(userId);
        return UserResponse.fromEntity(user);
    }

    @Override
    @Transactional
    public UserResponse updateCurrentUserProfile(String userId, UserUpdateRequest request) {
        User user = findUserById(userId);

        boolean exist = userRepository.existsByPhoneNumber(request.getPhoneNumber());
        if(exist){
            throw new BadRequestException("The phone number was registered by other");
        }

        UserMapper.toEntity(user,request);
        userRepository.save(user);
        return UserResponse.fromEntity(user);
    }

    @Override
    public String uploadAvatar(String userId, MultipartFile avatar) {
        return "";
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> searchUsers(String keyword, String value) {
        keyword = keyword.toLowerCase();
        List<UserResponse> list = new ArrayList<>();
        if(keyword.contains("name"))
        {
            list = userRepository.searchByName(value).stream().map(UserResponse::fromEntity).toList();
        }
        else if (keyword.contains("phone") || keyword.contains("mobile") ) {
            Optional<User> user = userRepository.findByPhoneNumber(value);
            if (user.isPresent())
            {
                list.add(UserResponse.fromEntity(user.get()));
            }
        }
        return list;
    }

    @Override
    @Transactional
    public String deactivateAccount(String userId) {

        User user = findUserById(userId);

        if(!user.getStatus().equals(Status.INACTIVE)){
            user.setStatus(Status.INACTIVE);
            userRepository.save(user);
            return String.format("User ID: %d has been deactivated successfully", userId);
        }
        return String.format("User ID: %d has been deactivated before", userId);
    }

    @Override
    @Transactional
    public String banAccount(String userId) {
        User user = findUserById(userId);
        if(!user.getStatus().equals(Status.BANNED) && !user.getStatus().equals(Status.INACTIVE))
        {
            user.setStatus(Status.BANNED);
            userRepository.save(user);
            return String.format("User ID: %s has been banned successfully", userId);
        }
        return String.format("User ID: %s has been banned or deactivated before", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(String id)
    {
        User user = findUserById(id);
        return UserResponse.fromEntity(user);
    }

    @Transactional(readOnly = true)
    private User findUserById(String id)
    {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","User ID",id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUserByPhoneNumber(String phoneNumber){
        return UserResponse.fromEntity(
                userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                        () -> new ResourceNotFoundException("User", "phoneNumber", phoneNumber)
                )
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean doesUserExist(String userId){
        return userRepository.existsById(userId);
    }
}
