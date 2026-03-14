package com.leon.service;

import com.leon.entity.ShoppingCart;
import com.leon.mapper.ShoppingCartMapper;
import com.leon.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车服务
 */
@Service
public class ShoppingCartService {
    
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    
    @Autowired
    private RedisUtil redisUtil;
    
    private static final String CART_CACHE_KEY = "cart:student:";
    private static final Integer CART_CACHE_EXPIRE = 1800; // 30 分钟
    
    /**
     * 添加商品到购物车
     */
    public int addToCart(ShoppingCart shoppingCart) {
        // 检查是否已存在
        ShoppingCart existItem = shoppingCartMapper.selectByStudentIdAndProductId(
            shoppingCart.getStudentId(), shoppingCart.getProductId());
        
        if (existItem != null) {
            // 已存在，数量 +1
            existItem.setQuantity(existItem.getQuantity() + shoppingCart.getQuantity());
            shoppingCartMapper.updateById(existItem);
        } else {
            // 不存在，新增
            shoppingCart.setSelected(1);
            shoppingCartMapper.insert(shoppingCart);
        }
        
        // 删除缓存
        redisUtil.delete(CART_CACHE_KEY + shoppingCart.getStudentId());
        
        return 1;
    }
    
    /**
     * 更新购物车商品数量
     */
    public int updateQuantity(Integer id, Integer quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        shoppingCart.setQuantity(quantity);
        
        ShoppingCart item = shoppingCartMapper.selectById(id);
        if (item != null) {
            redisUtil.delete(CART_CACHE_KEY + item.getStudentId());
        }
        
        return shoppingCartMapper.updateById(shoppingCart);
    }
    
    /**
     * 切换选中状态
     */
    public int toggleSelect(Integer id) {
        ShoppingCart item = shoppingCartMapper.selectById(id);
        if (item == null) {
            throw new RuntimeException("购物车项不存在");
        }
        
        ShoppingCart updateItem = new ShoppingCart();
        updateItem.setId(id);
        updateItem.setSelected(item.getSelected() == 1 ? 0 : 1);
        
        redisUtil.delete(CART_CACHE_KEY + item.getStudentId());
        
        return shoppingCartMapper.updateById(updateItem);
    }
    
    /**
     * 获取购物车列表
     */
    public List<ShoppingCart> getCartList(Integer studentId) {
        // 先查缓存
        String key = CART_CACHE_KEY + studentId;
        if (redisUtil.hasKey(key)) {
            return (List<ShoppingCart>) redisUtil.get(key);
        }
        
        // 缓存没有，查数据库
        List<ShoppingCart> cartList = shoppingCartMapper.selectByStudentId(studentId);
        if (cartList != null && !cartList.isEmpty()) {
            // 写入缓存
            redisUtil.set(key, cartList, CART_CACHE_EXPIRE);
        }
        return cartList;
    }
    
    /**
     * 清空购物车
     */
    public int clearCart(Integer studentId) {
        redisUtil.delete(CART_CACHE_KEY + studentId);
        return shoppingCartMapper.deleteByStudentId(studentId);
    }
}
