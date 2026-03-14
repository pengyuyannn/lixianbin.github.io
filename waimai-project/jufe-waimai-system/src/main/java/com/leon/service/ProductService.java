package com.leon.service;

import com.leon.entity.Product;
import com.leon.mapper.ProductMapper;
import com.leon.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务
 */
@Service
public class ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private RedisUtil redisUtil;
    
    private static final String PRODUCT_CACHE_KEY = "product:";
    private static final String PRODUCT_LIST_CACHE_KEY = "products:merchant:";
    private static final Integer PRODUCT_CACHE_EXPIRE = 1800; // 30 分钟
    
    public int insert(Product product) {
        return productMapper.insert(product);
    }
    
    public int updateById(Product product) {
        // 更新数据库后删除缓存
        redisUtil.delete(PRODUCT_CACHE_KEY + product.getId());
        redisUtil.delete(PRODUCT_LIST_CACHE_KEY + product.getMerchantId());
        return productMapper.updateById(product);
    }
    
    public Product selectById(Integer id) {
        // 先查缓存
        String key = PRODUCT_CACHE_KEY + id;
        if (redisUtil.hasKey(key)) {
            return (Product) redisUtil.get(key);
        }
        
        // 缓存没有，查数据库
        Product product = productMapper.selectById(id);
        if (product != null) {
            // 写入缓存
            redisUtil.set(key, product, PRODUCT_CACHE_EXPIRE);
        }
        return product;
    }
    
    public List<Product> selectByMerchantId(Integer merchantId) {
        // 先查缓存
        String key = PRODUCT_LIST_CACHE_KEY + merchantId;
        if (redisUtil.hasKey(key)) {
            return (List<Product>) redisUtil.get(key);
        }
        
        // 缓存没有，查数据库
        List<Product> products = productMapper.selectByMerchantId(merchantId);
        if (products != null && !products.isEmpty()) {
            // 写入缓存
            redisUtil.set(key, products, PRODUCT_CACHE_EXPIRE);
        }
        return products;
    }
    
    public List<Product> selectList(Product product) {
        return productMapper.selectList(product);
    }
    
    public boolean updateStock(Integer productId, Integer stock) {
        // 更新库存后删除缓存
        redisUtil.delete(PRODUCT_CACHE_KEY + productId);
        return productMapper.updateStock(productId, stock) > 0;
    }
}
