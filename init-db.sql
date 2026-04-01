-- MotoTrip Database Initialization Script
-- Database: mototrip
-- Tables: 22

-- =============================================
-- 清理现有表和索引
-- =============================================

-- 按依赖顺序删除表（先删除子表，再删除父表）
DROP TABLE IF EXISTS mt_location_updates CASCADE;
DROP TABLE IF EXISTS mt_location_shares CASCADE;
DROP TABLE IF EXISTS mt_no_parking_zones CASCADE;
DROP TABLE IF EXISTS mt_danger_zones CASCADE;
DROP TABLE IF EXISTS mt_offline_map_tiles CASCADE;
DROP TABLE IF EXISTS mt_offline_maps CASCADE;
DROP TABLE IF EXISTS mt_footprint_achievements CASCADE;
DROP TABLE IF EXISTS mt_footprints CASCADE;
DROP TABLE IF EXISTS mt_comments CASCADE;
DROP TABLE IF EXISTS mt_posts CASCADE;
DROP TABLE IF EXISTS mt_trip_shares CASCADE;
DROP TABLE IF EXISTS mt_preparations CASCADE;
DROP TABLE IF EXISTS mt_diaries CASCADE;
DROP TABLE IF EXISTS mt_trips CASCADE;
DROP TABLE IF EXISTS mt_user_likes CASCADE;
DROP TABLE IF EXISTS mt_reviews CASCADE;
DROP TABLE IF EXISTS mt_waypoints CASCADE;
DROP TABLE IF EXISTS mt_routes CASCADE;
DROP TABLE IF EXISTS mt_user_modes CASCADE;
DROP TABLE IF EXISTS mt_team_members CASCADE;
DROP TABLE IF EXISTS mt_teams CASCADE;
DROP TABLE IF EXISTS mt_users CASCADE;

-- 删除函数（如果存在）
DROP FUNCTION IF EXISTS update_updated_at_column CASCADE;

-- =============================================
-- 用户相关表
-- =============================================

-- 用户表
CREATE TABLE IF NOT EXISTS mt_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(500),
    phone VARCHAR(20),
    email VARCHAR(100),
    bio TEXT,
    motorcycle VARCHAR(100),
    riding_experience VARCHAR(20) DEFAULT 'newbie',
    motorcycle_type VARCHAR(20) DEFAULT 'scooter',
    openid VARCHAR(100),
    followers_count INT DEFAULT 0,
    following_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户模式表
CREATE TABLE IF NOT EXISTS mt_user_modes (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL REFERENCES mt_users(id),
    mode VARCHAR(20) DEFAULT 'newbie',
    max_ride_distance INT DEFAULT 200,
    max_speed INT DEFAULT 80,
    enable_distance_reminder BOOLEAN DEFAULT TRUE,
    enable_speed_reminder BOOLEAN DEFAULT TRUE,
    enable_danger_warning BOOLEAN DEFAULT TRUE,
    enable_simplified_ui BOOLEAN DEFAULT FALSE,
    enable_comfort_mode BOOLEAN DEFAULT FALSE,
    enable_professional_features BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 路线相关表
-- =============================================

-- 路线表
CREATE TABLE IF NOT EXISTS mt_routes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    cover_image VARCHAR(500),
    distance DECIMAL(10,2),
    duration DECIMAL(6,2),
    difficulty VARCHAR(20) DEFAULT 'easy',
    start_point JSONB,
    end_point JSONB,
    is_public BOOLEAN DEFAULT TRUE,
    likes INT DEFAULT 0,
    views INT DEFAULT 0,
    is_official BOOLEAN DEFAULT FALSE,
    avg_rating DECIMAL(3,2) DEFAULT 0,
    review_count INT DEFAULT 0,
    seasons VARCHAR(200),
    motorcycle_types VARCHAR(200),
    creator_id INT REFERENCES mt_users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 途经点表
CREATE TABLE IF NOT EXISTS mt_waypoints (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    type VARCHAR(20) DEFAULT 'other',
    location JSONB,
    images VARCHAR(1000),
    rating DECIMAL(3,2),
    phone VARCHAR(20),
    opening_hours VARCHAR(100),
    sequence INT DEFAULT 0,
    metadata JSONB,
    route_id INT REFERENCES mt_routes(id) ON DELETE CASCADE,
    trip_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 评论表
CREATE TABLE IF NOT EXISTS mt_reviews (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES mt_users(id),
    route_id INT NOT NULL REFERENCES mt_routes(id) ON DELETE CASCADE,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户点赞表（跨服务共享）
CREATE TABLE IF NOT EXISTS mt_user_likes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_user_likes UNIQUE (user_id, target_type, target_id)
);

-- =============================================
-- 行程相关表
-- =============================================

-- 行程表
CREATE TABLE IF NOT EXISTS mt_trips (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    cover_image VARCHAR(500),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'planning',
    total_distance DECIMAL(10,2),
    notes TEXT,
    user_id INT NOT NULL REFERENCES mt_users(id),
    route_id INT REFERENCES mt_routes(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 日记表
CREATE TABLE IF NOT EXISTS mt_diaries (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    images VARCHAR(2000),
    location JSONB,
    location_name VARCHAR(200),
    weather VARCHAR(50),
    temperature VARCHAR(20),
    mood VARCHAR(20) DEFAULT 'neutral',
    likes INT DEFAULT 0,
    comments INT DEFAULT 0,
    tag VARCHAR(100),
    user_id INT NOT NULL REFERENCES mt_users(id),
    trip_id INT REFERENCES mt_trips(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 准备清单表
CREATE TABLE IF NOT EXISTS mt_preparations (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(20) DEFAULT 'other',
    description TEXT,
    is_essential BOOLEAN DEFAULT FALSE,
    is_packed BOOLEAN DEFAULT FALSE,
    quantity INT DEFAULT 1,
    user_id INT NOT NULL REFERENCES mt_users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 行程分享表
CREATE TABLE IF NOT EXISTS mt_trip_shares (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES mt_users(id),
    trip_id INT NOT NULL REFERENCES mt_trips(id) ON DELETE CASCADE,
    title VARCHAR(200),
    summary TEXT,
    total_distance DECIMAL(10,2),
    duration DECIMAL(6,2),
    waypoint_count INT DEFAULT 0,
    route_points JSONB,
    is_shared BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 社交相关表
-- =============================================

-- 帖子表
CREATE TABLE IF NOT EXISTS mt_posts (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    images VARCHAR(2000),
    location VARCHAR(200),
    likes INT DEFAULT 0,
    comments INT DEFAULT 0,
    shares INT DEFAULT 0,
    user_id INT NOT NULL REFERENCES mt_users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 评论表（帖子评论）
CREATE TABLE IF NOT EXISTS mt_comments (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    post_id INT NOT NULL REFERENCES mt_posts(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES mt_users(id),
    parent_id INT REFERENCES mt_comments(id) ON DELETE CASCADE,
    likes INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 足迹表
CREATE TABLE IF NOT EXISTS mt_footprints (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES mt_users(id),
    latitude DECIMAL(10,7) NOT NULL,
    longitude DECIMAL(10,7) NOT NULL,
    location_name VARCHAR(200),
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    image VARCHAR(500),
    note TEXT,
    visit_count INT DEFAULT 0,
    distance DECIMAL(10,2),
    visited_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 足迹成就表
CREATE TABLE IF NOT EXISTS mt_footprint_achievements (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50),
    user_id INT NOT NULL REFERENCES mt_users(id),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    icon VARCHAR(100),
    target_count INT NOT NULL,
    current_count INT DEFAULT 0,
    unlocked BOOLEAN DEFAULT FALSE,
    unlocked_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 地图安全相关表
-- =============================================

-- 离线地图表
CREATE TABLE IF NOT EXISTS mt_offline_maps (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    min_lat DECIMAL(10,7),
    max_lat DECIMAL(10,7),
    min_lng DECIMAL(10,7),
    max_lng DECIMAL(10,7),
    min_zoom INT DEFAULT 10,
    max_zoom INT DEFAULT 18,
    map_provider VARCHAR(20) DEFAULT 'amap',
    tile_count INT DEFAULT 0,
    downloaded_count INT DEFAULT 0,
    status VARCHAR(20) DEFAULT 'pending',
    download_progress DECIMAL(5,2) DEFAULT 0,
    file_path VARCHAR(500),
    file_size BIGINT DEFAULT 0,
    creator_id INT NOT NULL REFERENCES mt_users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 离线地图瓦片表
CREATE TABLE IF NOT EXISTS mt_offline_map_tiles (
    id SERIAL PRIMARY KEY,
    map_id INT NOT NULL REFERENCES mt_offline_maps(id) ON DELETE CASCADE,
    z INT NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    file_path VARCHAR(500),
    file_size BIGINT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 危险区域表
CREATE TABLE IF NOT EXISTS mt_danger_zones (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    location JSONB,
    radius INT DEFAULT 500,
    type VARCHAR(30) DEFAULT 'other',
    severity VARCHAR(20) DEFAULT 'light',
    status VARCHAR(20) DEFAULT 'active',
    images VARCHAR(2000),
    reporter_id INT NOT NULL REFERENCES mt_users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 禁停区域表
CREATE TABLE IF NOT EXISTS mt_no_parking_zones (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    location JSONB,
    radius INT DEFAULT 500,
    images VARCHAR(2000),
    reporter_id INT NOT NULL REFERENCES mt_users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 位置共享表
CREATE TABLE IF NOT EXISTS mt_location_shares (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES mt_users(id),
    share_code VARCHAR(20) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 位置更新表
CREATE TABLE IF NOT EXISTS mt_location_updates (
    id SERIAL PRIMARY KEY,
    share_id INT NOT NULL REFERENCES mt_location_shares(id) ON DELETE CASCADE,
    latitude DECIMAL(10,7) NOT NULL,
    longitude DECIMAL(10,7) NOT NULL,
    speed DECIMAL(6,2),
    heading DECIMAL(5,2),
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 车队相关表
-- =============================================

-- 车队表
CREATE TABLE IF NOT EXISTS mt_teams (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    destination VARCHAR(200),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    max_members INT DEFAULT 10,
    status VARCHAR(20) DEFAULT 'open',
    cover_image VARCHAR(500),
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    creator_id INT NOT NULL REFERENCES mt_users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 车队成员表
CREATE TABLE IF NOT EXISTS mt_team_members (
    id SERIAL PRIMARY KEY,
    team_id INT NOT NULL REFERENCES mt_teams(id) ON DELETE CASCADE,
    user_id INT NOT NULL REFERENCES mt_users(id),
    role VARCHAR(20) DEFAULT 'member',
    status VARCHAR(20) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_team_member UNIQUE (team_id, user_id)
);

-- =============================================
-- 索引
-- =============================================

CREATE INDEX IF NOT EXISTS idx_routes_creator ON mt_routes(creator_id);
CREATE INDEX IF NOT EXISTS idx_routes_difficulty ON mt_routes(difficulty);
CREATE INDEX IF NOT EXISTS idx_routes_public ON mt_routes(is_public);
CREATE INDEX IF NOT EXISTS idx_waypoints_route ON mt_waypoints(route_id);
CREATE INDEX IF NOT EXISTS idx_waypoints_trip ON mt_waypoints(trip_id);
CREATE INDEX IF NOT EXISTS idx_reviews_route ON mt_reviews(route_id);
CREATE INDEX IF NOT EXISTS idx_reviews_user ON mt_reviews(user_id);
CREATE INDEX IF NOT EXISTS idx_trips_user ON mt_trips(user_id);
CREATE INDEX IF NOT EXISTS idx_trips_status ON mt_trips(status);
CREATE INDEX IF NOT EXISTS idx_diaries_user ON mt_diaries(user_id);
CREATE INDEX IF NOT EXISTS idx_diaries_trip ON mt_diaries(trip_id);
CREATE INDEX IF NOT EXISTS idx_preparations_user ON mt_preparations(user_id);
CREATE INDEX IF NOT EXISTS idx_posts_user ON mt_posts(user_id);
CREATE INDEX IF NOT EXISTS idx_comments_post ON mt_comments(post_id);
CREATE INDEX IF NOT EXISTS idx_comments_parent ON mt_comments(parent_id);
CREATE INDEX IF NOT EXISTS idx_footprints_user ON mt_footprints(user_id);
CREATE INDEX IF NOT EXISTS idx_footprints_location ON mt_footprints(latitude, longitude);
CREATE INDEX IF NOT EXISTS idx_achievements_user ON mt_footprint_achievements(user_id);
CREATE INDEX IF NOT EXISTS idx_offline_maps_creator ON mt_offline_maps(creator_id);
CREATE INDEX IF NOT EXISTS idx_tiles_map ON mt_offline_map_tiles(map_id);
CREATE INDEX IF NOT EXISTS idx_danger_zones_location ON mt_danger_zones USING GIN (location);
CREATE INDEX IF NOT EXISTS idx_danger_zones_status ON mt_danger_zones(status);
CREATE INDEX IF NOT EXISTS idx_no_parking_location ON mt_no_parking_zones USING GIN (location);
CREATE INDEX IF NOT EXISTS idx_location_shares_user ON mt_location_shares(user_id);
CREATE INDEX IF NOT EXISTS idx_location_updates_share ON mt_location_updates(share_id);
CREATE INDEX IF NOT EXISTS idx_teams_creator ON mt_teams(creator_id);
CREATE INDEX IF NOT EXISTS idx_teams_status ON mt_teams(status);
CREATE INDEX IF NOT EXISTS idx_team_members_team ON mt_team_members(team_id);
CREATE INDEX IF NOT EXISTS idx_team_members_user ON mt_team_members(user_id);
CREATE INDEX IF NOT EXISTS idx_user_likes_target ON mt_user_likes(target_type, target_id);

-- =============================================
-- updated_at 自动更新触发器
-- =============================================

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 为所有包含 updated_at 的表创建触发器
DO $$
DECLARE
    tbl TEXT;
BEGIN
    FOR tbl IN SELECT table_name FROM information_schema.columns
               WHERE column_name = 'updated_at'
               AND table_schema = 'public'
               AND table_name LIKE 'mt_%'
    LOOP
        -- 先删除已存在的触发器
        EXECUTE format('DROP TRIGGER IF EXISTS update_%s_updated_at ON %s;', tbl, tbl);
        -- 然后创建新触发器
        EXECUTE format('CREATE TRIGGER update_%s_updated_at
            BEFORE UPDATE ON %s
            FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
        ', tbl, tbl);
    END LOOP;
END;
$$;
