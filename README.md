# MotoTrip Java 微服务

基于 Spring Boot 3 构建的摩托车骑行管理平台，从 NestJS 项目 1:1 功能还原。

## 技术栈

| 组件 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.12 | 微服务底座 |
| Spring Cloud | 2025.0.0 | 服务规范 |
| Spring Cloud Alibaba | 2025.0.0.0 | 服务治理 |
| Nacos | 2.4.0 | 注册/配置中心 |
| MyBatis-Plus | 3.5.16 | ORM 框架 |
| Druid | 1.2.28 | 连接池 |
| Knife4j | 4.5.0 | API 文档 |
| SpringDoc | 2.8.9 | OpenAPI 3.0 支持 |
| Redisson | 3.50.0 | Redis 客户端 |
| RocketMQ | 5.2.x | 消息队列 |
| PostgreSQL | 18 | 数据库 |
| Java | 17 | 基础版本 |

## 项目结构

```
mototrip-java/
├── pom.xml                          # 父 POM，统一版本管理
├── docker-compose.yml               # PG18, Redis7.2, Nacos, RocketMQ, Sentinel
├── init-db.sql                      # 22张表 + 索引 + 触发器
├── .gitignore
├── mototrip-common/                 # 公共模块
│   ├── client/                      # Feign 客户端定义
│   ├── config/                      # 公共配置
│   ├── context/                     # 用户上下文
│   ├── dto/                         # 公共 DTO
│   ├── enums/                       # 枚举定义
│   ├── exception/                   # 异常处理
│   ├── interceptor/                 # 拦截器
│   ├── response/                    # 统一响应
│   └── util/                        # 工具类
├── mototrip-gateway/                # API 网关 :8080
├── mototrip-auth-server/            # 认证服务 :8100
├── mototrip-user-center/            # 用户中心 :8110
├── mototrip-route-service/          # 路线服务 :8120
├── mototrip-trip-service/           # 行程服务 :8130
├── mototrip-social-service/         # 社交服务 :8140
├── mototrip-map-safety-service/     # 地图安全服务 :8150
├── mototrip-team-service/           # 车队服务 :8160
└── mototrip-weather-service/        # 天气服务 :8170
```

## 快速开始

### 前置要求

- JDK 17+
- Maven 3.6+
- Docker & Docker Compose

### 启动基础设施

```bash
# 启动 PostgreSQL、Redis、Nacos、RocketMQ
docker-compose up -d
```

### 初始化数据库

```bash
# 使用 init-db.sql 初始化数据库
# 数据库名: mototrip
```

### 编译项目

```bash
mvn clean compile
```

### 启动服务

建议按以下顺序启动服务：

1. **Nacos** (已通过 docker-compose 启动)
2. **mototrip-gateway** (端口 8080)
3. **mototrip-auth-server** (端口 8100)
4. **mototrip-user-center** (端口 8110)
5. 其他业务服务

## API 文档

启动所有服务后，访问聚合 API 文档：

```
http://localhost:8080/doc.html
```

各服务的独立文档地址：

- Gateway: http://localhost:8080/doc.html
- Auth Service: http://localhost:8100/doc.html
- User Center: http://localhost:8110/doc.html
- Route Service: http://localhost:8120/doc.html
- Trip Service: http://localhost:8130/doc.html
- Social Service: http://localhost:8140/doc.html
- Map Safety Service: http://localhost:8150/doc.html
- Team Service: http://localhost:8160/doc.html
- Weather Service: http://localhost:8170/doc.html

## 服务端口

| 服务 | 端口 | 说明 |
|------|------|------|
| gateway | 8080 | API 网关 |
| auth-server | 8100 | 认证服务 |
| user-center | 8110 | 用户中心 |
| route-service | 8120 | 路线服务 |
| trip-service | 8130 | 行程服务 |
| social-service | 8140 | 社交服务 |
| map-safety-service | 8150 | 地图安全服务 |
| team-service | 8160 | 车队服务 |
| weather-service | 8170 | 天气服务 |

## 核心特性

### 1. 用户认证
- 用户注册（Bcrypt 加密）
- JWT 登录 + Redis 存储
- 微信登录
- 获取当前用户信息
- 用户登出

### 2. 用户中心
- 用户资料 CRUD
- 用户模式切换（新手/老手/乘客）

### 3. 路线服务
- 路线 CRUD + 分页
- 航点管理
- 评论功能
- 点赞（重复检查 409）

### 4. 行程服务
- 行程 CRUD
- 日记功能
- 行前准备清单（toggle isPacked）
- 行程分享

### 5. 社交服务
- 帖子发布
- 评论（嵌套回复）
- 足迹记录
- 足迹成就
- 点赞

### 6. 地图安全服务
- 离线地图（瓦片坐标生成 + RocketMQ 异步下载）
- 危险区域（Haversine 附近搜索）
- 禁停区域
- 警告聚合（夜间+危险+禁停）
- 位置共享（8位 UUID shareCode）

### 7. 车队服务
- 车队 CRUD
- 成员管理（pending/approved 状态流转）

### 8. 天气服务
- Open-Meteo API 代理
- 25 个 WMO 天气码映射

## 架构设计

### 用户上下文传递链

```
Gateway JWT 验证 → 设置 X-User-Id/X-Username 头
  → UserContextInterceptor 提取到 ThreadLocal
    → FeignUserContextInterceptor 传播到下游 Feign 调用
```

### 统一响应格式

```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

### Sentinel 资源分类

- **GET/HEAD/OPTIONS** → `global-api` (100 QPS)
- **/auth/** 路径 → `auth-api` (10 QPS)
- **其他写操作** → `write-api` (20 QPS)

### 数据库策略

- 单库 `mototrip`，`mt_` 前缀
- `mt_user_likes` 表在 route/trip/social 三个服务中各自定义实体（按 `target_type` 区分）
- `mt_users` 表在 auth 和 user-center 各自定义

## 项目统计

| 指标 | 数量 |
|------|------|
| 模块 | 10个 |
| Java 文件 | ~235个 |
| API 端点 | 96个（GET:40, POST:30, PUT:12, DELETE:14） |
| 数据库表 | 22张 |
| Feign 客户端 | 6个 |
| MQ 消费者 | 4个 |

## 开发说明

### 环境变量

| 变量名 | 默认值 | 说明 |
|--------|--------|------|
| DB_USERNAME | postgres | 数据库用户名 |
| DB_PASSWORD | postgres | 数据库密码 |
| REDIS_HOST | localhost | Redis 主机 |
| REDIS_PORT | 6379 | Redis 端口 |
| NACOS_ADDR | localhost:8848 | Nacos 地址 |
| ROCKETMQ_ADDR | localhost:9876 | RocketMQ 地址 |
| JWT_SECRET | ... | JWT 密钥 |
| JWT_EXPIRATION | 604800 | JWT 过期时间（秒） |

### 代码规范

- 包名：`com.mototrip.{service}.{module}`
- 表名前缀：`mt_`
- 统一响应：`Result<T>`
- 异常处理：继承 `BusinessException`

## 许可证

Apache License 2.0

## 致谢

- 原始 NestJS 项目：[LiXuD/mototrip-api](https://github.com/LiXuD/mototrip-api)
