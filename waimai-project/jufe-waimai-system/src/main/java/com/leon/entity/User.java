package com.leon.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
public class User {
    private Integer id;
    private String phone;
    private String username;
    private String password;
    private String name;
    private String image;
    /**
     * 角色类型：1-学生 2-骑手 3-商家
     */
    private Integer roleType;
    /**
     * 账号状态：0-禁用 1-正常
     */
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
