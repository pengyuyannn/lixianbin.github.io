package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 学生档案实体
 */
@Data
public class UserProfileStudent {
    private Integer id;
    private Integer userId;
    private String studentId;
    private String college;
    private String major;
    private String grade;
    private String dormitoryBuilding;
    private String dormitoryRoom;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
