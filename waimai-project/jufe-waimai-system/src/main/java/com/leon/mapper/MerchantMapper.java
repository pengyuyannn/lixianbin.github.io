package com.leon.mapper;

import com.leon.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商家 Mapper
 */
@Mapper
public interface MerchantMapper {
    
    int insert(Merchant merchant);
    
    int updateById(Merchant merchant);
    
    Merchant selectById(@Param("id") Integer id);
    
    Merchant selectByUserId(@Param("userId") Integer userId);
    
    List<Merchant> selectList(Merchant merchant);
    
    int deleteById(@Param("id") Integer id);
}
