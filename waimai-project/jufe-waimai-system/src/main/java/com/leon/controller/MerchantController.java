package com.leon.controller;

import com.leon.common.Result;
import com.leon.entity.Merchant;
import com.leon.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商家控制器
 */
@RestController
@RequestMapping("/merchants")
// @CrossOrigin(origins = "*") 注解用于启用跨域资源共享 (CORS)。
// 它允许来自任何来源（域名、协议或端口）的前端应用访问该控制器下的所有接口。
// 这在前后端分离的开发环境中非常常见，用于解决浏览器因同源策略而拦截请求的问题。
@CrossOrigin(origins = "*")
public class MerchantController {
    
    @Autowired
    private MerchantService merchantService;
    
    /**
     * 获取商家列表
     */
    @GetMapping
    public Result<List<Merchant>> list(Merchant merchant) {
        return Result.success(merchantService.selectList(merchant));
    }
    
    /**
     * 根据 ID 获取商家详情
     */
    @GetMapping("/{id}")
    public Result<Merchant> getById(@PathVariable Integer id) {
        return Result.success(merchantService.selectById(id));
    }
    
    /**
     * 创建商家
     */
    @PostMapping
    public Result<Integer> create(@RequestBody Merchant merchant) {
        return Result.success(merchantService.insert(merchant));
    }
    
    /**
     * 更新商家信息
     */
    @PutMapping
    public Result<Integer> update(@RequestBody Merchant merchant) {
        return Result.success(merchantService.updateById(merchant));
    }
}
