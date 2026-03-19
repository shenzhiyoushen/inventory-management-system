-- 创建数据库
CREATE DATABASE IF NOT EXISTS demo_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE demo_db;

-- 创建用户表
CREATE TABLE IF NOT EXISTS sys_user (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                        username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(50) NOT NULL COMMENT '密码',
    phone VARCHAR(20) DEFAULT '' COMMENT '手机号',
    email VARCHAR(50) DEFAULT '' COMMENT '邮箱',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
    ) COMMENT '用户表';

-- 插入测试数据（用户名：admin，密码：123456）
INSERT INTO sys_user (username, password, phone, email) VALUES
                                                            ('admin', '123456', '13800138000', 'admin@example.com'),
                                                            ('test', '123456', '13900139000', 'test@example.com');