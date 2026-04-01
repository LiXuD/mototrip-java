package com.mototrip.common.interceptor;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.mototrip.common.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class SentinelResourceInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String resource;

        // Classify resource based on HTTP method
        if ("GET".equals(method) || "HEAD".equals(method) || "OPTIONS".equals(method)) {
            resource = "global-api";
        } else if (uri.contains("/auth/")) {
            resource = "auth-api";
        } else {
            resource = "write-api";
        }

        Entry entry = null;
        try {
            entry = SphU.entry(resource);
            request.setAttribute("_sentinel_entry", entry);
            return true;
        } catch (FlowException e) {
            log.warn("Sentinel flow limited: {} {}", method, uri);
            writeResponse(response, 429, "请求过于频繁，请稍后再试");
            return false;
        } catch (DegradeException e) {
            log.warn("Sentinel degraded: {} {}", method, uri);
            writeResponse(response, 503, "服务暂时不可用，请稍后再试");
            return false;
        } catch (BlockException e) {
            log.warn("Sentinel blocked: {} {}", method, uri);
            writeResponse(response, 429, "请求被限流");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        Entry entry = (Entry) request.getAttribute("_sentinel_entry");
        if (entry != null) {
            if (ex != null) {
                Tracer.traceEntry(ex, entry);
            }
            entry.exit();
        }
    }

    private void writeResponse(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        Result<Void> result = Result.error(status, message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
