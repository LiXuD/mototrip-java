# MotoTrip NestJS → Java Spring Cloud 微服务重构方案

## 一、任务理解

### 1.1 目标
将现有 NestJS 后端项目 (`mototrip-api`) 重构为 Java 微服务架构，**1:1 还原所有现有功能**，不增删需求。

### 1.2 约束
- 构建工具：Maven 单仓库多模块
- 数据库：PostgreSQL（保持现有 `mt_` 前缀表结构不变）
- 响应格式：与 NestJS 完全一致
- API 路径：保持 `/api/xxx` 前缀不变

### 1.3 确认的技术栈版本

| 架构层级 | 技术组件 | 版本 | 说明 |
|---------|---------|------|------|
| 基础框架 | Spring Boot | **3.5.12** | 从 4.0.0 降级，确保组件兼容性 |
| 微服务核心 | Spring Cloud | **2025.0.x** (Northfields) | 兼容 Spring Boot 3.5.x |
| 阿里生态 | Spring Cloud Alibaba | **2025.0.0.0** | 兼容 Spring Boot 3.5.x |
| 注册/配置中心 | Nacos | 2.4.0 | 服务发现 + 动态配置 |
| API 网关 | Spring Cloud Gateway | 4.1.x | 统一入口 + JWT 鉴权 |
| 服务调用 | OpenFeign + LoadBalancer | 2025.0.x | 远程调用 |
| 熔断限流 | Sentinel | 2.0.x (BOM管理) | 高可用保护 |
| 数据库 | PostgreSQL | 18 | 业务数据库 |
| 连接池/ORM | Druid + MyBatis-Plus | **1.2.28** + **3.5.16** | Druid 升级到 1.2.28 以支持 Boot 3.5 |
| 权限认证 | Spring Security OAuth2 Auth Server | 1.3.x | 认证授权（归属 auth 微服务） |
| 令牌存储 | Redis + Redisson | 7.2.x + **3.50.0** | 分布式令牌存储 |
| 消息队列 | RocketMQ | 5.2.x | 异步解耦 |
| 分布式事务 | Seata | 2.0.x | 跨服务事务（按需使用） |
| 链路监控 | Micrometer + SkyWalking | 最新 | 全链路追踪 |
| 接口文档 | SpringDoc + Knife4j | **2.8.9** + **4.5.0** | 排除 Knife4j 内置 springdoc，手动管理版本 |

### 1.4 版本兼容性风险提示

| 风险项 | 说明 | 应对措施 |
|--------|------|---------|
| Knife4j 4.5.0 | 内置 springdoc 版本与 Boot 3.5 不完全兼容 | 排除内置 springdoc，手动引入 2.8.9 |
| Redisson 3.50.0 | 官方标注支持到 Boot 3.4.x | 优先测试，不兼容则手动配置 |
| MyBatis-Plus 3.5.16 | 使用 `mybatis-plus-spring-boot3-starter` | 已确认支持 Boot 3.x |
| Druid 1.2.28 | 必须使用 `druid-spring-boot-3-starter` | 注意 starter 名称区别 |

---

## 二、项目结构设计

### 2.1 Maven 单仓库多模块结构

```
mototrip/
├── pom.xml                              # 父 POM（版本管理 + dependencyManagement）
├── mototrip-common/                     # 公共模块（统一响应、异常处理、Redis/MQ工具、枚举、Feign客户端）
├── mototrip-gateway/                    # API 网关（Spring Cloud Gateway + JWT鉴权 + Sentinel限流）
├── mototrip-auth-server/                # 认证服务（注册、登录、微信登录、OAuth2 Auth Server）
├── mototrip-user-center/                # 用户中心（用户资料 + 用户模式）
├── mototrip-route-service/              # 路线服务（路线 + 途经点 + 评论）
├── mototrip-trip-service/               # 行程服务（行程 + 日记 + 准备清单 + 分享）
├── mototrip-social-service/             # 社交服务（帖子/评论 + 足迹/成就）
├── mototrip-map-safety-service/         # 地图安全服务（离线地图 + 危险区域 + 禁停区域 + 警告聚合 + 位置共享）
├── mototrip-team-service/               # 车队服务（车队 + 成员管理）
├── mototrip-weather-service/            # 天气服务（Open-Meteo API 代理，无数据库）
├── docker-compose.yml                   # 基础设施编排
├── init-db.sql                          # 数据库初始化脚本
├── sql/                                 # 各服务 DDL 脚本
└── .gitignore
```

### 2.2 各服务端口分配

| 服务 | 端口 | 包名 | 启动类 |
|------|------|------|--------|
| mototrip-gateway | 8080 | `com.mototrip.gateway` | `GatewayApplication` |
| mototrip-auth-server | 8100 | `com.mototrip.auth` | `AuthServerApplication` |
| mototrip-user-center | 8110 | `com.mototrip.usercenter` | `UserCenterApplication` |
| mototrip-route-service | 8120 | `com.mototrip.route` | `RouteServiceApplication` |
| mototrip-trip-service | 8130 | `com.mototrip.trip` | `TripServiceApplication` |
| mototrip-social-service | 8140 | `com.mototrip.social` | `SocialServiceApplication` |
| mototrip-map-safety-service | 8150 | `com.mototrip.mapsafety` | `MapSafetyServiceApplication` |
| mototrip-team-service | 8160 | `com.mototrip.team` | `TeamServiceApplication` |
| mototrip-weather-service | 8170 | `com.mototrip.weather` | `WeatherServiceApplication` |

### 2.3 微服务拆分映射

| 目标微服务 | 包含的 NestJS 模块 | 数据库表 |
|-----------|-------------------|---------|
| mototrip-auth-server | auth | mt_users |
| mototrip-user-center | user, user-mode | mt_users(只读), mt_user_modes |
| mototrip-route-service | route, waypoint, review | mt_routes, mt_waypoints, mt_reviews, mt_user_likes |
| mototrip-trip-service | trip, diary, preparation, share | mt_trips, mt_diaries, mt_preparations, mt_trip_shares, mt_user_likes |
| mototrip-social-service | social, footprint | mt_posts, mt_comments, mt_footprints, mt_footprint_achievements, mt_user_likes |
| mototrip-map-safety-service | map, danger-zone, no-parking-zone, warning, location | mt_offline_maps, mt_offline_map_tiles, mt_danger_zones, mt_no_parking_zones, mt_location_shares, mt_location_updates |
| mototrip-team-service | team | mt_teams, mt_team_members |
| mototrip-weather-service | weather | 无（纯 API 代理） |

---

## 三、父 POM 设计

### 3.1 版本属性

```xml
<properties>
    <java.version>17</java.version>
    <spring-boot.version>3.5.12</spring-boot.version>
    <spring-cloud.version>2025.0.0</spring-cloud.version>
    <spring-cloud-alibaba.version>2025.0.0.0</spring-cloud-alibaba.version>
    <postgresql.version>42.7.7</postgresql.version>
    <druid.version>1.2.28</druid.version>
    <mybatis-plus.version>3.5.16</mybatis-plus.version>
    <redisson.version>3.50.0</redisson.version>
    <rocketmq-spring.version>2.3.5</rocketmq-spring.version>
    <seata.version>2.0.0</seata.version>
    <knife4j.version>4.5.0</knife4j.version>
    <springdoc.version>2.8.9</springdoc.version>
    <hutool.version>5.8.37</hutool.version>
    <mapstruct.version>1.6.3</mapstruct.version>
    <jjwt.version>0.12.6</jjwt.version>
</properties>
```

### 3.2 BOM 依赖管理

```xml
<dependencyManagement>
    <dependencies>
        <!-- Spring Boot BOM -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <!-- Spring Cloud BOM -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <!-- Spring Cloud Alibaba BOM -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>${spring-cloud-alibaba.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <!-- 项目内部模块 -->
        <dependency>
            <groupId>com.mototrip</groupId>
            <artifactId>mototrip-common</artifactId>
            <version>${project.version}</version>
        </dependency>
        <!-- MyBatis-Plus (Spring Boot 3 专用) -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>
        <!-- Druid (Spring Boot 3 专用) -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-3-starter</artifactId>
            <version>${druid.version}</version>
        </dependency>
        <!-- Knife4j (排除内置 springdoc) -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
            <version>${knife4j.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springdoc</groupId>
                    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- SpringDoc (手动管理版本) -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 3.3 各模块依赖分类

| 模块 | 公共依赖 | 特有依赖 |
|------|---------|---------|
| **common** | spring-boot-starter-web(provided), openfeign, loadbalancer, nacos-discovery, sentinel, redisson, jjwt, jackson, lombok, validation, hutool | 无 |
| **gateway** | spring-cloud-starter-gateway(webflux), nacos-discovery, nacos-config, sentinel | jjwt |
| **auth-server** | mototrip-common, spring-boot-starter-web, spring-security-oauth2-authorization-server, spring-boot-starter-data-redis, druid, mybatis-plus, postgresql, nacos-discovery, nacos-config | 无 |
| **user-center** | mototrip-common, spring-boot-starter-web, druid, mybatis-plus, postgresql, nacos-discovery, nacos-config, knife4j | 无 |
| **route-service** | 同 user-center | 无 |
| **trip-service** | 同 user-center | 无 |
| **social-service** | 同 user-center | 无 |
| **map-safety-service** | 同 user-center + rocketmq | spring-boot-starter-webflux(高德地图) |
| **team-service** | 同 user-center | 无 |
| **weather-service** | mototrip-common, spring-boot-starter-webflux, nacos-discovery, nacos-config, knife4j | 无数据库依赖 |

---

## 四、Common 模块详细设计

### 4.1 包结构

```
com.mototrip.common/
├── response/
│   ├── Result.java                     # 统一响应 {code: 0, message: "success", data: T}
│   ├── PageResult.java                 # 分页响应 {list, total, page, pageSize, hasMore}
│   └── PageRequest.java                # 分页请求参数
├── exception/
│   ├── BusinessException.java          # 业务异常基类 (code + message + status)
│   ├── NotFoundException.java          # 404
│   ├── ForbiddenException.java         # 403
│   ├── ConflictException.java          # 409
│   ├── UnauthorizedException.java      # 401
│   ├── BadRequestException.java        # 400
│   └── GlobalExceptionHandler.java     # 全局异常处理器 (精确匹配 NestJS 格式)
├── enums/
│   ├── BaseEnum.java                   # 枚举基础接口
│   ├── RidingExperience.java           # newbie/experienced/expert
│   ├── MotorcycleType.java             # scooter/street/sport/adv
│   ├── UserMode.java                   # newbie/experienced/passenger
│   ├── RouteDifficulty.java            # easy/medium/hard
│   ├── WaypointType.java               # scenic/restaurant/hotel/gas/repair/other
│   ├── TripStatus.java                 # planning/ongoing/completed
│   ├── DiaryMood.java                  # happy/excited/calm/tired/neutral
│   ├── PreparationCategory.java        # tool/safety/clothing/electronics/documents/other
│   ├── DangerType.java                 # 10种危险类型
│   ├── DangerSeverity.java             # light/medium/severe
│   ├── DangerStatus.java               # active/resolved/ignored
│   ├── TeamStatus.java                 # open/full/completed/cancelled
│   ├── TeamMemberRole.java             # member/leader
│   └── TeamMemberStatus.java           # pending/approved/rejected
├── context/
│   └── UserContext.java                # ThreadLocal 持有当前用户 userId/username
├── interceptor/
│   ├── UserContextInterceptor.java     # 从 X-User-Id 请求头提取用户信息
│   └── FeignUserContextInterceptor.java # Feign 请求拦截器 (传递 X-User-Id/X-Username)
├── feign/
│   ├── UserClient.java                 # 用户服务 Feign 客户端
│   ├── RouteClient.java                # 路线服务 Feign 客户端
│   ├── TripClient.java                 # 行程服务 Feign 客户端
│   ├── SocialClient.java               # 社交服务 Feign 客户端
│   ├── MapSafetyClient.java            # 地图安全服务 Feign 客户端
│   ├── TeamClient.java                 # 车队服务 Feign 客户端
│   └── fallback/                       # 各 Feign Client 的 FallbackFactory 实现
├── util/
│   ├── JwtUtil.java                    # JWT 生成/解析工具
│   ├── JsonUtil.java                   # JSON 序列化工具
│   └── GeoUtils.java                   # 地理位置计算工具 (Haversine 公式)
├── constant/
│   ├── CommonConstant.java             # 公共常量
│   └── MqConstant.java                 # MQ Topic 常量
├── dto/
│   └── UserIdDTO.java                  # 跨服务传递的 userId
├── config/
│   ├── FeignConfig.java                # Feign 全局配置 (超时、日志)
│   ├── JacksonConfig.java              # Jackson 全局配置 (日期格式、非空)
│   └── RedissonConfig.java             # Redisson 配置
└── handler/
    └── MyBatisPlusMetaHandler.java     # 自动填充 created_at / updated_at
```

### 4.2 核心响应格式（精确匹配 NestJS）

**成功响应**：`{code: 0, message: "success", data: T}`
**异常响应**：`{code: httpStatus, message: "...", timestamp: "ISO8601", path: "/api/xxx"}`
**分页响应**：`{list: [...], total: 100, page: 1, pageSize: 10, hasMore: true}`

---

## 五、数据库实体映射方案

### 5.1 映射规则

| NestJS TypeORM | Java MyBatis-Plus |
|----------------|-------------------|
| `@Entity()` + `@Table()` | `@TableName("mt_xxx")` |
| `@PrimaryGeneratedColumn()` | `@TableId(type = IdType.AUTO)` |
| `@Column({ select: false })` | `@TableField(select = false)` |
| `@Column({ type: 'jsonb' })` | `@TableField(typeHandler = JacksonTypeHandler.class)` + `@TableName(autoResultMap = true)` |
| `@Column({ type: 'simple-array' })` | `String` 类型（逗号分隔），Service 层转换 |
| `@Column({ type: 'enum' })` | `String` 类型（存储枚举值字符串） |
| `@CreateDateColumn()` | `@TableField(fill = FieldFill.INSERT)` |
| `@UpdateDateColumn()` | `@TableField(fill = FieldFill.INSERT_UPDATE)` |

### 5.2 全部 22 张表映射

| 表名 | 实体类 | 所属服务 | 关键字段 |
|------|--------|---------|---------|
| mt_users | User | auth-server + user-center | id, username, password(select:false), nickname, avatar, phone(select:false), email(select:false), bio, motorcycle, ridingExperience, motorcycleType, openid(select:false), followersCount(0), followingCount(0), created_at, updated_at |
| mt_user_modes | UserMode | user-center | id, userId(unique), mode(newbie), maxRideDistance(200), maxSpeed(80), enableDistanceReminder(true), enableSpeedReminder(true), enableDangerWarning(true), enableSimplifiedUI(false), enableComfortMode(false), enableProfessionalFeatures(false) |
| mt_routes | Route | route-service | id, name, description, coverImage, distance(10,2), duration(6,2), difficulty, startPoint(jsonb), endPoint(jsonb), isPublic(true), likes(0), views(0), isOfficial(false), avgRating(3,2), reviewCount(0), seasons, motorcycleTypes, creatorId |
| mt_waypoints | Waypoint | route-service | id, name, description, type, location(jsonb), images, rating, phone, openingHours, sequence(0), metadata(jsonb), routeId, tripId |
| mt_reviews | Review | route-service | id, userId, routeId, rating, content |
| mt_user_likes | UserLike | route/trip/social-service | id, userId, targetType, targetId + **唯一索引(userId, targetType, targetId)** |
| mt_trips | Trip | trip-service | id, name, description, coverImage, startDate, endDate, status, totalDistance, notes, userId, routeId |
| mt_diaries | Diary | trip-service | id, title, content, images, location(jsonb), locationName, weather, temperature, mood, likes(0), comments(0), tag, userId, tripId |
| mt_preparations | Preparation | trip-service | id, name, category, description, isEssential, isPacked, quantity, userId |
| mt_trip_shares | TripShare | trip-service | id, userId, tripId, title, summary, totalDistance, duration, waypointCount, routePoints(jsonb), isShared |
| mt_posts | Post | social-service | id, content(text), images, location, likes(0), comments(0), shares(0), userId |
| mt_comments | Comment | social-service | id, content(text), postId, userId, parentId(nullable), likes(0) |
| mt_footprints | Footprint | social-service | id, userId, latitude, longitude, locationName, province, city, district, image, note, visitCount(0), distance, visitedAt |
| mt_footprint_achievements | FootprintAchievement | social-service | id, userId, name, description, icon, targetCount, currentCount, unlocked(false), unlockedAt |
| mt_offline_maps | OfflineMap | map-safety-service | id, name, description, minLat/maxLat/minLng/maxLng, minZoom(10)/maxZoom(18), mapProvider(amap), tileCount, downloadedCount, status, downloadProgress, filePath, fileSize, creatorId |
| mt_offline_map_tiles | OfflineMapTile | map-safety-service | id, mapId, z, x, y, status, filePath, fileSize |
| mt_danger_zones | DangerZone | map-safety-service | id, name, description, location(jsonb), radius(500), type(10种枚举), severity, status, images, reporterId |
| mt_no_parking_zones | NoParkingZone | map-safety-service | id, name, description, location(jsonb), radius(500), images, reporterId |
| mt_location_shares | LocationShare | map-safety-service | id, userId, shareCode, isActive, expiresAt |
| mt_location_updates | LocationUpdate | map-safety-service | id, shareId, latitude, longitude, speed, heading, recordedAt |
| mt_teams | Team | team-service | id, name, description, destination, startTime, endTime, maxMembers(10), status, coverImage, latitude, longitude, creatorId |
| mt_team_members | TeamMember | team-service | id, teamId, userId, role, status |

### 5.3 数据库策略

- **同一数据库 `mototrip`**，通过表前缀 `mt_` 区分服务归属
- `mt_user_likes` 跨服务共享表，各需要的服务都配置访问权限
- MyBatis-Plus 全局配置 `map-underscore-to-camel-case: true`

---

## 六、网关路由和鉴权设计

### 6.1 路由规则

| 路由 ID | 目标服务 | 路径匹配 |
|---------|---------|---------|
| auth-service | mototrip-auth-server | `/api/auth/**` |
| user-center | mototrip-user-center | `/api/users/**`, `/api/user-mode/**` |
| route-service | mototrip-route-service | `/api/routes/**`, `/api/waypoints/**`, `/api/reviews/**` |
| trip-service | mototrip-trip-service | `/api/trips/**`, `/api/diaries/**`, `/api/preparations/**`, `/api/shares/**` |
| social-service | mototrip-social-service | `/api/posts/**`, `/api/footprints/**` |
| map-safety-service | mototrip-map-safety-service | `/api/maps/**`, `/api/danger-zones/**`, `/api/no-parking-zones/**`, `/api/warnings/**`, `/api/locations/**` |
| team-service | mototrip-team-service | `/api/teams/**` |
| weather-service | mototrip-weather-service | `/api/weather/**` |

### 6.2 JWT 鉴权过滤器

- **白名单**（免鉴权）：`/api/auth/register`, `/api/auth/login`, `/api/auth/wechat/login`, GET 方法的公开资源端点
- **鉴权流程**：提取 `Authorization: Bearer xxx` → 验证 JWT → 提取 `{sub: userId, username}` → 设置请求头 `X-User-Id` / `X-Username` 传递到下游服务
- **异常响应**：401 格式 `{code: 401, message: "...", timestamp: "...", path: "..."}`

### 6.3 Sentinel 限流

- 全局限流：100次/分钟（匹配 NestJS ThrottlerModule: ttl=60000, limit=100）
- 429 响应格式：`{code: 429, message: "请求过于频繁", timestamp: "...", path: "..."}`

### 6.4 CORS 配置

- `allowedOrigins: *`（匹配 NestJS 配置）
- `allow-credentials: true`
- `allowed-methods: *`

---

## 七、服务间调用 (OpenFeign) 设计

### 7.1 Feign Client 接口（定义在 common 模块）

| Feign Client | 目标服务 | 方法 |
|-------------|---------|------|
| UserClient | mototrip-user-center | `getUserById(id)`, `getUsersByIds(ids)` |
| RouteClient | mototrip-route-service | `getRouteById(id)` (内部接口) |
| TripClient | mototrip-trip-service | `getTripById(id)` (内部接口) |
| SocialClient | mototrip-social-service | `getPostById(id)` (内部接口) |
| TeamClient | mototrip-team-service | `getTeamById(id)` (内部接口) |

### 7.2 跨服务调用场景

| 调用方 | 被调用方 | 场景 |
|--------|---------|------|
| route-service | user-center | 路线列表返回创建者信息 |
| trip-service | user-center | 行程列表返回用户信息 |
| trip-service | route-service | 行程详情返回关联路线 |
| social-service | user-center | 帖子列表返回用户信息 |
| map-safety-service | user-center | 位置共享返回用户信息 |
| team-service | user-center | 车队成员返回用户信息 |

### 7.3 用户上下文传递

- 网关验证 JWT 后设置 `X-User-Id` / `X-Username` 请求头
- 各服务通过 `UserContextInterceptor` 从请求头提取并存入 `ThreadLocal`
- Feign 调用通过 `FeignUserContextInterceptor` 自动传递请求头

---

## 八、异步消息 (RocketMQ) 使用场景

### 8.1 Topic 规划

| Topic | 生产者 | 消费者 | 用途 |
|-------|--------|--------|------|
| `MOTOTRIP_MAP_DOWNLOAD` | map-safety-service | map-safety-service | 异步下载地图瓦片（替代 NestJS 内存异步） |
| `MOTOTRIP_FOOTPRINT_ACHIEVEMENT` | social-service | social-service | 足迹添加后异步更新成就 |
| `MOTOTRIP_TEAM_NOTIFICATION` | team-service | (预留) | 车队审批通知 |
| `MOTOTRIP_LIKE_EVENT` | route/trip/social-service | (预留) | 点赞事件异步处理 |

### 8.2 消息体定义

```java
// MapDownloadEvent: { mapId, timestamp }
// FootprintAchievementEvent: { userId, footprintId, province, city, timestamp }
// LikeEvent: { userId, targetType, targetId, timestamp }
// TeamNotificationEvent: { teamId, userId, type, timestamp }
```

---

## 九、分布式事务 (Seata) 使用评估

### 9.1 结论

**当前阶段建议不引入 Seata**，原因：
- MotoTrip 项目的绝大多数操作可在单服务内用本地 `@Transactional` 完成
- 真正需要跨服务事务的场景极少
- 使用 **本地事务 + RocketMQ 最终一致性** 即可满足需求

### 9.2 如需引入的场景

- 车队成员审批后自动更新车队状态（但实际在同一服务内，本地事务即可）
- 创建行程时跨服务扣减配额（当前无此需求）

---

## 十、配置中心 (Nacos) 配置规划

### 10.1 Namespace

`mototrip`

### 10.2 配置文件列表

| Data ID | 说明 |
|---------|------|
| `application-common.yaml` | 公共配置（Jackson、MyBatis-Plus、日志） |
| `mototrip-gateway.yaml` | 网关路由、CORS、限流 |
| `mototrip-auth-server.yaml` | JWT密钥、微信配置、数据库 |
| `mototrip-user-center.yaml` | 数据库配置 |
| `mototrip-route-service.yaml` | 数据库配置 |
| `mototrip-trip-service.yaml` | 数据库配置 |
| `mototrip-social-service.yaml` | 数据库配置 |
| `mototrip-map-safety-service.yaml` | 数据库配置 + RocketMQ |
| `mototrip-team-service.yaml` | 数据库配置 |
| `mototrip-weather-service.yaml` | Open-Meteo API 配置 |

---

## 十一、统一包结构规范

每个微服务采用统一的包结构：

```
com.mototrip.{service}/
├── {Service}Application.java          # 启动类
├── config/                            # 配置类（MyBatis-Plus、Druid、Redis、Knife4j）
├── controller/                        # REST 控制器
├── service/                           # 接口
│   └── impl/                          # 实现类
├── mapper/                            # MyBatis-Plus Mapper
├── entity/                            # 数据库实体
├── dto/
│   ├── request/                       # 请求 DTO（含 validation 注解）
│   └── response/                      # 响应 DTO
├── converter/                         # MapStruct 转换器
├── enums/                             # 服务特有枚举（如有）
├── interceptor/                       # 拦截器
├── context/                           # 上下文
└── constants/                         # 常量
```

---

## 十二、关键业务规则（必须 1:1 还原）

| 规则 | 说明 |
|------|------|
| 全局响应格式 | `{code: 0, message: "success", data: T}` |
| 全局异常格式 | `{code, message, timestamp, path}` |
| 全局 API 前缀 | `api` |
| 限流 | 100次/分钟 |
| 分页格式 | `{list, total, page, pageSize, hasMore}` |
| 分页上限 | Trip 模块 pageSize 最大 100 |
| 点赞去重 | mt_user_likes 唯一索引，重复点赞返回 409 |
| 所有权验证 | route/trip/diary/post/footprint 的 update/delete 需验证 owner，非 owner 返回 403 |
| 敏感字段 | password, phone, email, openid 不暴露到响应 |
| JWT payload | `{sub: userId, username: username}` |
| 密码加密 | bcrypt cost factor 10 |
| 微信登录 | 调用 `https://api.weixin.qq.com/sns/jscode2session` |
| 天气 API | Open-Meteo `https://api.open-meteo.com/v1/forecast`，25种 WMO 代码映射 |
| 地图瓦片 | 高德地图 URL 构建，异步下载（RocketMQ 替代内存异步） |
| 警告聚合 | 聚合夜间警告 + 危险区域 + 禁停区域 |
| 位置历史 | 默认 24 小时，最多 100 条 |
| 车队规则 | leader 不能离开车队；满员时自动更新 team status 为 full |

---

## 十三、分步实施计划

### Wave 1: 基础设施层

| 步骤 | 内容 | 验证标准 |
|------|------|---------|
| 1.1 | 创建父 POM + 所有模块骨架 | `mvn validate` 通过 |
| 1.2 | 创建 mototrip-common 公共模块（Result、PageResult、异常类、枚举、UserContext、Feign拦截器、MetaHandler） | 单元测试通过 |
| 1.3 | 创建 docker-compose.yml（PostgreSQL 18, Redis, Nacos 2.4.0, RocketMQ, Sentinel Dashboard） | `docker-compose up -d` 全部 healthy |
| 1.4 | 创建 init-db.sql（建库、22张表、索引、唯一约束） | SQL 执行无报错 |

### Wave 2: 认证与网关

| 步骤 | 内容 | 验证标准 |
|------|------|---------|
| 2.1 | mototrip-auth-server（OAuth2 配置、JWT 签发、注册、登录、微信登录、获取当前用户、退出登录） | 5 个端点 curl 测试通过 |
| 2.2 | mototrip-gateway（路由规则、JWT 鉴权过滤器、白名单、CORS、Sentinel 限流） | 公开端点免鉴权、受保护端点需 token |

### Wave 3: 业务服务（7 个服务可并行）

| 步骤 | 内容 | 端点数 | 验证标准 |
|------|------|--------|---------|
| 3.1 | mototrip-user-center | 5 | 用户资料 CRUD + 用户模式 CRUD + 模式切换 |
| 3.2 | mototrip-route-service | 16 | 路线 CRUD + 分页筛选 + 点赞 + Waypoint CRUD + Review CRUD |
| 3.3 | mototrip-trip-service | 18 | 行程 CRUD + 日记 CRUD + 准备清单 + 分享管理 |
| 3.4 | mototrip-social-service | 12 | 帖子 CRUD + 评论 + 足迹 CRUD + 成就 + 统计 |
| 3.5 | mototrip-map-safety-service | 30+ | 离线地图 + 危险区域 + 禁停区域 + 位置共享 + 警告聚合 |
| 3.6 | mototrip-team-service | 10 | 车队 CRUD + 成员管理 + 审批流程 |
| 3.7 | mototrip-weather-service | 3 | 当前天气 + 沿途天气 + 天气预报 |

### Wave 4: 集成与增强

| 步骤 | 内容 | 验证标准 |
|------|------|---------|
| 4.1 | Feign 跨服务客户端 + Fallback 降级 | 跨服务调用正常 + 降级返回 503 |
| 4.2 | RocketMQ 消息集成（地图瓦片异步下载 + 足迹成就更新） | 异步下载启动 + 成就更新 |
| 4.3 | Sentinel 熔断限流（接口级别限流 + Feign 熔断） | 限流 429 + 熔断降级 |
| 4.4 | Knife4j API 文档（所有 Controller 添加注解 + 网关聚合） | doc.html 可访问 + 80+ 端点可见 |

### Wave 5: 验证与收尾

| 步骤 | 内容 | 验证标准 |
|------|------|---------|
| 5.1 | 端到端集成验证（逐一验证 80+ 端点的响应格式） | 与 NestJS 响应格式完全一致 |
| 5.2 | SkyWalking 链路监控（可选） | 跨服务链路追踪正常 |
| 5.3 | 项目收尾（.gitignore、README） | `mvn clean package -DskipTests` 通过 |

---

## 十四、假设与决策

1. **数据库**：使用同一个 PostgreSQL 18 实例和同一个 `mototrip` 数据库，通过表前缀区分服务归属
2. **枚举存储**：使用 `String` 类型存储枚举值（与 NestJS 一致），不使用 MyBatis-Plus EnumTypeHandler
3. **simple-array**：使用逗号分隔的 `String` 存储，Service 层做 `String` ↔ `List<String>` 转换
4. **jsonb**：使用 MyBatis-Plus `JacksonTypeHandler`，实体字段类型为 `Map<String, Object>`
5. **Seata**：当前阶段不引入，使用本地事务 + RocketMQ 最终一致性
6. **认证方式**：网关层 JWT 验证 + 请求头传递用户信息，下游服务通过拦截器获取
7. **API 文档**：Knife4j 4.5.0 + 手动管理 SpringDoc 2.8.9 版本
