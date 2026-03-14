package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 评价实体
 */
@Data
public class Comment {
    private Integer id;
    private Integer orderId;
    private Integer studentId;
    private Integer merchantId;
    private Integer riderId;
    /**
     * 商品评分（1-5 分）
     */
    private BigDecimal productRating;
    /**
     * 配送评分（1-5 分）
     */
    private BigDecimal deliveryRating;
    private String content;
    private String imageUrls;
    private String replyContent;
    private LocalDateTime replyTime;
    /**
     * 是否匿名：0-否 1-是
     */
    private Integer isAnonymous;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
