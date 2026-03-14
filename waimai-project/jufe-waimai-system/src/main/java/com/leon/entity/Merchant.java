package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * 商家实体
 */
@Data
public class Merchant {
    private Integer id;
    private Integer userId;
    private String shopName;
    private String shopLogo;
    private String shopPhone;
    private String shopAddress;
    private String campusArea;
    private LocalTime businessHoursStart;
    private LocalTime businessHoursEnd;
    /**
     * 起送价
     */
    private BigDecimal minDeliveryAmount;
    /**
     * 配送费
     */
    private BigDecimal deliveryFee;
    /**
     * 营业状态：0-休息中 1-营业中
     */
    private Integer shopStatus;
    private String shopNotice;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
