package com.mototrip.common.interceptor;

import com.mototrip.common.context.UserContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignUserContextInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Long userId = UserContext.getUserId();
        String username = UserContext.getUsername();
        if (userId != null) {
            template.header("X-User-Id", String.valueOf(userId));
        }
        if (username != null) {
            template.header("X-Username", username);
        }
    }
}
