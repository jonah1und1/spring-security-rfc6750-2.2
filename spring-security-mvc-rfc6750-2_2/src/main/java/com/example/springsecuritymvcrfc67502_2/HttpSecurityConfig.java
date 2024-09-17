package com.example.springsecuritymvcrfc67502_2;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(auth -> auth.anyRequest()
                                                      .authenticated())
                   .oauth2ResourceServer(resourceServer -> resourceServer.opaqueToken(withDefaults()))
                   .oauth2ResourceServer(spec -> {
                       DefaultBearerTokenResolver resolver = new DefaultBearerTokenResolver();
                       resolver.setAllowFormEncodedBodyParameter(true);
                       resolver.setAllowUriQueryParameter(true);
                       spec.bearerTokenResolver(resolver);
                   })
                   .build();
    }
}
