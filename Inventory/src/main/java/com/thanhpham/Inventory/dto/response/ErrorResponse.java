package com.thanhpham.Inventory.dto.response;

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
public class ErrorResponse {
    @Schema(
            description = "API path invoked by client"
    )
    private String apiPath;
    @Schema(
            description = "Error code representing the error happened"
    )
    private Integer errorCode;
    @Schema(
            description = "Error message representing the error happened"
    )
    private String errorMessage;
    @Schema(
            description = "Time representing the error happened"
    )
    private LocalDateTime errorTime;
}