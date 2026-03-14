package com.leon.mapper;

import com.leon.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 Mapper
 */
@Mapper
public interface UserMapper {
    
    int insert(User user);
    
    int updateById(User user);
    
    User selectById(@Param("id") Integer id);
    
    User selectByUsername(@Param("username") String username);
    
    User selectByPhone(@Param("phone") String phone);
    
    User selectByPhoneAndRoleType(@Param("phone") String phone, @Param("roleType") Integer roleType);
    
    int deleteById(@Param("id") Integer id);
}
