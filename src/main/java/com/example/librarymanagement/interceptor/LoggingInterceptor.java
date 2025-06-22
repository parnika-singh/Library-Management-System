package com.example.librarymanagement.interceptor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String method = request.getMethod();
        String uri = request.getRequestURI();

        System.out.println("[REQUEST] " + method + " " + uri + " at " + LocalDateTime.now());

        if ((uri.contains("/delete/") || uri.contains("/edit/")) && uri.endsWith("/null")) {
            System.err.println("⚠️ Invalid route access: " + uri);
            response.sendRedirect("/error/general");
            return false;
        }


        return true;
    }
}