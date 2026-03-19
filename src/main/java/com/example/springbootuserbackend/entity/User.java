package com.example.springbootuserbackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("sys_user")
public class User {
    // 主键ID
    @TableId(type = IdType.AUTO)
    private Long id;
    // 用户名
    private String username;
    // 密码（演示用，实际需加密）
    private String password;
    // 手机号
    private String phone;
    // 邮箱
    private String email;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}