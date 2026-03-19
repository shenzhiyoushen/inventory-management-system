-- 核心保留：IF NOT EXISTS → 表不存在才创建，否则跳过
CREATE TABLE IF NOT EXISTS sys_user (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- H2支持AUTO_INCREMENT，去掉内联COMMENT（H2不兼容）
                                        username VARCHAR(50) NOT NULL,        -- 保留非空约束，去掉内联COMMENT
    password VARCHAR(50) NOT NULL,        -- 保留非空约束
    phone VARCHAR(20) DEFAULT '',         -- 保留默认空字符串
    email VARCHAR(50) DEFAULT '',         -- 保留默认空字符串
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 替换DATETIME为TIMESTAMP（H2原生支持）
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP   -- 去掉ON UPDATE（H2不支持，代码层面兜底）
    );

-- 可选：单独添加表/字段注释（H2支持的语法，不影响表创建逻辑）
COMMENT ON TABLE sys_user IS '用户表';
COMMENT ON COLUMN sys_user.id IS '主键ID';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.create_time IS '创建时间';
COMMENT ON COLUMN sys_user.update_time IS '更新时间';