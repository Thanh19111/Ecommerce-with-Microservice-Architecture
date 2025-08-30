package com.thanhpham.Gateway.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanhpham.Gateway.entity.ErrorCode;
import com.thanhpham.Gateway.entity.ErrorResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class AccessDenied {

    private final ObjectMapper objectMapper;

    public AccessDenied(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public Mono<Void> returnMessage(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(ErrorCode.FORBIDDEN.getStatusCode());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(
                exchange.getRequest().getPath().toString(),
                ErrorCode.FORBIDDEN.getCode(),
                ErrorCode.FORBIDDEN.getMessage(),
                LocalDateTime.now()
        );

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Mono.error(e);
        }
    }

    public Mono<Void> returnMessage(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(ErrorCode.FORBIDDEN.getStatusCode());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(
                exchange.getRequest().getPath().toString(),
                ErrorCode.FORBIDDEN.getCode(),
                message,
                LocalDateTime.now()
        );

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Mono.error(e);
        }
    }
}
