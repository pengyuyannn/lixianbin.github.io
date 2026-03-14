package com.leon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应 DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    /**
     * 用户 ID
     */
    private Integer userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 角色类型：1-学生 2-骑手 3-商家
     */
    private Integer roleType;
    
    /**
     * JWT Token
     */
    private String token;
}
