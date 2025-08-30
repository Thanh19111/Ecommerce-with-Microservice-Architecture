package com.thanhpham.Product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "This schema to hold successful response information"
)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class APIResponse<T> {
    @Schema(
            description = "Status code in the response"
    )
    @Builder.Default
    private Integer statusCode = 200;

    @Schema(
            description = "Status message in the response"
    )
    private String message;

    @Schema(
            description = "Query result in the response"
    )
    private T result;
}
