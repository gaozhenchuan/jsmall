package org.jsmall.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class BaseInterceptor implements HandlerInterceptor {

    private static final String SESSION_USER = "SESSION_USER_INFO";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        
        String requestUri = request.getRequestURI();
        
        String[] allowUrls = new String[] {"/toLogin", "/login", "/loginTurn"};
        if (session.getAttribute(SESSION_USER) == null) {
            for (String url : allowUrls) {
                if (requestUri.endsWith(url)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        
    }

}
