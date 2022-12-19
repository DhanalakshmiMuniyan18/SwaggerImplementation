/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ideas2it.groceryshop.util.SecurityUtil;

/**
 * <p>
 *     Check if bearer token is valid or not and give authentication.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Component
public class CustomSecurityFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final SecurityUtil securityUtil;

    @Autowired
    public CustomSecurityFilter(UserDetailsService userDetailsService,
                                SecurityUtil securityUtil) {
        this.userDetailsService = userDetailsService;
        this.securityUtil = securityUtil;
    }

    /**
     * <p>
     *      Validates token received in HttpServletRequest.
     * </p>
     *
     * @param httpServletRequest        - Contains authentication type
     * @param httpServletResponse       - Contains http response codes
     * @param filterChain               - Contains request and response servlet
     * @throws IllegalArgumentException - If wrong token is given
     * @throws ServletException         - If request or response is inappropriate
     * @throws IOException              - If input or output is inappropriate
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws IllegalArgumentException, ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    username = securityUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException illegalArgumentException) {
                    throw new IllegalArgumentException("Unable to get JWT Token");
                }
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (securityUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken
                            usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken
                                    (userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().
                            setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}