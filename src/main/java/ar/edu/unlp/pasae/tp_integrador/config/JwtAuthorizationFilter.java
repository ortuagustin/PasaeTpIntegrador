package ar.edu.unlp.pasae.tp_integrador.config;

import static ar.edu.unlp.pasae.tp_integrador.config.JwtConfig.COOKIE_NAME;
import static ar.edu.unlp.pasae.tp_integrador.config.JwtConfig.SECRET;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  final UserDetailsService userDetailsService;

  public JwtAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService) {
    super(authManager);
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    SecurityContextHolder.getContext().setAuthentication(this.getUserFromJwt(req));
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getUserFromJwt(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);

    if (cookie == null) {
      return null;
    }

    String token = cookie.getValue();

    if (token == null) {
      return null;
    }

    String username = JWT
        .require(Algorithm.HMAC512(SECRET.getBytes()))
        .build()
        .verify(token)
        .getSubject();

    if (username == null) {
      return null;
    }

    UserDetails user = this.userDetailsService.loadUserByUsername(username);

    return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
  }
}