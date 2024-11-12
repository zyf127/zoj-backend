package com.zyf.zojbackendgateway.filter;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import com.zyf.zojbackendcommon.constant.UserConstant;
import com.zyf.zojbackendcommon.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * JWT密钥
     */
    @Value("${jwt.secret}")
    private String secret;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 判断路径中是否包含 inner，inner 只允许内部调用
        String path = request.getURI().getPath();
        if (antPathMatcher.match("/**/inner/**", path)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }
        // 通过 JWT 校验管理员权限
        if (antPathMatcher.match("/**/admin/**", path)) {
            String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            if (StrUtil.isBlank(authorization) || !authorization.contains("Bearer")) {
                return response.writeWith(Mono.just(dataBuffer));
            }
            String token = authorization.substring(7);
            Claims claims = JWTUtils.getClaimsFromToken(token, secret);
            if (claims == null) {
                return response.writeWith(Mono.just(dataBuffer));
            }
            String userRole = (String)claims.get("userRole");
            if (!UserConstant.ADMIN_ROLE.equals(userRole)) {
                return response.writeWith(Mono.just(dataBuffer));
            }
        }
        return chain.filter(exchange);
    }

    /**
     * 优先级提到最高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
