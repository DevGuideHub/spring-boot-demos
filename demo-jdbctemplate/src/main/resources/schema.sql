-- ###########################################################################
-- 用户表
-- 存储系统用户基本信息
-- ###########################################################################
CREATE TABLE IF NOT EXISTS user (
    -- 用户唯一标识，自增主键
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    -- 用户姓名，非空约束
    name VARCHAR(50) NOT NULL COMMENT '用户名',
    -- 用户年龄，允许为空
    age INT COMMENT '年龄',
    -- 用户邮箱，用于登录和通知
    email VARCHAR(100) COMMENT '邮箱'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ###########################################################################
-- 索引说明
-- ###########################################################################
-- 为邮箱字段创建唯一索引，确保邮箱不重复
-- CREATE UNIQUE INDEX uk_user_email ON user(email);

-- 为年龄字段创建普通索引，优化按年龄查询
-- CREATE INDEX idx_user_age ON user(age);