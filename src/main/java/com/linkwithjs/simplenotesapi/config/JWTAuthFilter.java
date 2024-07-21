package com.linkwithjs.simplenotesapi.config;


import com.linkwithjs.simplenotesapi.service.UserService;
import com.linkwithjs.simplenotesapi.utility.JWTUtils;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;
    private final UserService userService;

    public JWTAuthFilter(JWTUtils jwtUtils, UserService userService) {
//        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;
        if(authHeader == null || authHeader.isBlank()){
            try {
                filterChain.doFilter(request,response);
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        jwtToken = authHeader.substring(7);
        userEmail = jwtUtils.extractUsername(jwtToken);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = userService.loadUserByUsername(userEmail);

        if(jwtUtils.isTokenValid(jwtToken, userDetails)){
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(token);
            SecurityContextHolder.setContext(securityContext);
        }
        }

        try {
            filterChain.doFilter(request, response);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

}
