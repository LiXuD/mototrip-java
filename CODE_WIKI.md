# MotoTrip 项目 Code Wiki

## 1. 项目概览

MotoTrip 是一个基于 Spring Boot 3 构建的摩托车骑行管理平台，从 NestJS 项目 1:1 功能还原。该项目采用微服务架构，提供了完整的摩托车骑行相关功能，包括用户认证、路线管理、行程规划、社交互动、地图安全等核心功能。

## 2. 技术栈

| 组件 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.12 | 微服务底座 |
| Spring Cloud | 2025.0.0 | 服务规范 |
| Spring Cloud Alibaba | 2025.0.0.0 | 服务治理 |
| Nacos | 2.4.0 | 注册/配置中心 |
| MyBatis-Plus | 3.5.16 | ORM 框架 |
| Druid | 1.2.28 | 连接池 |
| Knife4j | 4.5.0 | API 文档 |
| SpringDoc | 2.5.0 | OpenAPI 3.0 支持 |
| Redisson | 3.50.0 | Redis 客户端 |
| RocketMQ | 5.2.x | 消息队列 |
| PostgreSQL | 18 | 数据库 |
| Java | 17 | 基础版本 |
| Hutool | 5.8.37 | 工具库 |
| MapStruct | 1.6.3 | 对象映射 |
| Lombok | 1.18.38 | 代码简化 |
| JWT | 0.12.6 | JSON Web Token |

## 3. 项目结构

```
mototrip-java/
├── pom.xml                          # 父 POM，统一版本管理
├── docker-compose.yml               # Redis7.2, Nacos, RocketMQ, Sentinel
├── init-db.sql                      # 22张表 + 索引 + 触发器
├── test-data.sql                    # 测试数据
├── start-all-services.sh            # 一键启动所有服务脚本
├── stop-all-services.sh             # 一键停止所有服务脚本
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

## 4. 核心模块分析

### 4.1 公共模块 (mototrip-common)

公共模块提供了整个项目的基础组件和工具类，包括：

- **Feign 客户端**：定义了各服务间的远程调用接口
- **统一响应格式**：`Result<T>` 类封装了统一的 API 响应格式
- **异常处理**：定义了各种业务异常类
- **用户上下文**：`UserContext` 用于在服务间传递用户信息
- **工具类**：包括 JWT 工具、JSON 工具、地理工具等
- **枚举定义**：各种业务枚举类型

### 4.2 API 网关 (mototrip-gateway)

API 网关负责请求路由、认证鉴权和流量控制，主要功能：

- **JWT 认证**：验证用户令牌并提取用户信息
- **路径白名单**：对特定路径和方法免鉴权
- **请求转发**：将请求转发到相应的微服务
- **文档聚合**：聚合所有微服务的 API 文档
- **路由配置**：使用 StripPrefix=1 过滤器，移除 /api 前缀后转发到下游服务

### 4.3 认证服务 (mototrip-auth-server)

认证服务处理用户认证相关功能：

- **用户注册**：创建新用户并加密密码
- **用户登录**：验证用户凭证并生成 JWT 令牌
- **微信登录**：通过微信小程序登录
- **获取当前用户信息**：根据用户 ID 获取用户详情
- **用户登出**：清除用户的 refresh token

### 4.4 用户中心 (mototrip-user-center)

用户中心管理用户资料和用户模式：

- **用户资料 CRUD**：创建、读取、更新、删除用户资料
- **用户模式切换**：切换用户模式（新手/老手/乘客）

### 4.5 路线服务 (mototrip-route-service)

路线服务管理骑行路线：

- **路线 CRUD**：创建、读取、更新、删除路线
- **航点管理**：管理路线的航点信息
- **评论功能**：对路线进行评论
- **点赞功能**：对路线进行点赞

### 4.6 行程服务 (mototrip-trip-service)

行程服务管理用户的骑行行程：

- **行程 CRUD**：创建、读取、更新、删除行程
- **日记功能**：管理行程的日记
- **行前准备清单**：管理行程的准备事项
- **行程分享**：分享行程信息

### 4.7 社交服务 (mototrip-social-service)

社交服务提供社交互动功能：

- **帖子发布**：发布骑行相关帖子
- **评论功能**：对帖子进行评论（支持嵌套回复）
- **足迹记录**：记录用户的骑行足迹
- **足迹成就**：根据足迹生成成就
- **点赞功能**：对帖子进行点赞

### 4.8 地图安全服务 (mototrip-map-safety-service)

地图安全服务提供地图相关的安全功能：

- **离线地图**：生成和管理离线地图瓦片
- **危险区域**：管理危险区域信息
- **禁停区域**：管理禁停区域信息
- **警告聚合**：聚合各种警告信息
- **位置共享**：实现用户位置共享

### 4.9 车队服务 (mototrip-team-service)

车队服务管理骑行车队：

- **车队 CRUD**：创建、读取、更新、删除车队
- **成员管理**：管理车队成员，包括成员状态流转

### 4.10 天气服务 (mototrip-weather-service)

天气服务提供天气相关信息：

- **Open-Meteo API 代理**：代理调用 Open-Meteo API 获取天气信息
- **天气码映射**：25 个 WMO 天气码映射

## 5. 关键类与函数

### 5.1 公共模块

#### Result&lt;T&gt; 类

**功能**：统一 API 响应格式

**位置**：[mototrip-common/src/main/java/com/mototrip/common/response/Result.java](file:///Users/lixd/IdeaProjects/Git/mototrip/mototrip-java/mototrip-common/src/main/java/com/mototrip/common/response/Result.java)

**主要方法**：
- `success(T data)`：返回成功响应，包含数据
- `success()`：返回成功响应，无数据
- `error(int code, String message)`：返回错误响应

### 5.2 API 网关

#### JwtAuthGlobalFilter 类

**功能**：JWT 认证全局过滤器

**位置**：[mototrip-gateway/src/main/java/com/mototrip/gateway/filter/JwtAuthGlobalFilter.java](file:///Users/lixd/IdeaProjects/Git/mototrip/mototrip-java/mototrip-gateway/src/main/java/com/mototrip/gateway/filter/JwtAuthGlobalFilter.java)

**主要方法**：
- `filter(ServerWebExchange exchange, GatewayFilterChain chain)`：处理请求，验证 JWT 令牌
- `unauthorizedResponse(ServerWebExchange exchange, String message)`：返回未授权响应

### 5.3 认证服务

#### AuthServiceImpl 类

**功能**：认证服务实现

**位置**：[mototrip-auth-server/src/main/java/com/mototrip/auth/service/impl/AuthServiceImpl.java](file:///Users/lixd/IdeaProjects/Git/mototrip/mototrip-java/mototrip-auth-server/src/main/java/com/mototrip/auth/service/impl/AuthServiceImpl.java)

**主要方法**：
- `register(RegisterRequest request)`：用户注册
- `login(LoginRequest request)`：用户登录
- `wechatLogin(WechatLoginRequest request)`：微信登录
- `getCurrentUser(Long userId)`：获取当前用户信息
- `logout(Long userId)`：用户登出

### 5.4 用户上下文

#### UserContext 类

**功能**：管理用户上下文信息

**位置**：[mototrip-common/src/main/java/com/mototrip/common/context/UserContext.java](file:///Users/lixd/IdeaProjects/Git/mototrip/mototrip-java/mototrip-common/src/main/java/com/mototrip/common/context/UserContext.java)

**主要方法**：
- `setUserId(Long userId)`：设置用户 ID
- `getUserId()`：获取用户 ID
- `setUsername(String username)`：设置用户名
- `getUsername()`：获取用户名
- `clear()`：清除用户上下文

## 6. 依赖关系

### 6.1 服务间依赖

| 服务 | 依赖服务 | 依赖方式 |
|------|---------|--------|
| auth-server | - | - |
| user-center | - | - |
| route-service | user-center | Feign |
| trip-service | user-center | Feign |
| social-service | user-center | Feign |
| map-safety-service | - | - |
| team-service | user-center | Feign |
| weather-service | - | - |
| gateway | 所有服务 | 路由 |

### 6.2 技术依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 3.5.12 | 微服务底座 |
| Spring Cloud | 2025.0.0 | 服务规范 |
| Spring Cloud Alibaba | 2025.0.0.0 | 服务治理 |
| Nacos | 2.4.0 | 注册/配置中心 |
| MyBatis-Plus | 3.5.16 | ORM 框架 |
| Druid | 1.2.28 | 连接池 |
| Knife4j | 4.5.0 | API 文档 |
| Redisson | 3.50.0 | Redis 客户端 |
| RocketMQ | 5.2.x | 消息队列 |
| PostgreSQL | 18 | 数据库 |

## 7. 项目运行方式

### 7.1 前置要求

- JDK 17+
- Maven 3.6+
- Docker &amp; Docker Compose

### 7.2 启动步骤

1. **启动基础设施**
   ```bash
   # 启动 Redis、Nacos、RocketMQ
   docker-compose up -d
   ```

2. **初始化数据库**
   ```bash
   # 使用 init-db.sql 初始化数据库
   # 数据库名: mototrip
   ```

3. **编译项目**
   ```bash
   mvn clean compile
   ```

4. **启动服务**
   
   优先使用项目提供的启动脚本：
   ```bash
   # 一键启动所有服务
   bash start-all-services.sh
   ```
   
   或者按以下顺序手动启动服务：
   1. **Nacos** (已通过 docker-compose 启动)
   2. **mototrip-gateway** (端口 8080)
   3. **mototrip-auth-server** (端口 8100)
   4. **mototrip-user-center** (端口 8110)
   5. 其他业务服务

5. **停止服务**
   ```bash
   # 一键停止所有服务
   bash stop-all-services.sh
   ```

### 7.3 API 文档访问

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

### 7.4 服务端口

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

## 8. 核心特性

### 8.1 用户认证
- 用户注册（Bcrypt 加密）
- JWT 登录 + Redis 存储
- 微信登录
- 获取当前用户信息
- 用户登出

### 8.2 用户中心
- 用户资料 CRUD
- 用户模式切换（新手/老手/乘客）

### 8.3 路线服务
- 路线 CRUD + 分页
- 航点管理
- 评论功能
- 点赞（重复检查 409）

### 8.4 行程服务
- 行程 CRUD
- 日记功能
- 行前准备清单（toggle isPacked）
- 行程分享

### 8.5 社交服务
- 帖子发布
- 评论（嵌套回复）
- 足迹记录
- 足迹成就
- 点赞

### 8.6 地图安全服务
- 离线地图（瓦片坐标生成 + RocketMQ 异步下载）
- 危险区域（Haversine 附近搜索）
- 禁停区域
- 警告聚合（夜间+危险+禁停）
- 位置共享（8位 UUID shareCode）

### 8.7 车队服务
- 车队 CRUD
- 成员管理（pending/approved 状态流转）

### 8.8 天气服务
- Open-Meteo API 代理
- 25 个 WMO 天气码映射

## 9. 架构设计

### 9.1 用户上下文传递链

```
Gateway JWT 验证 → 设置 X-User-Id/X-Username 头
  → UserContextInterceptor 提取到 ThreadLocal
    → FeignUserContextInterceptor 传播到下游 Feign 调用
```

### 9.2 统一响应格式

```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

### 9.3 Sentinel 资源分类

- **GET/HEAD/OPTIONS** → `global-api` (100 QPS)
- **/auth/** 路径 → `auth-api` (10 QPS)
- **其他写操作** → `write-api` (20 QPS)

### 9.4 数据库策略

- 单库 `mototrip`，`mt_` 前缀
- `mt_user_likes` 表在 route/trip/social 三个服务中各自定义实体（按 `target_type` 区分）
- `mt_users` 表在 auth 和 user-center 各自定义

### 9.5 Gateway 路由策略

- Gateway 使用 StripPrefix=1 过滤器，移除 /api 前缀后转发到下游服务
- 下游控制器不再包含 /api 前缀，直接使用 /auth、/users、/routes 等路径
- 支持通用路由（用于 Knife4j 文档聚合）和业务路由两种模式

## 10. 开发指南

### 10.1 环境变量

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

### 10.2 代码规范

- 包名：`com.mototrip.{service}.{module}`
- 表名前缀：`mt_`
- 统一响应：`Result&lt;T&gt;`
- 异常处理：继承 `BusinessException`
- 控制器路径：不包含 `/api` 前缀，与 Gateway StripPrefix=1 配置保持一致

### 10.3 项目统计

| 指标 | 数量 |
|------|------|
| 模块 | 10个 |
| Java 文件 | ~235个 |
| API 端点 | 96个（GET:40, POST:30, PUT:12, DELETE:14） |
| 数据库表 | 22张 |
| Feign 客户端 | 6个 |
| MQ 消费者 | 4个 |
| 控制器 | 19个 |

## 11. 许可证

Apache License 2.0

