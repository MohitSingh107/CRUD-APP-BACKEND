package com.springrest.springrest.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService uds;
    @Autowired
    private JwtTokenHelper jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");
        System.out.println(requestToken);
        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("MohitB")) {
            token = requestToken.substring(7);
            try {
                username = this.jwt.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt Token Expired");
            } catch (MalformedJwtException e) {
                System.out.println("invalid jwt");
            }
        } else {
            System.out.println("Jwt Don't Exist / Doest not start with Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails ud = this.uds.loadUserByUsername(username);
            if (this.jwt.validateToken(token, ud)) {
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);

            } else {
                System.out.println("Invalid Token");
            }
        }else{
            System.out.println("username is null");
        }
        filterChain.doFilter(request,response);
    }
}
