package com.thanhpham.User.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "This schema to hold error response information"
)
@Builder
public class ListErrorResponse<T> {
    @Schema(
            description = "API path invoked by client",
            example = "api/v1/users/register"
    )
    private String apiPath;
    @Schema(
            description = "Error code representing the error happened",
            example = "500"
    )
    private Integer errorCode;
    @Schema(
            description = "Error message representing the error happened",
            example = "User id not found"
    )
    private T errorMessage;
    @Schema(
            description = "Time representing the error happened",
            example = "2025-05-27T13:37:42.2469606"
    )
    private LocalDateTime errorTime;
}