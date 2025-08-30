package com.thanhpham.User.dto.response;

import com.thanhpham.User.entity.Gender;
import com.thanhpham.User.entity.Status;
import com.thanhpham.User.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {

    @Schema(description = "Full Name", example = "Pham Van Thanh")
    private String fullName;

    @Schema(description = "Phone Number", example = "0826573341")
    private String phoneNumber;

    @Schema(description = "Address", example = "123/5B đường Lê Lợi, Phường 6, thành phố Tuy Hòa, tỉnh Phú Yên")
    private String address;

    private Status status;

    @Schema(description = "Avatar Url", example = "https://cdn-media.sforum.vn/storage/app/media/wp-content/uploads/2024/02/anh-phong-canh-66-1.jpg")
    private String avatarUrl;

    @Schema(description = "Gender", example = "MALE")
    private Gender gender;

    @Schema(description = "Date of Birth", example = "2000-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "Updated At", example = "2025-05-26T20:21:34.192086")
    private LocalDateTime updatedAt;

    public static UserResponse fromEntity(User user){
        return new UserResponse(
                user.getFullName(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getStatus(),
                user.getAvatarUrl(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getUpdatedAt()
        );
    }
}
