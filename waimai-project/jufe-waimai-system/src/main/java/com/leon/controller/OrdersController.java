package com.leon.controller;

import com.leon.common.Result;
import com.leon.entity.OrderDetail;
import com.leon.entity.Orders;
import com.leon.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrdersController {
    
    @Autowired
    private OrdersService ordersService;
    
    /**
     * 创建订单
     */
    @PostMapping
    public Result<Orders> create(@RequestBody CreateOrderRequest request) {
        Orders order = ordersService.createOrder(
            request.getStudentId(),
            request.getMerchantId(),
            request.getProductIds(),
            request.getDeliveryAddress(),
            request.getReceiverName(),
            request.getReceiverPhone(),
            request.getRemark()
        );
        return Result.success(order);
    }
    
    /**
     * 支付订单
     */
    @PostMapping("/{id}/pay")
    public Result<Integer> pay(@PathVariable Integer id, @RequestParam Integer paymentMethod) {
        return Result.success(ordersService.payOrder(id, paymentMethod));
    }
    
    /**
     * 商家接单
     */
    @PostMapping("/{id}/merchant-accept")
    public Result<Integer> merchantAccept(@PathVariable Integer id) {
        return Result.success(ordersService.merchantAccept(id));
    }
    
    /**
     * 骑手接单
     */
    @PostMapping("/{id}/rider-accept")
    public Result<Integer> riderAccept(@PathVariable Integer id, @RequestParam Integer riderId) {
        return Result.success(ordersService.riderAccept(id, riderId));
    }
    
    /**
     * 开始配送
     */
    @PostMapping("/{id}/start-delivery")
    public Result<Integer> startDelivery(@PathVariable Integer id) {
        return Result.success(ordersService.startDelivery(id));
    }
    
    /**
     * 完成订单
     */
    @PostMapping("/{id}/complete")
    public Result<Integer> complete(@PathVariable Integer id) {
        return Result.success(ordersService.completeOrder(id));
    }
    
    /**
     * 取消订单
     */
    @PostMapping("/{id}/cancel")
    public Result<Integer> cancel(@PathVariable Integer id, @RequestParam String reason) {
        return Result.success(ordersService.cancelOrder(id, reason));
    }
    
    /**
     * 根据订单 ID 获取订单详情列表
     * 
     * @param id 订单 ID，必须为正整数
     * @return 订单详情列表
     */
    @GetMapping("/{id}")
    public Result<List<OrderDetail>> getOrderDetails(@PathVariable Integer id) {
        // 参数验证
        if (id == null || id <= 0) {
            log.warn("无效的订单 ID: {}", id);
            return Result.error(400, "无效的订单 ID");
        }
        
        try {
            log.info("查询订单详情列表，订单 ID: {}", id);
            List<OrderDetail> orderDetails = ordersService.getOrderDetailsByOrderId(id);
            
            log.info("订单详情查询成功，订单 ID: {}, 详情数量：{}", id, orderDetails.size());
            return Result.success(orderDetails);
            
        } catch (IllegalArgumentException e) {
            log.warn("参数错误，订单 ID: {}, 错误：{}", id, e.getMessage());
            return Result.error(400, e.getMessage());
            
        } catch (RuntimeException e) {
            log.warn("业务异常，订单 ID: {}, 错误：{}", id, e.getMessage());
            return Result.error(404, e.getMessage());
            
        } catch (Exception e) {
            log.error("查询订单详情失败，订单 ID: {}, 错误：{}", id, e.getMessage(), e);
            return Result.error(500, "查询订单详情失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取订单列表
     */
    @GetMapping
    public Result<List<Orders>> list(Orders orders) {
        return Result.success(ordersService.selectList(orders));
    }
    
    /**
     * 创建订单请求 DTO
     */
    public static class CreateOrderRequest {
        private Integer studentId;
        private Integer merchantId;
        private List<Integer> productIds;
        private String deliveryAddress;
        private String receiverName;
        private String receiverPhone;
        private String remark;
        
        // Getters and Setters
        public Integer getStudentId() { return studentId; }
        public void setStudentId(Integer studentId) { this.studentId = studentId; }
        public Integer getMerchantId() { return merchantId; }
        public void setMerchantId(Integer merchantId) { this.merchantId = merchantId; }
        public List<Integer> getProductIds() { return productIds; }
        public void setProductIds(List<Integer> productIds) { this.productIds = productIds; }
        public String getDeliveryAddress() { return deliveryAddress; }
        public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
        public String getReceiverName() { return receiverName; }
        public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
        public String getReceiverPhone() { return receiverPhone; }
        public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }
        public String getRemark() { return remark; }
        public void setRemark(String remark) { this.remark = remark; }
    }
}
