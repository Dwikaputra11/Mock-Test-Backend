package com.kiwadev.mocktest.security;

import com.kiwadev.mocktest.exception.AuthException;
import com.kiwadev.mocktest.helper.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestSecurityFilter extends OncePerRequestFilter {

    private final JwtService         jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String       jwt        = "";
        String       username   = "";
        final String requestUrl = request.getRequestURL().toString();

        // get session from mvc url to retrieve access token
        if (requestUrl.contains("/web-public")) {
            var userRequest = request.getSession().getAttribute(Constant.ACCESS_TOKEN);
            log.info("Access token: {}", userRequest);
            if (userRequest instanceof String) jwt = (String) userRequest;
        }

        log.info("path info: {}", request.getRequestURL());

        // if request is from api
        if (requestUrl.contains("/api")) {
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7); // get token
        }

        if (!jwt.isEmpty()) username = jwtService.extractUsername(jwt);

        if (!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                logger.info("Token valid");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                throw new AuthException("Invalid token! your token may not exist in our server. Please try again");
            }
        }
        filterChain.doFilter(request, response);
    }
}
