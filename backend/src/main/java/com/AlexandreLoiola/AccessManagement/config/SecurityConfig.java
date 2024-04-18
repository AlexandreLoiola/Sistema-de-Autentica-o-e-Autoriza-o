package com.AlexandreLoiola.AccessManagement.config;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RoleService roleService;

    public SecurityConfig(RoleService roleService) {
        this.roleService = roleService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/**").hasRole("Admin");
                    Set<RoleModel> roles = roleService.findAllRoleModels();
                    for (RoleModel role : roles) {
                        for (AuthorizationModel authorization : role.getAuthorizations()) {
                            for (MethodModel method : authorization.getMethods()) {
                                authorize.requestMatchers(HttpMethod.valueOf(method.getDescription()), authorization.getPath()).hasRole(role.getDescription());
                            }
                        }
                    }
                    authorize.anyRequest().authenticated();
                })
                .build();
    }
}
