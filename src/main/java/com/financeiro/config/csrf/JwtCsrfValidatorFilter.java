package com.financeiro.config.csrf;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.financeiro.service.SecretService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtCsrfValidatorFilter extends OncePerRequestFilter {

	@Autowired
	private SecretService secretService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

		if ("POST".equals(request.getMethod()) && token != null) {
			try {
				Jwts.parser().setSigningKeyResolver(secretService.getSigningKeyResolver())
						.parseClaimsJws(token.getToken());
			} catch (JwtException e) {
				request.setAttribute("exception", e);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				RequestDispatcher dispatcher = request.getRequestDispatcher("expired-jwt");
				dispatcher.forward(request, response);
			}
		}

		filterChain.doFilter(request, response);
	}
}
