CREATE Database DoubleToken;
USE DoubleToken;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
                       `id` BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
                       `name` VARCHAR(20) NULL COMMENT '用户昵称',
                       `password` varchar(255) not null comment '用户密码',
                       `create_time` DATETIME NOT NULL DEFAULT Now() COMMENT '创建时间',
                       `update_time` DATETIME NOT NULL DEFAULT Now() on update NOW() COMMENT '修改时间'
)  ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;
