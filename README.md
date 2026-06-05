# DR1 分布式前后端作业

本仓库完成了一套前后端分离作业骨架：

- 后端：Spring Boot 3 + Spring Cloud Gateway + Spring Cloud Alibaba Nacos Discovery 多模块分布式框架
- ORM：MyBatis-Plus，包含分页插件、示例 CRUD、H2 本地数据库
- 代码生成器：MyBatis-Plus Generator，可本地直接生成 `sys_user` 示例代码，也可切换到 MySQL
- 前端：Vue 3 + Vite + Pinia + Vue Router + Element Plus

## 项目结构

```text
backend/
  dr1-common/     公共响应、分页模型和异常处理
  dr1-gateway/    API 网关，默认路由到本地服务
  dr1-auth/       认证服务示例
  dr1-system/     系统服务，集成 MyBatis-Plus CRUD
  dr1-generator/  MyBatis-Plus 代码生成器
dr1-ui/           Vue 3 + Element Plus 前端
```

## 后端启动

在 `backend` 目录下编译：

```bash
mvn clean package -DskipTests
```

分别启动三个服务：

```bash
mvn -pl dr1-auth spring-boot:run
mvn -pl dr1-system spring-boot:run
mvn -pl dr1-gateway spring-boot:run
```

默认端口：

- 网关：`http://localhost:9100`
- 认证服务：`http://localhost:9201`
- 系统服务：`http://localhost:9202`

示例接口：

- `POST http://localhost:9100/auth/login`
- `GET http://localhost:9100/system/users`
- `GET http://localhost:9100/system/users/page`

## Nacos 注册中心

本地默认关闭 Nacos，避免演示时必须先启动注册中心。需要启用分布式注册发现时，先启动 Nacos，然后用 `nacos` profile 启动：

```bash
mvn -pl dr1-auth spring-boot:run -Dspring-boot.run.profiles=nacos
mvn -pl dr1-system spring-boot:run -Dspring-boot.run.profiles=nacos
mvn -pl dr1-gateway spring-boot:run -Dspring-boot.run.profiles=nacos
```

默认 Nacos 地址：`localhost:8848`。

## 代码生成器

默认读取 H2 内存库中的 `sys_user` 示例表并输出到 `target/generated-code`：

```bash
cd backend
mvn -pl dr1-generator exec:java
```

连接 MySQL 示例：

```bash
mvn -pl dr1-generator exec:java ^
  -Dgen.jdbcUrl=jdbc:mysql://localhost:3306/dr1?useUnicode=true^&characterEncoding=utf8^&serverTimezone=Asia/Shanghai ^
  -Dgen.username=root ^
  -Dgen.password=123456 ^
  -Dgen.tables=sys_user ^
  -Dgen.parentPackage=com.dr1 ^
  -Dgen.module=system
```

## 前端启动

```bash
cd dr1-ui
npm install
npm run dev
```

前端默认地址：`http://localhost:5173`，开发代理会把 `/api` 转发到网关 `http://localhost:9100`。

