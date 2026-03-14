package com.leon.mapper;

import com.leon.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单详情 Mapper
 */
@Mapper
public interface OrderDetailMapper {
    
    int insert(OrderDetail orderDetail);
    
    int batchInsert(@Param("list") List<OrderDetail> orderDetails);
    
    List<OrderDetail> selectByOrderId(@Param("orderId") Integer orderId);
}
