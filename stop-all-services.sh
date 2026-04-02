#!/bin/bash
echo "🔴 正在停止所有 MotoTrip 微服务..."

# 杀死所有服务进程
pkill -f "AuthServerApplication"
pkill -f "GatewayApplication"
pkill -f "RouteServiceApplication"
pkill -f "TripServiceApplication"
pkill -f "MapSafetyServiceApplication"
pkill -f "SocialServiceApplication"
pkill -f "TeamServiceApplication"
pkill -f "UserCenterApplication"
pkill -f "WeatherServiceApplication"

# 释放端口（可选，更干净）
echo "🔴 释放所有微服务端口..."
lsof -i:8080 -i:8100 -i:8110 -i:8120 -i:8130 -i:8140 -i:8150 -i:8160 -i:8170 | grep LISTEN | awk '{print $2}' | xargs kill -9 2>/dev/null

echo "✅ 所有微服务已停止！"