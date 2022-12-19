/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ideas2it.groceryshop.filter.CustomSecurityFilter;

/**
 * <p>
 *    Provides service to restricts users from accessing all API and restrict them
 *    based on their role.
 * </p>
 *
 * @version 1.0
 * @author Rohit A P
 * @since 07-11-2022
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final CustomSecurityFilter customSecurityFilter;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          CustomSecurityFilter customSecurityFilter) {
        this.userDetailsService = userDetailsService;
        this.customSecurityFilter = customSecurityFilter;
    }

    /**
     * <p>
     *     Creates bean for BCryptPasswordEncoder.
     * </p>
     *
     * @return BCryptPasswordEncoder - Password encoder.
     */
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <p>
     *     Creates bean for DaoAuthenticationProvider T.
     * </p>
     *
     * @return authenticationProvider  - Authenticate user based on username and password.
     * @throws AuthenticationException - Contains invalid credentials message.
     */
    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider()
                                             throws AuthenticationException {
        DaoAuthenticationProvider daoAuthenticationProvider =
                                             new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * <p>
     *     Restricts users from accessing all api.
     * </p>
     *
     * @param httpSecurity  - To configure security filter.
     * @return http.build() - Contains authorization based on role.
     * @throws Exception    - Contains exception message.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/login",
                        "/api/v1/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/categories",
                        "/api/v1/products", "/api/v1/stores",
                        "/api/v1/stocks/location/*/products/*",
                        "/api/v1/users/roles")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users", "/api/v1/users/",
                        "/api/v1/user/orders/cancelledOrders",
                        "/api/v1/user/orders/*/*",
                        "/api/v1/user/orders/date/*/user/*",
                        "/api/v1/user/orders/date/*", "/api/v1/stocks/products/*",
                        "/api/v1/stocks/location/*/products/*", "/api/v1/stores",
                        "/api/v1/user/orders/products/*",
                        "/api/v1/users/*/role")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/categories/*",
                        "/api/v1/products/*", "/api/v1/users/roles/*",
                        "/api/v1/stocks/location/*/products/*",
                        "/api/v1/stocks/*/*", "/api/v1/stores/*",
                        "/api/v1/categories" +
                                "/subCategories/*/*")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/*",
                        "/api/v1/categories/*", "/api/v1/products/*",
                        "/api/v1/stocks/*", "/api/v1/stores/*",
                        "/api/v1/categories/subCategories/*/*",
                        "/api/v1/users/roles/*")
                .hasRole("ADMIN")
                .antMatchers("/api/v1/orders/*/orderDelivery",
                        "/api/v1/orders/*/statusUpdate")
                .hasRole("DELIVERY_PERSON")
                .antMatchers("/api/v1/user/orders/activeOrders")
                .hasAnyRole("ADMIN", "DELIVERY_PERSON")
                .antMatchers(HttpMethod.PUT, "/api/v1/user/carts",
                        "/api/v1/user/orders/*/cancelOrder")
                .hasRole("CUSTOMER")
                .antMatchers(HttpMethod.POST , "/api/v1/user/orders/*",
                        "/api/v1/user/carts")
                .hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/api/v1/user/orders/*",
                        "/api/v1/user/carts",
                        "/api/v1/user/orders")
                .hasRole("CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user/carts",
                        "/api/v1/user/carts/products/*")
                .hasRole("CUSTOMER")
                .anyRequest().authenticated()
                .and().httpBasic();
        httpSecurity.addFilterBefore(customSecurityFilter,
                UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/v1/login").antMatchers("/api/v1/users/*");
    }
}