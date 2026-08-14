package com.software.dev.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 剥离 context-path（如 /efmp-job），使白名单判断不受 server.servlet.context-path 配置影响
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        String path = (contextPath != null && !contextPath.isEmpty() && requestURI.startsWith(contextPath))
                ? requestURI.substring(contextPath.length())
                : requestURI;

        // 允许访问的路径
        if (path.startsWith("/demo/") ||
            path.equals("/api/auth/login") ||
            path.equals("/api/auth/check") ||
            path.equals("/login") ||
            path.isEmpty() ||
            path.equals("/")) {
            return true;
        }
        
        // 检查用户是否已登录
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return true;
        }
        
        // 未登录，返回401状态码
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\": false, \"message\": \"请先登录\"}");
        return false;
    }
}