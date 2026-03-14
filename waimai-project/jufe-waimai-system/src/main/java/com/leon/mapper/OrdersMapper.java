package com.leon.mapper;

import com.leon.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单 Mapper
 */
@Mapper
public interface OrdersMapper {
    
    int insert(Orders orders);
    
    int updateById(Orders orders);
    
    Orders selectById(@Param("id") Integer id);
    
    Orders selectByOrderNo(@Param("orderNo") String orderNo);
    
    List<Orders> selectList(Orders orders);
    
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
