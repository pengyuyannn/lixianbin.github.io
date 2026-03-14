package com.leon.service;

import com.leon.entity.Merchant;
import com.leon.entity.OrderDetail;
import com.leon.entity.Orders;
import com.leon.entity.ShoppingCart;
import com.leon.mapper.MerchantMapper;
import com.leon.mapper.OrderDetailMapper;
import com.leon.mapper.OrdersMapper;
import com.leon.mapper.ShoppingCartMapper;
import com.leon.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 订单服务
 */
@Service
public class OrdersService {
    
    @Autowired
    private OrdersMapper ordersMapper;
    
    @Autowired
    private MerchantMapper merchantMapper;
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    
    @Autowired
    private RedisUtil redisUtil;
    
    private static final String ORDER_CACHE_KEY = "order:";
    private static final String ORDER_LIST_CACHE_KEY = "orders:student:";
    private static final Integer ORDER_CACHE_EXPIRE = 7200; // 2 小时
    
    /**
     * 创建订单（使用事务）
     */
    @Transactional(rollbackFor = Exception.class)
    public Orders createOrder(Integer studentId, Integer merchantId, List<Integer> productIds, 
                              String deliveryAddress, String receiverName, String receiverPhone,
                              String remark) {
        // 获取购物车选中的商品
        List<ShoppingCart> cartItems = shoppingCartMapper.selectByStudentId(studentId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("购物车为空");
        }
        
        // 计算订单总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal deliveryFee = BigDecimal.ZERO;
        BigDecimal packingFee = new BigDecimal("2.00"); // 打包费固定 2 元
        
        for (ShoppingCart item : cartItems) {
            if (item.getSelected() == 1) {
                totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        
        // 实付金额 = 总金额 + 配送费 + 打包费
        BigDecimal payAmount = totalAmount.add(deliveryFee).add(packingFee);
        
        // 生成订单号
        String orderNo = generateOrderNo();
        
        // 创建订单
        Orders orders = new Orders();
        orders.setOrderNo(orderNo);
        orders.setStudentId(studentId);
        orders.setMerchantId(merchantId);
        orders.setOrderStatus(0); // 待支付
        orders.setTotalAmount(totalAmount);
        orders.setDeliveryFee(deliveryFee);
        orders.setPackingFee(packingFee);
        orders.setDiscountAmount(BigDecimal.ZERO);
        orders.setPayAmount(payAmount);
        orders.setDeliveryAddress(deliveryAddress);
        orders.setReceiverName(receiverName);
        orders.setReceiverPhone(receiverPhone);
        orders.setRemark(remark);
        
        ordersMapper.insert(orders);
        
        // 创建订单详情
        for (ShoppingCart cartItem : cartItems) {
            if (cartItem.getSelected() == 1) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orders.getId());
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setProductName(cartItem.getProductName());
                orderDetail.setProductImage(cartItem.getProductImage());
                orderDetail.setProductPrice(cartItem.getProductPrice());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setSubtotal(cartItem.getProductPrice().multiply(new BigDecimal(cartItem.getQuantity())));
                
                orderDetailMapper.insert(orderDetail);
            }
        }
        
        // 清空购物车
        shoppingCartMapper.deleteByStudentId(studentId);
        
        // 删除缓存
        redisUtil.delete(ORDER_LIST_CACHE_KEY + studentId);
        
        return orders;
    }
    
    /**
     * 支付订单
     */
    @Transactional(rollbackFor = Exception.class)
    public int payOrder(Integer orderId, Integer paymentMethod) {
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null) {
            throw new RuntimeException("订单不存在");
        }
        if (orders.getOrderStatus() != 0) {
            throw new RuntimeException("订单状态异常，无法支付");
        }
        
        // 更新订单状态为已支付待接单
        orders.setOrderStatus(1);
        orders.setPaymentMethod(paymentMethod);
        orders.setPaymentTime(LocalDateTime.now());
        
        // 删除缓存
        redisUtil.delete(ORDER_CACHE_KEY + orderId);
        redisUtil.delete(ORDER_LIST_CACHE_KEY + orders.getStudentId());
        
        return ordersMapper.updateById(orders);
    }
    
    /**
     * 商家接单
     */
    @Transactional(rollbackFor = Exception.class)
    public int merchantAccept(Integer orderId) {
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null || orders.getOrderStatus() != 1) {
            throw new RuntimeException("订单状态异常");
        }
        
        orders.setOrderStatus(2);
        orders.setMerchantAcceptTime(LocalDateTime.now());
        
        // 删除缓存
        redisUtil.delete(ORDER_CACHE_KEY + orderId);
        
        return ordersMapper.updateById(orders);
    }
    
    /**
     * 骑手接单
     */
    @Transactional(rollbackFor = Exception.class)
    public int riderAccept(Integer orderId, Integer riderId) {
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null || orders.getOrderStatus() != 2) {
            throw new RuntimeException("订单状态异常");
        }
        
        orders.setRiderId(riderId);
        orders.setOrderStatus(3);
        orders.setRiderAcceptTime(LocalDateTime.now());
        
        // 删除缓存
        redisUtil.delete(ORDER_CACHE_KEY + orderId);
        
        return ordersMapper.updateById(orders);
    }
    
    /**
     * 开始配送
     */
    @Transactional(rollbackFor = Exception.class)
    public int startDelivery(Integer orderId) {
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null || orders.getOrderStatus() != 3) {
            throw new RuntimeException("订单状态异常");
        }
        
        orders.setOrderStatus(4);
        orders.setDeliveryStartTime(LocalDateTime.now());
        
        // 删除缓存
        redisUtil.delete(ORDER_CACHE_KEY + orderId);
        
        return ordersMapper.updateById(orders);
    }
    
    /**
     * 完成订单
     */
    @Transactional(rollbackFor = Exception.class)
    public int completeOrder(Integer orderId) {
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null || orders.getOrderStatus() != 4) {
            throw new RuntimeException("订单状态异常");
        }
        
        orders.setOrderStatus(5);
        orders.setDeliveryCompleteTime(LocalDateTime.now());
        
        // 删除缓存
        redisUtil.delete(ORDER_CACHE_KEY + orderId);
        
        return ordersMapper.updateById(orders);
    }
    
    /**
     * 取消订单
     */
    @Transactional(rollbackFor = Exception.class)
    public int cancelOrder(Integer orderId, String reason) {
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null) {
            throw new RuntimeException("订单不存在");
        }
        if (orders.getOrderStatus() == 5 || orders.getOrderStatus() == 6) {
            throw new RuntimeException("订单已完成或已取消");
        }
        
        orders.setOrderStatus(6);
        orders.setCancelTime(LocalDateTime.now());
        orders.setCancelReason(reason);
        
        // 删除缓存
        redisUtil.delete(ORDER_CACHE_KEY + orderId);
        redisUtil.delete(ORDER_LIST_CACHE_KEY + orders.getStudentId());
        
        return ordersMapper.updateById(orders);
    }
    
    public Orders selectById(Integer orderId) {
        // 先查缓存
        String key = ORDER_CACHE_KEY + orderId;
        if (redisUtil.hasKey(key)) {
            return (Orders) redisUtil.get(key);
        }
        
        // 缓存没有，查数据库
        Orders orders = ordersMapper.selectById(orderId);
        if (orders != null) {
            // 填充商家名称
            fillMerchantName(orders);
            // 写入缓存
            redisUtil.set(key, orders, ORDER_CACHE_EXPIRE);
        }
        return orders;
    }
    
    public List<Orders> selectList(Orders orders) {
        List<Orders> orderList = ordersMapper.selectList(orders);
        // 缓存学生订单列表
        if (orders.getStudentId() != null && orderList != null) {
            // 填充所有订单的商家名称
            orderList.forEach(this::fillMerchantName);
            redisUtil.set(ORDER_LIST_CACHE_KEY + orders.getStudentId(), orderList, ORDER_CACHE_EXPIRE);
        }
        return orderList;
    }
    
    /**
     * 为订单填充商家名称
     * 
     * @param orders 订单对象
     */
    private void fillMerchantName(Orders orders) {
        if (orders != null && orders.getMerchantId() != null) {
            Merchant merchant = merchantMapper.selectById(orders.getMerchantId());
            if (merchant != null) {
                orders.setMerchantName(merchant.getShopName());
            }
        }
    }
    
    /**
     * 根据订单 ID 查询订单详情列表
     * 
     * @param orderId 订单 ID
     * @return 订单详情列表
     */
    public List<OrderDetail> getOrderDetailsByOrderId(Integer orderId) {
        // 参数验证
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("无效的订单 ID");
        }
        
        // 验证订单是否存在
        Orders orders = ordersMapper.selectById(orderId);
        if (orders == null) {
            throw new RuntimeException("订单不存在");
        }
        
        // 查询订单详情列表
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
        
        // 如果订单没有详情，返回空列表而不是 null
        return orderDetails != null ? orderDetails : List.of();
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).replace("-", "");
    }
}
