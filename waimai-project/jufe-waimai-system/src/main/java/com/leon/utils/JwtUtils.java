package com.leon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * 注意：本实现基于JJWT 0.9.x版本（与测试用例一致）
 * 若使用JJWT 0.10.0+，需将密钥转换为SecretKey（参考注释说明）
 */
public class JwtUtils {

    // 与测试类完全一致的密钥（字符串形式，UTF-8编码）
    private static final String SECRET_KEY = "aXRoZWltYQ==";

    // 令牌过期时间：12小时（毫秒）
    private static final Integer EXPIRATION_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     *
     * @param claims 自定义载荷数据（如用户ID、用户名等）
     * @return JWT字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 使用与测试一致的密钥
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌
     *
     * @param token JWT字符串
     * @return Claims对象（包含原始载荷数据）
     * @throws RuntimeException 解析失败时抛出（含具体原因）
     */
    public static Claims parseToken(String token) throws Exception {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 使用与生成时相同的密钥
                .parseClaimsJws(token)
                .getBody();

    }

    /*
     * 【重要提示】JJWT版本兼容说明：
     *
     * 1. 当前实现适用于 JJWT 0.9.x 版本（与题目测试用例完全一致）
     *
     * 2. 若项目使用 JJWT 0.10.0+ 版本，请替换以下两处：
     *    - 生成令牌：.signWith(
     *          Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)),
     *          SignatureAlgorithm.HS256)
     *    - 解析令牌：.setSigningKey(
     *          Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
     *    并添加导入：import io.jsonwebtoken.security.Keys; import java.nio.charset.StandardCharsets;
     *
     * 3. 密钥说明：SECRET_KEY "aXRoZWltYQ==" 是Base64编码字符串，
     *    但JJWT 0.9.x将其视为普通UTF-8字符串处理（非解码使用），
     *    与测试用例行为严格一致。
     */
}