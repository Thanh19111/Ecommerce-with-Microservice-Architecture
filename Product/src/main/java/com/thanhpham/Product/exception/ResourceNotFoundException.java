package com.thanhpham.Product.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found with the give in put data %s: '%s'", resourceName,fieldName,fieldValue));
    }
}
