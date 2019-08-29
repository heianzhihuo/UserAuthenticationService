# 国泰君安实习的简单项目

## 项目的用户认证微服务部分

1. Springboot框架
2. Dubbo框架
3. zookeeper注册中心
4. mysql永久存储用户信息和密码
5. redis临时存储session信息(token)
6. mybatis管理mysql

### 用户认证功能

- 用户密码登录
- 查询已经登陆的用户信息
- 查询当前用户的等级
- 查询用户的账户余额
- 查询用户账户流水
- 用户资金转移（即从一个用户到另一个用户）

### 各个模块作用

- user-api：用户认证服务的相关接口以及实体类
- user-service：用户认证服务的实现部分，mapper用于mybatis数据库映射、redis用于redis的相关配置类、rocketmq用于rocketmq的配置，serviceImpl实现用户认证服务的接口。mysql、redis、dubbo配置在application.yml中，spring/logback.xml配置日志
- user-api：用户认证服务的web，实现restful api，用于自己测试

## 项目名称：基于微服务的客户认证、产品购买流程

操作系统：
Linux

工具：
SpringBoot、Dubbo、Nginx、ElasticSearch、Kibana、Logstash、RocketMQ、MySql、Redis、Zookeeper、Kafka

环境搭建：
1、微服务环境搭建：SpringBoot、Dubbo（Zookeeper注册中心3台等）、Nginx反向代理
2、elk搭建(3 ES + 1 Kibana + 3 Logstash)
3、rocketMQ搭建（2Master-2Slave-2NameSrv）
4、数据库搭建（MySql、Redis集群）

流程：
客户登录，查询产品，选择产品进行下单，查看下单结果

用户登录：
	登录token使用Redis做缓存，超时时间1小时
查询产品：
	查询所有产品列表，涉及产品ID、产品名称、产品描述、产品价格、产品库存等
下单：
	客户选择一个或多个产品进行下单提交，返回下单结果（成功or失败），成功进行正确扣款，失败进行回滚（发送RocketMQ消息，进行事务补偿，保证产品库存及客户账户余额数据正确）
	每日凌晨1点进行定时任务补偿


涉及服务：
用户、认证服务（1000万客户）
产品服务(1000产品，事务补偿)
订单服务（支持1000万客户，分库分表或分区表）
流程整合

场景：
客户单笔下单
多客户高并发下单
保证高可用



逻辑架构图和物理部署图、数据库设计

# 各部分作用说明

## Mysql 基于磁盘的关系型关系型数据库管理系统
## Redis 基于内存的日执行、key-value型数据库，常用于缓存，计算器，排行榜，限速器（防止用户疯狂的点击带来的不便）

## ELK是一套完整的日志解决方案
- EastiSearch是基于Lucene开发的分布式存储检引擎，用来存储各类日志;
- Logstash对日志进行收集、分析，并将其存储供以后使用
- 为Logstah和ElasticSearch提供用于日志展示的Web界面，还用于帮助汇总、分析和搜索重要日志数据

## RocketMQ，RocketMQ是一款分布式消息中间件

- NameServer 在rocketMQ中相当于zookeeper在dubbo中的作用，是用来存储broke和client的注册信息的

## springboot+zookeeper+dubbo做后端接口

## Nginx：用于前后端分离，负载均衡

## Mysql做数据库持久化层