package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${security.secret.key}")
    private String secretKey;

    public String generateToken(UserModel userModel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("auth_api")
                    .withSubject(userModel.getEmail())
                    .withExpiresAt(generateExpiresDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Error while generating token", ex);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("auth_api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

    private Instant generateExpiresDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
