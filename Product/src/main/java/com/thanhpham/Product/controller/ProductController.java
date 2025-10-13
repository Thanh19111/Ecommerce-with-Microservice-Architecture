package com.thanhpham.Product.controller;

import com.thanhpham.Product.dto.request.ProductCreateRequest;
import com.thanhpham.Product.dto.request.ProductUpdateRequest;
import com.thanhpham.Product.dto.response.*;
import com.thanhpham.Product.entity.Product;
import com.thanhpham.Product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Tag(name = "CRUD REST APIs for Product Service", description = "CREATE, UPDATE, FETCH and DELETE Product details")
public class ProductController {

   private final IProductService iProductService;

    @Operation(
            summary = "Create Account REST API",
            description = "Create a new Account"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<String>builder()
                        .message("Success")
                        .result(iProductService.createProduct(productCreateRequest))
                        .build());
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductImage(@RequestParam("id") Long id, @RequestParam("file")MultipartFile file){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.<ProductResponse>builder()
                        .message("Success")
                        .result(iProductService.updateImageUrl(id, file))
                        .build());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<ProductTreeResponse>> findAllProductByCategoryId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.<ProductTreeResponse>builder()
                        .message("Success")
                        .result(iProductService.findAllProductByCategoryId(id))
                        .build());
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.<ProductResponse>builder()
                        .message("Success")
                        .result(iProductService.updateProduct(productUpdateRequest))
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findByProductId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.<ProductResponse>builder()
                        .message("Success")
                        .result(iProductService.findByProductId(id))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.<String>builder()
                        .message("Success")
                        .result(iProductService.deleteProduct(id))
                        .build());
    }
}
