package com.thanhpham.Gateway.component;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private final Unauthorized unauthorized;

    public AuthenticationEntryPoint(Unauthorized unauthorized){
        this.unauthorized = unauthorized;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        System.out.println(">> JWT Error: " + ex.getMessage());
        return unauthorized.returnMessage(exchange);
    }
}
