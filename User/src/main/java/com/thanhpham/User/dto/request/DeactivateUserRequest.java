package com.thanhpham.User.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeactivateUserRequest {
    @NotBlank(message = "User id cannot be blank")
    @Schema(description = "User ID", example = "12345")
    private String userId;
}
