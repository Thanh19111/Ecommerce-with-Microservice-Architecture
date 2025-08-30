package com.thanhpham.User.dto.request;

import com.thanhpham.User.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {

    @Pattern(regexp = "^$|^(?!\\s)\\p{L}+( \\p{L}+)*(?<!\\s)$", message = "Invalid full name")
    @Size(max = 50, min = 1, message = "Full name must be 50 characters or fewer")
    @Schema(description = "Full Name", example = "Pham Van Thanh")
    private String fullName;

    @Pattern(regexp = "^$|^0[0-9]{9}$", message = "Invalid phone number")
    @Schema(description = "Phone Number", example = "0826573341")
    private String phoneNumber;

    @Schema(description = "Avatar Url", example = "https://cdn-media.sforum.vn/storage/app/media/wp-content/uploads/2024/02/anh-phong-canh-66-1.jpg")
    @Pattern(
            regexp = "^$|^(https?://).+\\.(png|jpg|jpeg|gif|bmp)$",
            message = "Avatar URL must be a valid image URL (png, jpg, jpeg, gif, bmp)"
    )
    private String avatarUrl;

    @Schema(description = "Gender", example = "MALE")
    private Gender gender;

    @Pattern(regexp = "^$|^(?!\\s)[\\p{L}0-9\\s,./\\-]{5,100}(?<!\\s)", message = "Invalid address")
    @Schema(description = "Address", example = "123/5B đường Lê Lợi, Phường 6, thành phố Tuy Hòa, tỉnh Phú Yên")
    private String address;

    @Schema(description = "Date of Birth", example = "2000-01-01")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

}
