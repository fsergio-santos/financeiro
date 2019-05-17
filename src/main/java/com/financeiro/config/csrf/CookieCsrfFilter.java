package com.financeiro.config.csrf;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author yongzan
 * @date 2016/7/27
 */
public class CookieCsrfFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(CookieCsrfFilter.class);
    
    // name of the cookie
    public static final String XSRF_TOKEN_COOKIE_NAME = "XSRF-TOKEN";

    // name of the header to be receiving from the client
    public static final String XSRF_TOKEN_HEADER_NAME = "X-XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Get csrf attribute from request
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName()); // Or "_csrf"
                                                                                      // (See CSRFFilter.doFilterInternal).
        if (csrf != null) { // if csrf attribute was found
            String token = csrf.getToken();
            if (token != null) { // if there is a token
                // set the cookie
                Cookie cookie = new Cookie(XSRF_TOKEN_COOKIE_NAME, token);
                cookie.setPath("/");
                //cookie.setHttpOnly(true);
                //cookie.setSecure(true);
                
                // CORS requests can't see the cookie if domains are different,even if httpOnly is false.
                // So, let's add it as a header as well.
                response.addCookie(cookie);
                response.addHeader(XSRF_TOKEN_HEADER_NAME, token);
            }
        }
        filterChain.doFilter(request, response);
    }

}
