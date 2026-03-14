package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 */
@Data
public class Product {
    private Integer id;
    private Integer merchantId;
    private String name;
    private String description;
    private String image;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 原价（用于显示折扣）
     */
    private BigDecimal originalPrice;
    /**
     * 商品分类（如：主食/饮品/小吃）
     */
    private String category;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 月销量
     */
    private Integer soldCount;
    /**
     * 商品状态：0-下架 1-上架
     */
    private Integer status;
    /**
     * 排序权重
     */
    private Integer sortOrder;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
