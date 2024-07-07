package com.hk.sec.prep.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hk.sec.prep.services.JWTService;
import com.hk.sec.prep.services.UserService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    
    @Autowired
    private final JWTService jwtService;

    
    @Autowired
    private final UserService userService;

    /**
     * The main method that gets called for every request 
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Get the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        // If the Authorization header is missing or doesn't start with "Bearer ", proceed with the next filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract the JWT token from the Authorization header
            final String jwt = authHeader.substring(7);
            // Extract the username (or email) from the JWT token
            final String userEmail = jwtService.extractUserName(jwt);

            // If the username is not empty and there is no current authentication in the security context
            if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details using the username
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

                // Validate the JWT token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Create an empty SecurityContext
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    
                    // Create a UsernamePasswordAuthenticationToken with user details and authorities
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    
                    // Set the details of the authentication token with the request details
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the security context
                    securityContext.setAuthentication(usernamePasswordAuthenticationToken);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
        } catch (Exception e) {
            // Print the stack trace if there is an exception during the token validation process
            e.printStackTrace();
        }
        
        // Proceed with the next filter in the filter chain
        filterChain.doFilter(request, response);
    }
}
