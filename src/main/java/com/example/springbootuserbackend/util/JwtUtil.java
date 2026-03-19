package com.example.springbootuserbackend.util;

import com.example.springbootuserbackend.common.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

/**
 * 优化后的JWT工具类（符合HS256密钥规范）
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire;

    // 生成安全密钥（仅需执行一次，复制结果到配置文件）
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("生成的256位密钥（Base64）：" + base64Key);
    }

    // 核心修复：将字符串密钥转换为256位的SecretKey
    private SecretKey getSecretKey() {
        // 1. 如果密钥是Base64编码，直接解码；否则转换为字节数组（确保256位）
        byte[] keyBytes;
        try {
            keyBytes = Base64.getDecoder().decode(secret);
        } catch (IllegalArgumentException e) {
            // 非Base64编码，直接用原始字符串的字节
            keyBytes = secret.getBytes();
        }

        // 2. 确保密钥长度≥256位，不足则抛出明确错误
        if (keyBytes.length < 32) {
            throw new BusinessException("JWT密钥长度必须≥256位（32个字符）");
        }

        // 3. 生成符合HS256要求的SecretKey
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 生成token（改用SecretKey）
    public String generateToken(String username) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expire);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256) // 使用规范的SecretKey
                .compact();
    }

    // 解析token获取用户名（改用SecretKey）
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey()) // 使用规范的SecretKey
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new BusinessException(401, "token无效或已过期：" + e.getMessage());
        }
    }

    // 验证token是否有效（改用SecretKey）
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}