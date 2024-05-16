package com.AlexandreLoiola.AccessManagement.infra.security;

import com.AlexandreLoiola.AccessManagement.mapper.UserMapper;
import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.AlexandreLoiola.AccessManagement.service.TokenService;
import com.AlexandreLoiola.AccessManagement.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserService userService;
    private UserMapper userMapper;

    public SecurityFilter(TokenService tokenService, UserService userService, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);
        if (token != null) {
            String login = tokenService.validateToken(token);
            UserModel userModel = userService.findUserModelByEmail(login);
            List<GrantedAuthority> authorities = userModel.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getDescription()))
                    .collect(Collectors.toList());
            var authentication = new UsernamePasswordAuthenticationToken(userModel, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}