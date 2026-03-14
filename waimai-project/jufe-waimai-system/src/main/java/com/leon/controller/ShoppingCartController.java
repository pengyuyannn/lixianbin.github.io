package com.leon.controller;

import com.leon.common.Result;
import com.leon.entity.ShoppingCart;
import com.leon.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车控制器
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class ShoppingCartController {
    
    @Autowired
    private ShoppingCartService shoppingCartService;
    
    /**
     * 添加商品到购物车
     */
    @PostMapping
    public Result<Integer> addToCart(@RequestBody ShoppingCart shoppingCart) {
        return Result.success(shoppingCartService.addToCart(shoppingCart));
    }
    
    /**
     * 更新购物车商品数量
     */
    @PutMapping("/{id}")
    public Result<Integer> updateQuantity(@PathVariable Integer id, @RequestParam Integer quantity) {
        return Result.success(shoppingCartService.updateQuantity(id, quantity));
    }
    
    /**
     * 切换选中状态
     */
    @PutMapping("/{id}/toggle")
    public Result<Integer> toggleSelect(@PathVariable Integer id) {
        return Result.success(shoppingCartService.toggleSelect(id));
    }
    
    /**
     * 获取购物车列表
     */
    @GetMapping("/student/{studentId}")
    public Result<List<ShoppingCart>> getCartList(@PathVariable Integer studentId) {
        return Result.success(shoppingCartService.getCartList(studentId));
    }
    
    /**
     * 清空购物车
     */
    @DeleteMapping("/student/{studentId}")
    public Result<Integer> clearCart(@PathVariable Integer studentId) {
        return Result.success(shoppingCartService.clearCart(studentId));
    }
}
