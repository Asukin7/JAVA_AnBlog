# 安安博客AnBlog

## 前言

本项目为个人学习与自用项目，其中存在许多不足之处请谅解。

## 项目介绍

本项目为一个前后端分离的多用户博客系统，后端使用SpringBoot + Mybatis + Shiro框架、Mysql数据库、Redis缓存实现。

前端地址：https://github.com/Asukin7/WEB_AnBlog

### 角色与权限

#### 管理员

- 用户管理
  - 封禁
  - 添加
  - 授权
  - 删除
  - 查询
- 博客管理（未做）
  - 删除
  - 查询
- 博客评论管理（未做）
  - 删除
  - 查询
- 博客模块管理（未做）
  - 添加
  - 修改
  - 删除
  - 查询
- 通知管理（未做）
  - 推送
  - 撰写
  - 删除
  - 查询
- 数据监控（未做）
- 操作记录（未做）

#### 用户

- 账号管理
  - 邮箱管理
  - 修改密码
  - 销毁账号
- 资料管理
  - 头像
  - 昵称
  - 简介
  - 赞赏码
- 博客管理
  - 发布
  - 撰写
  - 修改
  - 删除
  - 查询
- 博客评论管理
  - 回复
  - 删除
- 博客点赞管理
  - 点赞
  - 删除
- 通知管理（未做）
  - 查看
  - 已读
  - 删除

#### 游客

- 登录
- 注册
- 找回密码
- 查询博客
- 预览博客
- 赞赏博客
- 查看博主信息（未做）

### 数据库建库语句

``` mysql 
CREATE DATABASE `an_blog` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `an_blog`;

CREATE TABLE `user` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`enabled` INT ( 11 ) DEFAULT 1 COMMENT '0表示封禁，1表示正常',
	`username` VARCHAR ( 16 ) UNIQUE DEFAULT NULL,
	`email` VARCHAR ( 32 ) UNIQUE DEFAULT NULL,
	`password` VARCHAR ( 256 ) DEFAULT NULL,
	`nickname` VARCHAR ( 32 ) DEFAULT NULL,
	`introduction` VARCHAR ( 256 ) DEFAULT NULL,
	`profilePhoto` VARCHAR ( 256 ) DEFAULT NULL,
	`appreciationCode` VARCHAR ( 256 ) DEFAULT NULL,
	PRIMARY KEY ( `id` )
);

CREATE TABLE `role` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR ( 32 ) DEFAULT NULL,
	PRIMARY KEY ( `id` )
);

INSERT INTO `role` VALUES (1, 'admin');
INSERT INTO `role` VALUES (2, 'user');

CREATE TABLE `user_role` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`userId` INT ( 11 ) DEFAULT NULL,
	`roleId` INT ( 11 ) DEFAULT 2,
	FOREIGN KEY ( `userId` ) REFERENCES `user` ( `id` ),
	FOREIGN KEY ( `roleId` ) REFERENCES `role` ( `id` ),
	PRIMARY KEY ( `id` )
);

CREATE TABLE `category` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR ( 32 ) DEFAULT NULL,
	PRIMARY KEY ( `id` )
);

CREATE TABLE `tags` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR ( 32 ) DEFAULT NULL,
	PRIMARY KEY ( `id` )
);

CREATE TABLE `blog` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`userId` INT ( 11 ) DEFAULT NULL,
	`categoryId` INT ( 11 ) DEFAULT NULL,
	`state` INT ( 11 ) DEFAULT NULL COMMENT '0表示草稿箱，1表示已发表，2表示已删除',
	`title` VARCHAR ( 256 ) DEFAULT NULL,
	`mdContent` TEXT COMMENT 'md源码',
	`htmlContent` TEXT COMMENT 'html源码',
	`summary` TEXT,
	`publishDate` DATETIME DEFAULT NULL,
	`editDate` DATETIME DEFAULT NULL,
    `viewNumber` INT ( 11 ) DEFAULT 0,
	FOREIGN KEY ( `userId` ) REFERENCES `user` ( `id` ),
	FOREIGN KEY ( `categoryId` ) REFERENCES `category` ( `id` ),
	PRIMARY KEY ( `id` )
);

CREATE TABLE `blog_tags` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`blogId` INT ( 11 ) DEFAULT NULL,
	`tagsId` INT ( 11 ) DEFAULT NULL,
	FOREIGN KEY ( `blogId` ) REFERENCES `blog` ( `id` ),
	FOREIGN KEY ( `tagsId` ) REFERENCES `tags` ( `id` ),
	PRIMARY KEY ( `id` )
);

CREATE TABLE `comment` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`userId` INT ( 11 ) DEFAULT NULL,
	`blogId` INT ( 11 ) DEFAULT NULL,
	`rootId` INT ( 11 ) DEFAULT 0 COMMENT '父级的评论ID',
	`dialogId` INT ( 11 ) DEFAULT 0 COMMENT '回复的评论的用户ID',
	`content` TEXT,
	`publishDate` DATETIME DEFAULT NULL,
	FOREIGN KEY ( `userId` ) REFERENCES `user` ( `id` ),
	FOREIGN KEY ( `blogId` ) REFERENCES `blog` ( `id` ),
	PRIMARY KEY ( `id` )
);

CREATE TABLE `blog_like` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`userId` INT ( 11 ) DEFAULT NULL,
	`blogId` INT ( 11 ) DEFAULT NULL,
	`date` DATETIME DEFAULT NULL,
	FOREIGN KEY ( `userId` ) REFERENCES `user` ( `id` ),
	FOREIGN KEY ( `blogId` ) REFERENCES `blog` ( `id` ),
	PRIMARY KEY ( `id` )
);

CREATE TABLE `comment_like` (
	`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
	`userId` INT ( 11 ) DEFAULT NULL,
	`blogId` INT ( 11 ) DEFAULT NULL,
	`commentId` INT ( 11 ) DEFAULT NULL,
	`rootId` INT ( 11 ) DEFAULT 0,
	`date` DATETIME DEFAULT NULL,
	FOREIGN KEY ( `userId` ) REFERENCES `user` ( `id` ),
	FOREIGN KEY ( `blogId` ) REFERENCES `blog` ( `id` ),
	FOREIGN KEY ( `commentId` ) REFERENCES `comment` ( `id` ),
	PRIMARY KEY ( `id` )
);
```

### 接口

| URI         | Method | 权限  |
| ----------- | ------ | ----- |
| /tourist/** | *      |       |
| /user/**    | *      | user  |
| /admin/**   | *      | admin |

