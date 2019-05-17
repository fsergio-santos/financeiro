package com.financeiro.security.custom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private static final String SESSION_COOKIE_PATH = "/";

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final HttpSession session = request.getSession();
        
        response.setHeader("Access-Control-Allow-Origin", "*");
    
        if (authentication != null && authentication.getDetails() != null) {
            try {
            	session.removeAttribute("user");
                session.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        String path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        response.setStatus(HttpServletResponse.SC_OK);
        limparTodosCookies(request, response);
        response.sendRedirect(path+"/logout.html?logSucc=true");
    }
    
    private void limparTodosCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1)
            return;
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath(SESSION_COOKIE_PATH);
            cookie.setValue(null);
            response.addCookie(cookie);
        }
    }
    

}
