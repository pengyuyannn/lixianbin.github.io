package com.leon.controller;

import com.leon.common.Result;
import com.leon.dto.LoginRequest;
import com.leon.dto.LoginResponse;
import com.leon.entity.User;
import com.leon.mapper.UserMapper;
import com.leon.utils.CurrentHolder;
import com.leon.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 用户登录
     */
    @PostMapping
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("用户登录请求：username={}", request.getUsername());
        
        // 1. 根据用户名查询用户
        User user = userMapper.selectByUsername(request.getUsername());
        
        if (user == null) {
            return Result.error("用户不存在");
        }
        
        // 2. 校验密码
        if (!request.getPassword().equals(user.getPassword())) {
            return Result.error("密码错误");
        }
        
        // 3. 检查用户状态
        if (user.getStatus() != 1) {
            return Result.error("账号已被禁用");
        }
        
        // 4. 生成 JWT Token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        claims.put("roleType", user.getRoleType());
        
        String token = JwtUtils.generateToken(claims);
        
        log.info("用户登录成功：userId={}, username={}", user.getId(), user.getUsername());
        
        // 5. 返回登录信息
        LoginResponse response = new LoginResponse(
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getPhone(),
            user.getRoleType(),
            token
        );
        
        return Result.success(response);
    }
    
    /**
     * 获取当前登录用户信息（用于测试 CurrentHolder）
     */
//    @GetMapping("/current")
//    public Result<User> getCurrentUser() {
//        Integer userId = CurrentHolder.getCurrentId();
//        if (userId == null) {
//            return Result.error("未登录");
//        }
//
//        User user = userMapper.selectById(userId);
//        return Result.success(user);
//    }
}
