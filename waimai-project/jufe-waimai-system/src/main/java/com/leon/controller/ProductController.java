package com.leon.controller;

import com.leon.common.Result;
import com.leon.entity.Product;
import com.leon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * 获取商品列表（根据商家 ID）
     */
    @GetMapping("/merchant/{merchantId}")
    public Result<List<Product>> listByMerchant(@PathVariable Integer merchantId) {
        return Result.success(productService.selectByMerchantId(merchantId));
    }
    
    /**
     * 获取商品列表（带筛选条件）
     */
    @GetMapping
    public Result<List<Product>> list(Product product) {
        return Result.success(productService.selectList(product));
    }
    
    /**
     * 根据 ID 获取商品详情
     */
    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Integer id) {
        return Result.success(productService.selectById(id));
    }
    
    /**
     * 创建商品
     */
    @PostMapping
    public Result<Integer> create(@RequestBody Product product) {
        return Result.success(productService.insert(product));
    }
    
    /**
     * 更新商品信息
     */
    @PutMapping
    public Result<Integer> update(@RequestBody Product product) {
        return Result.success(productService.updateById(product));
    }
    
    /**
     * 更新商品库存
     */
    @PutMapping("/{id}/stock")
    public Result<Boolean> updateStock(@PathVariable Integer id, @RequestParam Integer stock) {
        return Result.success(productService.updateStock(id, stock));
    }
}
