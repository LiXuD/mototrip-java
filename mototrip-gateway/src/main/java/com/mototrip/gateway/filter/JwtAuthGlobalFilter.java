package com.mototrip.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthGlobalFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret:mototrip-jwt-secret-key-must-be-at-least-256-bits-for-hs256-algorithm}")
    private String jwtSecret;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // 白名单路径（不需要鉴权）
    private static final List<String> WHITE_LIST = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/auth/wechat/login"
    );

    // 白名单前缀（GET 方法免鉴权）
    private static final List<String> WHITE_PREFIXES = List.of(
            "/api/routes",
            "/api/weather",
            "/api/danger-zones",
            "/api/no-parking-zones",
            "/api/warnings",
            "/api/teams",
            "/api/posts",
            "/api/footprints"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String method = request.getMethod().name();

        // Knife4j/Swagger 文档路径放行（优先处理）
        if (path.startsWith("/doc.html") || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources") || path.startsWith("/webjars")
                || (path.contains("/v3/api-docs") && path.startsWith("/api/"))
                || path.startsWith("/mototrip-")) {
            return chain.filter(exchange);
        }

        // 白名单精确匹配
        if (WHITE_LIST.contains(path)) {
            return chain.filter(exchange);
        }

        // 白名单前缀匹配（仅 GET 方法）
        if ("GET".equalsIgnoreCase(method)) {
            for (String prefix : WHITE_PREFIXES) {
                if (path.startsWith(prefix + "/") || path.equals(prefix)) {
                    return chain.filter(exchange);
                }
            }
        }

        // 提取 Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorizedResponse(exchange, "未登录或token已过期");
        }

        String token = authHeader.substring(7);
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String userId = claims.getSubject();
            String username = claims.get("username", String.class);

            // 将 userId 和 username 传递到下游服务
            ServerHttpRequest newRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .header("X-Username", username != null ? username : "")
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        } catch (Exception e) {
            log.warn("JWT验证失败: {}", e.getMessage());
            return unauthorizedResponse(exchange, "无效的token");
        }
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", 401);
        body.put("message", message);
        body.put("timestamp", Instant.now().toString());
        body.put("path", exchange.getRequest().getPath().value());

        try {
            ObjectMapper mapper = new ObjectMapper();
            byte[] bytes = mapper.writeValueAsBytes(body);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
