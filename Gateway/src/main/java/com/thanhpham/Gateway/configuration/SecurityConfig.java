package com.thanhpham.Gateway.configuration;


import com.thanhpham.Gateway.component.AccessDeniedHandler;
import com.thanhpham.Gateway.component.AuthenticationEntryPoint;
import com.thanhpham.Gateway.endpoint.InventoryEndpoint;
import com.thanhpham.Gateway.endpoint.OrderEndpoint;
import com.thanhpham.Gateway.endpoint.ProductEndpoint;
import com.thanhpham.Gateway.endpoint.UserEndpoint;
import com.thanhpham.Gateway.util.GetAllEndPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final GetAllEndPoint getAllEndPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity, AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint) {
        serverHttpSecurity
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET,getAllEndPoint.getAllEndpoints(UserEndpoint.ADMIN_GET_ENDPOINTS, InventoryEndpoint.ADMIN_GET_ENDPOINTS, OrderEndpoint.ADMIN_GET_ENDPOINTS)).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST,getAllEndPoint.getAllEndpoints(ProductEndpoint.ADMIN_POST_ENDPOINTS, InventoryEndpoint.ADMIN_POST_ENDPOINTS)).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, getAllEndPoint.getAllEndpoints(ProductEndpoint.ADMIN_PUT_ENDPOINTS, InventoryEndpoint.ADMIN_PUT_ENDPOINTS, OrderEndpoint.ADMIN_PUT_ENDPOINTS, UserEndpoint.ADMIN_PUT_ENDPOINTS)).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, getAllEndPoint.getAllEndpoints(ProductEndpoint.ADMIN_DELETE_ENDPOINTS)).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, getAllEndPoint.getAllEndpoints(ProductEndpoint.PUBLIC_GET_ENDPOINTS, InventoryEndpoint.PUBLIC_GET_ENDPOINTS)).permitAll()
                        .pathMatchers(HttpMethod.POST, getAllEndPoint.getAllEndpoints(UserEndpoint.PUBLIC_POST_ENDPOINTS, InventoryEndpoint.PUBLIC_POST_ENDPOINTS, OrderEndpoint.PUBLIC_POST_ENDPOINTS)).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))
                .exceptionHandling(exceptionHandlingSpec ->
                        exceptionHandlingSpec
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler));

        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return serverHttpSecurity.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


    public SecurityConfig(GetAllEndPoint getAllEndPoint){
        this.getAllEndPoint = getAllEndPoint;
    }
}
