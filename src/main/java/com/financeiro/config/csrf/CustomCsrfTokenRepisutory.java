package com.financeiro.config.csrf;


import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.Assert;

/**
 * @author yongzan
 * @date 2016/7/29
 *  If need customize token  
 */
public final class CustomCsrfTokenRepisutory implements CsrfTokenRepository{
    
    private static final String DEFAULT_CSRF_PARAMETER_NAME = "_xsrf";

    private static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";

    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = CustomCsrfTokenRepisutory.class
            .getName().concat(".XSRF_TOKEN");
    
    private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;

    private String headerName = DEFAULT_CSRF_HEADER_NAME;

    private String sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;
    
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(headerName, parameterName, createNewToken());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(sessionAttributeName);
            }
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute(sessionAttributeName, token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (CsrfToken) session.getAttribute(sessionAttributeName);
    }
    
    public void setParameterName(String parameterName) {
        Assert.hasLength(parameterName, "parameterName cannot be null or empty");
        this.parameterName = parameterName;
    }

    public void setHeaderName(String headerName) {
        Assert.hasLength(headerName, "headerName cannot be null or empty");
        this.headerName = headerName;
    }

    public void setSessionAttributeName(String sessionAttributeName) {
        Assert.hasLength(sessionAttributeName, "sessionAttributename cannot be null or empty");
        this.sessionAttributeName = sessionAttributeName;
    }
    
    private String createNewToken() {
        return UUID.randomUUID().toString();
    }
}
