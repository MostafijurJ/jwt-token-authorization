package com.learn.auth.jwt.jwttokenauthorization.filter;

import com.learn.auth.jwt.jwttokenauthorization.config.JwtToken;
import com.learn.auth.jwt.jwttokenauthorization.service.UserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {


  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtToken jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)

      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String username = null;

    String jwtToken = null;


    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

      jwtToken = requestTokenHeader.substring(7);

      try {
        username = jwtTokenUtil.getUsernameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        log.error("Unable to get JWT Token " + e);
      } catch (ExpiredJwtException e) {
        log.error("JWT Token has expired " + e);
      }

    } else {
      logger.warn("JWT Token does not begin with Bearer String");
      log.trace("JWT Token does not begin with Bearer String");
    }


    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


      if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

            userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken

            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }
}
