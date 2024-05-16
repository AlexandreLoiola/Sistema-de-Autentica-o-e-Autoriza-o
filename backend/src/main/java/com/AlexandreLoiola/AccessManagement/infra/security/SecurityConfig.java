package com.AlexandreLoiola.AccessManagement.infra.security;

import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.service.RoleService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private SecurityFilter securityFilter;
    private RoleService roleService;

    public SecurityConfig(SecurityFilter securityFilter, RoleService roleService) {
        this.securityFilter = securityFilter;
        this.roleService = roleService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        Set<RoleModel> roleModels = roleService.findAllRoleModels();
        Map<RequestMatcher, String> requestMatcherAuthorityMap = new HashMap<>();
        roleModels.stream()
                .flatMap(roleModel -> roleModel.getAuthorizations().stream()
                        .flatMap(authorization -> authorization.getMethods().stream()
                                .map(method -> new SimpleEntry<>(new AntPathRequestMatcher(
                                            authorization.getPath(), method.getDescription()),
                                            roleModel.getDescription())
                                )
                        )
                )
                .forEach(entry -> requestMatcherAuthorityMap.put(entry.getKey(), entry.getValue()));

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> {
                    requestMatcherAuthorityMap.forEach((requestMatcher, authority) ->
                            authz.requestMatchers(requestMatcher).hasAuthority(authority));
                    authz.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                    authz.requestMatchers("/users/login").permitAll();
                    authz.requestMatchers("POST", "/users").permitAll();
                    authz.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}