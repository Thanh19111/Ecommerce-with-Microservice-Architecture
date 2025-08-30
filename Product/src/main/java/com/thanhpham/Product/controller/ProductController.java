package com.thanhpham.Product.controller;

import com.thanhpham.Product.dto.request.ProductCreateRequest;
import com.thanhpham.Product.dto.request.ProductUpdateRequest;
import com.thanhpham.Product.dto.response.*;
import com.thanhpham.Product.entity.Product;
import com.thanhpham.Product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<APIResponse<String>> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(iProductService.createProduct(productCreateRequest))
                        .build());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<APIResponse<ProductTreeResponse>> findAllProductByCategoryId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<ProductTreeResponse>builder()
                        .message("Success")
                        .result(iProductService.findAllProductByCategoryId(id))
                        .build());
    }

    @PutMapping("")
    public ResponseEntity<APIResponse<ProductResponse>> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<ProductResponse>builder()
                        .message("Success")
                        .result(iProductService.updateProduct(productUpdateRequest))
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> findByProductId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<ProductResponse>builder()
                        .message("Success")
                        .result(iProductService.findByProductId(id))
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.<String>builder()
                        .message("Success")
                        .result(iProductService.deleteProduct(id))
                        .build());
    }




}
