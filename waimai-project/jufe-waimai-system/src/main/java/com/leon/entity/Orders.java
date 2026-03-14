package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体
 */
@Data
public class Orders {
    private Integer id;
    private String orderNo;
    private Integer studentId;
    private Integer merchantId;
    private String merchantName; // 商家名称
    private Integer riderId;
    /**
     * 订单状态：0-待支付 1-已支付待接单 2-商家已接单 3-骑手已接单 4-配送中 5-已完成 6-已取消 7-退款中
     */
    private Integer orderStatus;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    /**
     * 配送费
     */
    private BigDecimal deliveryFee;
    /**
     * 打包费
     */
    private BigDecimal packingFee;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 实付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付方式：1-微信 2-支付宝 3-校园卡
     */
    private Integer paymentMethod;
    private String deliveryAddress;
    private String receiverName;
    private String receiverPhone;
    /**
     * 送达时间（用户预约的时间）
     */
    private LocalDateTime deliveryTime;
    private String remark;
    /**
     * 下单时间
     */
    private LocalDateTime createTime;
    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;
    /**
     * 商家接单时间
     */
    private LocalDateTime merchantAcceptTime;
    /**
     * 骑手接单时间
     */
    private LocalDateTime riderAcceptTime;
    /**
     * 开始配送时间
     */
    private LocalDateTime deliveryStartTime;
    /**
     * 配送完成时间
     */
    private LocalDateTime deliveryCompleteTime;
    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;
    private String cancelReason;
    private LocalDateTime updateTime;
}
