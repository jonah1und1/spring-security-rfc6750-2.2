package com.example.springwebfluxsecrfc67502_2;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.web.server.authentication.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(spec -> spec.anyExchange()
                                                  .authenticated())
                   .oauth2ResourceServer(resourceServer -> resourceServer.opaqueToken(withDefaults()))
                   .oauth2ResourceServer(spec -> {
                       final var bearerTokenConverter = new ServerBearerTokenAuthenticationConverter();
                       bearerTokenConverter.setAllowUriQueryParameter(true);
                       spec.bearerTokenConverter(bearerTokenConverter);
                   })
                   .build();
    }
}
