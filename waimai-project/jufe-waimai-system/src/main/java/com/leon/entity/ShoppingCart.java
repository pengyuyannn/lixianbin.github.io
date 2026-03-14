package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车实体
 */
@Data
public class ShoppingCart {
    private Integer id;
    private Integer studentId;
    private Integer merchantId;
    private Integer productId;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;
    private Integer quantity;
    /**
     * 是否选中：0-未选中 1-选中
     */
    private Integer selected;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
