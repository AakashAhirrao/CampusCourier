package com.campuscourier.campus_courier.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")){

            jwt = authorizationHeader.substring(7);

            try{
                email = jwtUtil.parseEmail(jwt);
            }
            catch (Exception e){
                System.out.println("Invalid or expired token");
            }
        }

        // 3. If we found an email, and the user isn't already logged into the server context...
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. Validate the token's signature
            if (jwtUtil.validateToken(jwt)) {

                // 5. Tell the Spring Security Bouncer: "This guy is legit, let him in!"
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. Pass the request to the next guard in the hallway
        chain.doFilter(request, response);
    }

}
