package com.leon.interceptor;

import com.leon.utils.CurrentHolder;
import com.leon.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;

/**
 * JWT Token 拦截器
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头中的 token
        String token = request.getHeader("token");
        
        // 2. 如果没有 token，拒绝访问
        if (token == null || token.isEmpty()) {
            log.warn("未携带 token，拒绝访问：{}", request.getRequestURI());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未授权访问，请先登录\",\"data\":null}");
            return false;
        }
        
        try {
            // 3. 解析 token
            Claims claims = JwtUtils.parseToken(token);
            
            // 4. 从 claims 中获取用户 ID
            Integer userId = claims.get("userId", Integer.class);
            
            if (userId == null) {
                log.warn("Token 中缺少 userId 信息");
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"Token 无效\",\"data\":null}");
                return false;
            }
            
            // 5. 将用户 ID 存入 ThreadLocal
            CurrentHolder.setCurrentId(userId.intValue());
            
            log.debug("Token 验证通过：userId={}", userId);
            
            return true;
            
        } catch (Exception e) {
            log.error("Token 验证失败：{}", e.getMessage());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Token 无效或已过期\",\"data\":null}");
            return false;
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 6. 清理 ThreadLocal，防止内存泄漏
        CurrentHolder.remove();
    }
}
