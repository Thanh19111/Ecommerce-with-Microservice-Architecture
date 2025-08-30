package com.thanhpham.Gateway.component;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AccessDeniedHandler implements ServerAccessDeniedHandler {
    private final AccessDenied accessDenied;

    public AccessDeniedHandler(AccessDenied accessDenied) {
        this.accessDenied = accessDenied;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        System.out.println(">> JWT Error: " + ex.getMessage());
        return accessDenied.returnMessage(exchange);
    }
}
