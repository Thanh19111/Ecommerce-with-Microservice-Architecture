package com.thanhpham.User.controller;

import com.thanhpham.User.dto.request.*;
import com.thanhpham.User.dto.response.APIResponse;
import com.thanhpham.User.dto.response.ErrorResponse;
import com.thanhpham.User.dto.response.UserResponse;
import com.thanhpham.User.service.imp.UserService;
import com.thanhpham.User.wrapper.ListUserResponseWrapper;
import com.thanhpham.User.wrapper.UserResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register user profile")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fail",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<APIResponse<UserResponse>> createAUser(@RequestBody @Valid UserCreateRequest request)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(APIResponse.<UserResponse>builder()
                        .message("Success")
                        .result(userService.createUserProfile(request))
                        .build());
    }

    @Operation(summary = "Get my profile")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fail",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/my-info")
    public ResponseEntity<APIResponse<UserResponse>> getMyInfo(@RequestHeader("X-User-Id")
                                                                   @NotBlank(message = "User id cannot be blank")
                                                                   String userId)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<UserResponse>builder()
                        .message("Success")
                        .result(userService.getCurrentUserProfile(userId))
                        .build());
    }

    @Operation(summary = "Update my profile")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fail",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping("/update/me")
    public ResponseEntity<APIResponse<UserResponse>> updateMyProfile(
            @RequestHeader("X-User-Id")
            @NotBlank(message = "User id cannot be blank")
            String userId,
            @Valid @RequestBody UserUpdateRequest request) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<UserResponse>builder()
                        .message("Success")
                        .result(userService.updateCurrentUserProfile(userId, request))
                        .build());
    }

    @Operation(summary = "Get user profile by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fail",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse<UserResponse>> getUserById(@PathVariable
                                                                     @NotBlank(message = "User id cannot be blank")
                                                                     String userId)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<UserResponse>builder()
                        .message("Success")
                        .result(userService.getUserById(userId))
                        .build());
    }


    @Operation(summary = "Deactivate a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping("/deactivate")
    public ResponseEntity<APIResponse<String>> deactivateAccount(@RequestBody @Valid DeactivateUserRequest request)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(userService.deactivateAccount(request.getUserId()))
                        .build());
    }

    @Operation(summary = "Ban a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fail",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @PutMapping("/ban")
    public ResponseEntity<APIResponse<String>> banAccount(@RequestBody @Valid BanUserRequest request)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(userService.banAccount(request.getUserId()))
                        .build());
    }

    @Operation(summary = "Search user profile")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Fail",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping(value = "/search", params = {"keyword","value"})
    public ResponseEntity<APIResponse<List<UserResponse>>> searchUsers(@RequestParam("keyword")
                                                                           @NotBlank(message = "Keyword cannot be blank")
                                                                           @Pattern(regexp = "^[A-Za-z0-9]{1,20}$", message = "Keyword can only be numbers or letters with a length of 1 to 20 characters")
                                                                           String keyword, @RequestParam("value")
                                                                            @NotBlank(message = "Value cannot be blank")
                                                                            @Size(max = 50, message = "Value must be 50 characters or fewer")
                                                                            @Pattern(regexp = "^\\p{L}+( \\p{L}+)*$|^0[0-9]{9}$", message = "Value can only be numbers or letters with a length of 1 to 50 characters")
                                                                            String value)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<List<UserResponse>>builder()
                        .message("Success")
                        .result(userService.searchUsers(keyword, value))
                        .build());
    }

    @Operation(summary = "Get my profile")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListUserResponseWrapper.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })

    @GetMapping(value = "/search", params = "phone")
    public ResponseEntity<APIResponse<UserResponse>> searchUsers(@RequestParam("phone")
                                                                     @Pattern(regexp = "^0[0-9]{9}$", message = "Invalid phone number")
                                                                     @NotBlank(message = "phoneNumber is required")
                                                                     String phone)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<UserResponse>builder()
                        .message("Success")
                        .result(userService.findUserByPhoneNumber(phone))
                        .build());
    }

    //Endpoint này chỉ được dùng trong Order Service để kiểm tra user có tồn tại không ?
    @Operation(summary = "User existed")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Exception",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/exist")
    public ResponseEntity<Boolean> doesUserExist(@RequestParam("userId")
                                                     @NotBlank(message = "User id cannot be blank")
                                                     String userId)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.doesUserExist(userId));
    }

}
