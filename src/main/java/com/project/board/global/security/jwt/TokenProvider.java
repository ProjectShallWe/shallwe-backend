package com.project.board.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.project.board.global.exception.CustomAccessNotValidException;
import com.project.board.global.exception.CustomAccessTokenExpiredException;
import com.project.board.global.exception.CustomRefreshTokenExpiredException;
import com.project.board.global.exception.CustomSignatureVerificationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenProvider {

    public String createAccessToken(String email, String username, Date accessTokenExpiresIn) {
        return JWT.create()
                .withIssuer(JwtProperties.ISSUER)
                .withSubject(JwtProperties.ACCESS_TOKEN)
                .withExpiresAt(accessTokenExpiresIn)
                .withClaim(JwtProperties.EMAIL, email)
                .withClaim(JwtProperties.NICKNAME, username)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public String createRefreshToken(String email, Date refreshTokenExpiresIn) {
        return JWT.create()
                .withIssuer(JwtProperties.ISSUER)
                .withSubject(JwtProperties.REFRESH_TOKEN)
                .withExpiresAt(refreshTokenExpiresIn)
                .withClaim(JwtProperties.EMAIL, email)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public boolean validateAccessToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw new CustomAccessTokenExpiredException();
        } catch (SignatureVerificationException | JWTDecodeException e) {
            throw new CustomSignatureVerificationException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateRefreshToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                    .build().verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw new CustomRefreshTokenExpiredException();
        } catch (SignatureVerificationException | JWTDecodeException e) {
            throw new CustomSignatureVerificationException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String extractToken(HttpServletRequest req) {
        return req.getHeader(JwtProperties.ACCESS_HEADER_PREFIX)
                .replace(JwtProperties.TOKEN_PREFIX, "");
    }

    public String getEmailFrom(String token) {
        return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build()
                .verify(token)
                .getClaim(JwtProperties.EMAIL)
                .asString();
    }
}
