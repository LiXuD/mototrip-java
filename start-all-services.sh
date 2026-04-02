#!/bin/bash

# MotoTrip 后端服务启动脚本
# 一次性启动所有后端服务

echo "开始启动 MotoTrip 后端服务..."

# 进入项目目录
cd "$(dirname "$0")"

# 启动 API Gateway
echo "启动 API Gateway..."
nohup mvn spring-boot:run -pl mototrip-gateway > gateway.log 2>&1 &
echo "API Gateway 启动中..."

# 启动 Auth Server
echo "启动 Auth Server..."
nohup mvn spring-boot:run -pl mototrip-auth-server > auth-server.log 2>&1 &
echo "Auth Server 启动中..."

# 启动 User Center
echo "启动 User Center..."
nohup mvn spring-boot:run -pl mototrip-user-center > user-center.log 2>&1 &
echo "User Center 启动中..."

# 启动 Route Service
echo "启动 Route Service..."
nohup mvn spring-boot:run -pl mototrip-route-service > route-service.log 2>&1 &
echo "Route Service 启动中..."

# 启动 Trip Service
echo "启动 Trip Service..."
nohup mvn spring-boot:run -pl mototrip-trip-service > trip-service.log 2>&1 &
echo "Trip Service 启动中..."

# 启动 Social Service
echo "启动 Social Service..."
nohup mvn spring-boot:run -pl mototrip-social-service > social-service.log 2>&1 &
echo "Social Service 启动中..."

# 启动 Map Safety Service
echo "启动 Map Safety Service..."
nohup mvn spring-boot:run -pl mototrip-map-safety-service > map-safety-service.log 2>&1 &
echo "Map Safety Service 启动中..."

# 启动 Team Service
echo "启动 Team Service..."
nohup mvn spring-boot:run -pl mototrip-team-service > team-service.log 2>&1 &
echo "Team Service 启动中..."

echo "所有后端服务启动命令已执行完成！"
echo "服务正在启动中，请等待 30 秒后检查服务状态。"
echo "可以使用以下命令检查服务状态："
echo "lsof -i :8080-8160"
