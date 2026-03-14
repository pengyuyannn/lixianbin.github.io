package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 骑手档案实体
 */
@Data
public class UserProfileRider {
    private Integer id;
    private Integer userId;
    private String realName;
    private String idCard;
    private String vehicleType;
    private String vehicleNumber;
    /**
     * 接单状态：0-休息 1-空闲 2-忙碌
     */
    private Integer workStatus;
    /**
     * 累计接单数
     */
    private Integer totalOrders;
    /**
     * 评分
     */
    private BigDecimal rating;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
