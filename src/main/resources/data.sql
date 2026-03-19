-- 插入测试数据（用户名：admin，密码：123456）


-- 插入admin用户：仅当username='admin'不存在时
INSERT INTO sys_user (username, password, phone, email)
SELECT 'admin', '123456', '13800138000', 'admin@example.com'
    WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'admin');

-- 插入test用户：仅当username='test'不存在时
INSERT INTO sys_user (username, password, phone, email)
SELECT 'test', '123456', '13900139000', 'test@example.com'
    WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE username = 'test');