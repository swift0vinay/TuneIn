package org.teadev.tunein.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.teadev.tunein.exceptions.JwtTokenException;
import org.teadev.tunein.service.JwtService;
import org.teadev.tunein.utility.TuneInUtility;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String JWT_TOKEN = TuneInUtility.getJwtTokenFromHeaders(request);
        if (JWT_TOKEN == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String USERNAME = jwtService.getUsername(JWT_TOKEN);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (USERNAME != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(USERNAME);
                if (jwtService.isTokenValid(JWT_TOKEN, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            USERNAME,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    throw new JwtTokenException("Token has expired! Please login again");
                }
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
    
}
