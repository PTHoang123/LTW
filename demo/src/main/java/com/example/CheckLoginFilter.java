package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CheckLoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {}
    
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        
        boolean isLoggedIn = session != null && session.getAttribute("userId") != null;
        String loginURI = request.getContextPath() + "/login.html";
        
        boolean isLoginPage = request.getRequestURI().equals(loginURI);
        boolean isStaticResource = request.getRequestURI().endsWith(".css") || 
                                 request.getRequestURI().endsWith(".js");
        
        if (isLoggedIn || isLoginPage || isStaticResource) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURI);
        }
    }
    
    public void destroy() {}
}