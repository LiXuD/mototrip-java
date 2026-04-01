package com.mototrip.common.interceptor;

import com.mototrip.common.context.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userIdStr = request.getHeader("X-User-Id");
        String username = request.getHeader("X-Username");
        if (userIdStr != null) {
            try {
                UserContext.setUserId(Long.parseLong(userIdStr));
                UserContext.setUsername(username);
            } catch (NumberFormatException e) {
                log.warn("Invalid X-User-Id header: {}", userIdStr);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
