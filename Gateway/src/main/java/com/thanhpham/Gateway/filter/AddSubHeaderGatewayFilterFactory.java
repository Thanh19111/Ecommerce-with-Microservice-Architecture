package com.thanhpham.Gateway.filter;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AddSubHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<AddSubHeaderGatewayFilterFactory.Config> {

    public AddSubHeaderGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    JWT jwt = JWTParser.parse(token);
                    JWTClaimsSet claimsSet = jwt.getJWTClaimsSet();
                    String sub = claimsSet.getSubject();

                    if (sub != null) {
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-Id", sub)
                                .build();

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange); // Trả về request đã thêm header
                    }
                } catch (Exception e) {
                    System.out.println("Error decoding JWT: " + e.getMessage());
                }
            }

            return chain.filter(exchange); // Không decode được thì đi qua bình thường
        };
    }

    public static class Config {
        // Có thể thêm cấu hình nếu cần
    }
}
