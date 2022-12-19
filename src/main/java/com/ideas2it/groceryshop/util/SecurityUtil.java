/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     Providing service for creating token and validate token
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Component
public class SecurityUtil {
    @Value("${jwt.secret}")
    private String secret;

    /**
     * <p>
     *      Get username from token.
     * </p>
     *
     * @param token - To get username.
     * @return - username.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * <p>
     *     Get claim from token.
     * </p>
     *
     * @param token - To get claims from token.
     * @param claimsResolver - To apply claims.
     * @return - Applied claimsResolver.
     * @param <T> - contains token, function<claims and claimsResolver.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * <p>
     *     Get all claims from token.
     * </p>
     *
     * @param token - To parse.
     * @return - claims.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * <p>
     *      Generate token based on username and claims.
     * </p>
     *
     * @param userDetails - Contains username and password
     *                      to generate token.
     * @return - bearer token.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * <p>
     *     Generate bearer token by username.
     * </p>
     *
     * @param claims - set secret key.
     * @param subject - username.
     * @return - bearer token.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * <p>
     *      Validate token by getting token and user details.
     * </p>
     *
     * @param token - To validate.
     * @param userDetails - Contains username and password.
     * @return - If token is valid it returns true or-else false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()));
    }
}