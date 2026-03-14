package com.leon.service;

import com.leon.entity.Merchant;
import com.leon.mapper.MerchantMapper;
import com.leon.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商家服务
 */
@Service
public class MerchantService {
    
    @Autowired
    private MerchantMapper merchantMapper;
    
    @Autowired
    private RedisUtil redisUtil;
    
    private static final String MERCHANT_CACHE_KEY = "merchant:";
    private static final Integer MERCHANT_CACHE_EXPIRE = 3600; // 1 小时
    
    public int insert(Merchant merchant) {
        return merchantMapper.insert(merchant);
    }
    
    public int updateById(Merchant merchant) {
        // 更新数据库后删除缓存
        redisUtil.delete(MERCHANT_CACHE_KEY + merchant.getId());
        return merchantMapper.updateById(merchant);
    }
    
    public Merchant selectById(Integer id) {
        // 先查缓存
        String key = MERCHANT_CACHE_KEY + id;
        if (redisUtil.hasKey(key)) {
            return redisUtil.get(key, Merchant.class);
        }
        
        // 缓存没有，查数据库
        Merchant merchant = merchantMapper.selectById(id);
        if (merchant != null) {
            // 写入缓存
            redisUtil.set(key, merchant, MERCHANT_CACHE_EXPIRE);
        }
        return merchant;
    }
    
    public List<Merchant> selectList(Merchant merchant) {
        return merchantMapper.selectList(merchant);
    }
}
