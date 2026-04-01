-- MotoTrip 测试数据脚本
-- 为每个功能模块添加 10 条测试数据

-- =============================================
-- 1. 用户数据
-- =============================================

-- 插入 10 个用户
INSERT INTO mt_users (username, password, nickname, avatar, phone, email, bio, motorcycle, riding_experience, motorcycle_type) VALUES
('user1', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '骑行新手', 'https://randomuser.me/api/portraits/men/1.jpg', '13800138001', 'user1@example.com', '刚入门的骑行爱好者', '春风 250NK', 'newbie', 'street'),
('user2', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '骑行达人', 'https://randomuser.me/api/portraits/women/2.jpg', '13800138002', 'user2@example.com', '热爱长途骑行的老司机', '宝马 R1250GS', 'professional', 'adventure'),
('user3', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '山路专家', 'https://randomuser.me/api/portraits/men/3.jpg', '13800138003', 'user3@example.com', '专注山路骑行的技术控', 'KTM 390 Duke', 'advanced', 'naked'),
('user4', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '城市通勤', 'https://randomuser.me/api/portraits/women/4.jpg', '13800138004', 'user4@example.com', '每天骑行通勤的上班族', '本田 PCX150', 'intermediate', 'scooter'),
('user5', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '复古爱好者', 'https://randomuser.me/api/portraits/men/5.jpg', '13800138005', 'user5@example.com', '喜欢复古摩托车的收藏家', '凯旋 Bobber', 'advanced', 'cruiser'),
('user6', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '赛道选手', 'https://randomuser.me/api/portraits/women/6.jpg', '13800138006', 'user6@example.com', '业余赛道选手', '雅马哈 R6', 'professional', 'sport'),
('user7', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '摩旅达人', 'https://randomuser.me/api/portraits/men/7.jpg', '13800138007', 'user7@example.com', '每年都会长途摩旅', '铃木 DL650', 'advanced', 'adventure'),
('user8', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '新手小白', 'https://randomuser.me/api/portraits/women/8.jpg', '13800138008', 'user8@example.com', '刚买第一辆摩托车', '雅马哈 MT-03', 'newbie', 'naked'),
('user9', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '改装玩家', 'https://randomuser.me/api/portraits/men/9.jpg', '13800138009', 'user9@example.com', '喜欢改装摩托车', '川崎 Z900', 'intermediate', 'naked'),
('user10', '$2a$10$uDApE38sFuoM6.Me95i8R.KSFC9l0q8ek0ANwSaaotNWMs/o9162O', '安全驾驶倡导者', 'https://randomuser.me/api/portraits/women/10.jpg', '13800138010', 'user10@example.com', '注重安全驾驶的骑手', '宝马 S1000RR', 'professional', 'sport');

-- 为每个用户创建用户模式
INSERT INTO mt_user_modes (user_id, mode, max_ride_distance, max_speed) VALUES
(1, 'newbie', 100, 60),
(2, 'professional', 500, 120),
(3, 'advanced', 300, 100),
(4, 'intermediate', 150, 80),
(5, 'advanced', 250, 90),
(6, 'professional', 400, 130),
(7, 'advanced', 450, 110),
(8, 'newbie', 80, 50),
(9, 'intermediate', 200, 90),
(10, 'professional', 500, 140);

-- =============================================
-- 2. 路线数据
-- =============================================

-- 插入 10 条路线
INSERT INTO mt_routes (name, description, cover_image, distance, duration, difficulty, start_point, end_point, is_public, is_official, seasons, motorcycle_types, creator_id) VALUES
('北京-怀柔山路', '经典的北京周边山路骑行路线，风景优美，弯道多', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', 80.5, 3.5, 'medium', '{"lat": 39.9042, "lng": 116.4074, "name": "北京市区"}', '{"lat": 40.3194, "lng": 116.6314, "name": "怀柔区"}', true, true, '春,夏,秋', 'street,naked', 2),
('上海-苏州环湖路线', '环太湖骑行，风景秀丽，适合休闲骑行', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', 120.0, 5.0, 'easy', '{"lat": 31.2304, "lng": 121.4737, "name": "上海市"}', '{"lat": 31.2989, "lng": 120.5853, "name": "苏州市"}', true, true, '春,夏,秋', 'scooter,street', 4),
('广州-珠海沿海路线', '沿海骑行，欣赏海景，路况良好', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21', 150.0, 6.0, 'easy', '{"lat": 23.1291, "lng": 113.2644, "name": "广州市"}', '{"lat": 22.2766, "lng": 113.5491, "name": "珠海市"}', true, true, '秋,冬,春', 'all', 1),
('成都-都江堰山路', '成都周边经典骑行路线，有挑战性', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f', 90.0, 4.0, 'medium', '{"lat": 30.5728, "lng": 104.0668, "name": "成都市"}', '{"lat": 31.0019, "lng": 103.6161, "name": "都江堰市"}', true, true, '春,夏,秋', 'street,naked,adventure', 3),
('西安-骊山环山路线', '骊山环山公路，弯道多，风景美', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', 60.0, 2.5, 'medium', '{"lat": 34.3416, "lng": 108.9398, "name": "西安市"}', '{"lat": 34.3681, "lng": 109.2247, "name": "骊山"}', true, true, '春,秋', 'street,naked', 5),
('重庆-武隆山路', '重庆周边山路，风景壮观，有挑战性', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', 180.0, 7.0, 'hard', '{"lat": 29.5630, "lng": 106.5516, "name": "重庆市"}', '{"lat": 29.2816, "lng": 107.9478, "name": "武隆区"}', true, true, '春,夏,秋', 'adventure,naked', 7),
('杭州-千岛湖路线', '杭州到千岛湖的风景路线', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6', 150.0, 6.0, 'medium', '{"lat": 30.2741, "lng": 120.1551, "name": "杭州市"}', '{"lat": 29.5474, "lng": 119.0268, "name": "千岛湖"}', true, true, '春,夏,秋', 'all', 6),
('南京-黄山路线', '南京到黄山的长途路线', 'https://images.unsplash.com/photo-1470004914212-05527e49370b', 300.0, 12.0, 'hard', '{"lat": 32.0603, "lng": 118.7969, "name": "南京市"}', '{"lat": 30.1333, "lng": 118.1750, "name": "黄山市"}', true, true, '春,秋', 'adventure,sport', 10),
('深圳-惠州沿海路线', '深圳到惠州的沿海骑行路线', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07', 120.0, 5.0, 'easy', '{"lat": 22.5431, "lng": 114.0579, "name": "深圳市"}', '{"lat": 22.9576, "lng": 114.4071, "name": "惠州市"}', true, true, '秋,冬,春', 'all', 8),
('武汉-神农架路线', '武汉到神农架的长途摩旅路线', 'https://images.unsplash.com/photo-1562408590-e329310d97fc', 400.0, 15.0, 'hard', '{"lat": 30.5928, "lng": 114.3055, "name": "武汉市"}', '{"lat": 31.7479, "lng": 110.4865, "name": "神农架"}', true, true, '夏,秋', 'adventure', 9);

-- 为每条路线添加 3 个途经点
-- 路线 1 的途经点
INSERT INTO mt_waypoints (name, description, type, location, sequence, route_id) VALUES
('怀柔水库', '风景优美的水库，适合休息', 'viewpoint', '{"lat": 40.3384, "lng": 116.6372}', 1, 1),
('红螺寺', '著名的佛教寺庙', 'attraction', '{"lat": 40.3412, "lng": 116.6578}', 2, 1),
('慕田峪长城', '长城著名段落', 'attraction', '{"lat": 40.3638, "lng": 116.5706}', 3, 1);

-- 路线 2 的途经点
INSERT INTO mt_waypoints (name, description, type, location, sequence, route_id) VALUES
('阳澄湖', '著名的阳澄湖大闸蟹产地', 'viewpoint', '{"lat": 31.3464, "lng": 120.7821}', 1, 2),
('太湖国家湿地公园', '生态公园', 'attraction', '{"lat": 31.2833, "lng": 120.2917}', 2, 2),
('苏州园林', '苏州著名园林', 'attraction', '{"lat": 31.2989, "lng": 120.5853}', 3, 2);

-- 路线 3 的途经点
INSERT INTO mt_waypoints (name, description, type, location, sequence, route_id) VALUES
('南沙湿地公园', '沿海湿地公园', 'viewpoint', '{"lat": 22.7569, "lng": 113.5408}', 1, 3),
('珠海渔女', '珠海标志性景点', 'attraction', '{"lat": 22.2569, "lng": 113.5411}', 2, 3),
('情侣路', '珠海著名沿海公路', 'viewpoint', '{"lat": 22.2288, "lng": 113.5417}', 3, 3);

-- 路线 4 的途经点
INSERT INTO mt_waypoints (name, description, type, location, sequence, route_id) VALUES
('青城山', '著名道教名山', 'attraction', '{"lat": 30.9147, "lng": 103.5774}', 1, 4),
('都江堰景区', '世界文化遗产', 'attraction', '{"lat": 31.0019, "lng": 103.6161}', 2, 4),
('龙池国家森林公园', '自然保护区', 'viewpoint', '{"lat": 31.0539, "lng": 103.6426}', 3, 4);

-- 路线 5 的途经点
INSERT INTO mt_waypoints (name, description, type, location, sequence, route_id) VALUES
('华清池', '著名温泉景区', 'attraction', '{"lat": 34.3665, "lng": 109.2088}', 1, 5),
('骊山森林公园', '国家森林公园', 'viewpoint', '{"lat": 34.3681, "lng": 109.2247}', 2, 5),
('秦始皇陵', '世界文化遗产', 'attraction', '{"lat": 34.3841, "lng": 109.2785}', 3, 5);

-- 为路线 6-10 也添加途经点（简化处理）
INSERT INTO mt_waypoints (name, description, type, location, sequence, route_id) VALUES
('武隆天生三桥', '著名自然景观', 'attraction', '{"lat": 29.2816, "lng": 107.9478}', 1, 6),
('仙女山', '国家森林公园', 'viewpoint', '{"lat": 29.3333, "lng": 108.0000}', 2, 6),
('芙蓉洞', '溶洞景观', 'attraction', '{"lat": 29.3167, "lng": 107.8833}', 3, 6),
('千岛湖中心湖区', '千岛湖主要景区', 'viewpoint', '{"lat": 29.5474, "lng": 119.0268}', 1, 7),
('梅峰岛', '千岛湖著名岛屿', 'attraction', '{"lat": 29.5667, "lng": 119.0500}', 2, 7),
('猴岛', '千岛湖特色岛屿', 'attraction', '{"lat": 29.5333, "lng": 119.0000}', 3, 7),
('黄山风景区', '世界文化与自然双重遗产', 'attraction', '{"lat": 30.1333, "lng": 118.1750}', 1, 8),
('宏村', '徽派古建筑群', 'attraction', '{"lat": 30.1000, "lng": 117.9750}', 2, 8),
('屯溪老街', '历史文化街区', 'attraction', '{"lat": 29.7167, "lng": 118.3167}', 3, 8),
('惠州西湖', '国家5A级景区', 'viewpoint', '{"lat": 23.0833, "lng": 114.4000}', 1, 9),
('双月湾', '著名海湾', 'viewpoint', '{"lat": 22.5000, "lng": 114.8333}', 2, 9),
('巽寮湾', '海滨旅游度假区', 'viewpoint', '{"lat": 22.6333, "lng": 114.7000}', 3, 9),
('神农架国家森林公园', '自然保护区', 'viewpoint', '{"lat": 31.7479, "lng": 110.4865}', 1, 10),
('神农顶', '神农架主峰', 'attraction', '{"lat": 31.7500, "lng": 110.4667}', 2, 10),
('大九湖', '高山湖泊', 'viewpoint', '{"lat": 31.4833, "lng": 110.1500}', 3, 10);

-- 为每条路线添加 5 条评论
-- 路线 1 的评论
INSERT INTO mt_reviews (user_id, route_id, rating, content) VALUES
(1, 1, 5, '非常棒的路线，风景优美，弯道挑战性适中'),
(3, 1, 4, '山路路况良好，适合周末骑行'),
(5, 1, 5, '怀柔的风景真的很美，推荐给所有骑友'),
(7, 1, 4, '适合中级骑手，有一定的弯道技巧要求'),
(9, 1, 5, '这条路线我已经骑了很多次，每次都有不同的感受');

-- 路线 2 的评论
INSERT INTO mt_reviews (user_id, route_id, rating, content) VALUES
(2, 2, 5, '环湖路线非常适合休闲骑行，风景如画'),
(4, 2, 4, '路况很好，适合 scooter 骑行'),
(6, 2, 4, '周末人有点多，建议早出发'),
(8, 2, 5, '非常适合新手骑行的路线'),
(10, 2, 4, '沿途有很多休息点，很方便');

-- 路线 3 的评论
INSERT INTO mt_reviews (user_id, route_id, rating, content) VALUES
(1, 3, 5, '沿海路线风景真美，海风拂面很舒服'),
(3, 3, 4, '路况良好，适合各种类型的摩托车'),
(5, 3, 5, '珠海的海边公路真的很棒'),
(7, 3, 4, '适合长途骑行，沿途补给方便'),
(9, 3, 5, '这条路线我强烈推荐给所有骑友');

-- 路线 4 的评论
INSERT INTO mt_reviews (user_id, route_id, rating, content) VALUES
(2, 4, 5, '成都周边的经典路线，山路很有挑战性'),
(4, 4, 3, '对于新手来说有点难度'),
(6, 4, 5, '都江堰的风景真的很美'),
(8, 4, 4, '虽然有难度，但很值得骑行'),
(10, 4, 5, '这条路线的弯道很考验技术');

-- 路线 5 的评论
INSERT INTO mt_reviews (user_id, route_id, rating, content) VALUES
(1, 5, 5, '骊山的环山公路太爽了'),
(3, 5, 4, '弯道很多，很有驾驶乐趣'),
(5, 5, 5, '西安周边的绝佳骑行路线'),
(7, 5, 4, '建议春秋季节骑行，夏天太热'),
(9, 5, 5, '这条路线我已经骑了很多次，百骑不厌');

-- 为路线 6-10 也添加评论（简化处理）
INSERT INTO mt_reviews (user_id, route_id, rating, content) VALUES
(2, 6, 5, '重庆到武隆的路线风景壮观'),
(4, 6, 4, '路况复杂，需要丰富的骑行经验'),
(6, 6, 5, '非常具有挑战性的路线'),
(8, 6, 3, '对于新手来说难度较大'),
(10, 6, 5, '这条路线是我骑过的最棒的路线之一'),
(1, 7, 5, '杭州到千岛湖的路线风景优美'),
(3, 7, 4, '适合全家出游的路线'),
(5, 7, 5, '沿途的风景真的很美'),
(7, 7, 4, '路况良好，骑行体验很棒'),
(9, 7, 5, '强烈推荐这条路线'),
(2, 8, 5, '南京到黄山的长途路线很有挑战性'),
(4, 8, 4, '需要充分的准备和体力'),
(6, 8, 5, '沿途的风景值得长途骑行'),
(8, 8, 3, '对于新手来说太长了'),
(10, 8, 5, '这是一条非常经典的长途摩旅路线'),
(1, 9, 5, '深圳到惠州的沿海路线很适合休闲骑行'),
(3, 9, 4, '路况良好，风景优美'),
(5, 9, 5, '惠州的海滩真的很美'),
(7, 9, 4, '适合周末短途骑行'),
(9, 9, 5, '这条路线我经常骑，非常喜欢'),
(2, 10, 5, '武汉到神农架的路线是真正的摩旅路线'),
(4, 10, 4, '长途骑行需要充分准备'),
(6, 10, 5, '神农架的风景真的太美了'),
(8, 10, 3, '对于新手来说难度较大'),
(10, 10, 5, '这是一条值得挑战的长途路线');

-- 为路线添加点赞
INSERT INTO mt_user_likes (user_id, target_type, target_id) VALUES
(1, 'route', 1),
(2, 'route', 1),
(3, 'route', 1),
(4, 'route', 1),
(5, 'route', 1),
(1, 'route', 2),
(2, 'route', 2),
(3, 'route', 2),
(4, 'route', 2),
(5, 'route', 2),
(1, 'route', 3),
(2, 'route', 3),
(3, 'route', 3),
(4, 'route', 3),
(5, 'route', 3),
(1, 'route', 4),
(2, 'route', 4),
(3, 'route', 4),
(4, 'route', 4),
(5, 'route', 4),
(1, 'route', 5),
(2, 'route', 5),
(3, 'route', 5),
(4, 'route', 5),
(5, 'route', 5),
(6, 'route', 6),
(7, 'route', 6),
(8, 'route', 6),
(9, 'route', 6),
(10, 'route', 6),
(6, 'route', 7),
(7, 'route', 7),
(8, 'route', 7),
(9, 'route', 7),
(10, 'route', 7),
(6, 'route', 8),
(7, 'route', 8),
(8, 'route', 8),
(9, 'route', 8),
(10, 'route', 8),
(6, 'route', 9),
(7, 'route', 9),
(8, 'route', 9),
(9, 'route', 9),
(10, 'route', 9),
(6, 'route', 10),
(7, 'route', 10),
(8, 'route', 10),
(9, 'route', 10),
(10, 'route', 10);

-- 更新路线的点赞数
UPDATE mt_routes SET likes = (SELECT COUNT(*) FROM mt_user_likes WHERE target_type = 'route' AND target_id = mt_routes.id);

-- 更新路线的评论数和平均评分
UPDATE mt_routes SET 
    review_count = (SELECT COUNT(*) FROM mt_reviews WHERE route_id = mt_routes.id),
    avg_rating = (SELECT AVG(rating) FROM mt_reviews WHERE route_id = mt_routes.id);

-- =============================================
-- 3. 行程数据
-- =============================================

-- 插入 10 条行程
INSERT INTO mt_trips (name, description, cover_image, start_date, end_date, status, total_distance, user_id, route_id) VALUES
('周末怀柔骑行', '周末两天的怀柔山路骑行', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', '2026-04-01', '2026-04-02', 'completed', 160.0, 1, 1),
('环太湖骑行', '为期三天的环太湖骑行', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', '2026-04-10', '2026-04-12', 'completed', 300.0, 2, 2),
('广州珠海一日游', '广州到珠海的一日往返', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21', '2026-04-05', '2026-04-05', 'completed', 300.0, 3, 3),
('成都周边游', '成都到都江堰的周末游', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f', '2026-04-15', '2026-04-16', 'completed', 180.0, 4, 4),
('西安骊山行', '西安到骊山的一日游', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', '2026-04-08', '2026-04-08', 'completed', 120.0, 5, 5),
('重庆武隆之旅', '重庆到武隆的三天行程', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', '2026-04-20', '2026-04-22', 'upcoming', 540.0, 6, 6),
('杭州千岛湖之旅', '杭州到千岛湖的两天行程', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6', '2026-04-25', '2026-04-26', 'planning', 300.0, 7, 7),
('南京黄山长途游', '南京到黄山的五天行程', 'https://images.unsplash.com/photo-1470004914212-05527e49370b', '2026-05-01', '2026-05-05', 'planning', 1500.0, 8, 8),
('深圳惠州沿海游', '深圳到惠州的周末游', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07', '2026-04-18', '2026-04-19', 'completed', 240.0, 9, 9),
('武汉神农架长途摩旅', '武汉到神农架的七天行程', 'https://images.unsplash.com/photo-1562408590-e329310d97fc', '2026-05-10', '2026-05-16', 'planning', 2800.0, 10, 10);

-- 为每个行程添加 3 篇日记
-- 行程 1 的日记
INSERT INTO mt_diaries (title, content, images, location, location_name, weather, temperature, mood, user_id, trip_id) VALUES
('第一天：北京市区到怀柔', '今天从北京市区出发，沿着山路骑行到怀柔，风景很美，路况良好。', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', '{"lat": 40.3194, "lng": 116.6314}', '怀柔区', '晴天', '22°C', 'happy', 1, 1),
('第二天：怀柔返程', '今天从怀柔返程，路上车有点多，但骑行体验还是很棒。', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', '{"lat": 39.9042, "lng": 116.4074}', '北京市区', '多云', '20°C', 'happy', 1, 1),
('怀柔骑行总结', '这次怀柔骑行非常愉快，山路骑行很有挑战性，风景也很美，下次还会再来。', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', '{"lat": 40.3194, "lng": 116.6314}', '怀柔区', '晴天', '22°C', 'excited', 1, 1);

-- 行程 2 的日记
INSERT INTO mt_diaries (title, content, images, location, location_name, weather, temperature, mood, user_id, trip_id) VALUES
('第一天：上海到苏州', '今天从上海出发，骑行到苏州，沿途风景优美。', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', '{"lat": 31.2989, "lng": 120.5853}', '苏州市', '晴天', '24°C', 'happy', 2, 2),
('第二天：环太湖', '今天环太湖骑行，风景如画，湖水清澈。', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', '{"lat": 31.2833, "lng": 120.2917}', '太湖', '多云', '22°C', 'excited', 2, 2),
('第三天：返程', '今天从苏州返回上海，结束了愉快的环太湖之旅。', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', '{"lat": 31.2304, "lng": 121.4737}', '上海市', '晴天', '23°C', 'happy', 2, 2);

-- 为行程 3-10 也添加日记（简化处理）
INSERT INTO mt_diaries (title, content, images, location, location_name, weather, temperature, mood, user_id, trip_id) VALUES
('广州到珠海', '今天从广州骑行到珠海，沿海路线风景真美。', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21', '{"lat": 22.2766, "lng": 113.5491}', '珠海市', '晴天', '28°C', 'happy', 3, 3),
('珠海返程', '今天从珠海返回广州，虽然有点累，但很值得。', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21', '{"lat": 23.1291, "lng": 113.2644}', '广州市', '晴天', '29°C', 'tired', 3, 3),
('广州珠海一日游总结', '虽然只是一天的行程，但骑行体验非常棒，沿海路线风景优美。', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21', '{"lat": 22.2766, "lng": 113.5491}', '珠海市', '晴天', '28°C', 'happy', 3, 3),
('成都到都江堰', '今天从成都骑行到都江堰，山路很有挑战性。', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f', '{"lat": 31.0019, "lng": 103.6161}', '都江堰市', '多云', '20°C', 'excited', 4, 4),
('都江堰返程', '今天从都江堰返回成都，沿途风景优美。', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f', '{"lat": 30.5728, "lng": 104.0668}', '成都市', '晴天', '22°C', 'happy', 4, 4),
('成都周边游总结', '这次成都周边游非常愉快，都江堰的风景很美。', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f', '{"lat": 31.0019, "lng": 103.6161}', '都江堰市', '多云', '20°C', 'happy', 4, 4),
('西安到骊山', '今天从西安骑行到骊山，环山公路很有驾驶乐趣。', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', '{"lat": 34.3681, "lng": 109.2247}', '骊山', '晴天', '18°C', 'excited', 5, 5),
('骊山返程', '今天从骊山返回西安，结束了愉快的一天。', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', '{"lat": 34.3416, "lng": 108.9398}', '西安市', '晴天', '20°C', 'happy', 5, 5),
('骊山骑行总结', '骊山的环山公路真的很棒，弯道很多，驾驶乐趣十足。', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', '{"lat": 34.3681, "lng": 109.2247}', '骊山', '晴天', '18°C', 'excited', 5, 5),
('重庆到武隆', '今天从重庆出发，骑行到武隆，路况复杂但风景壮观。', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', '{"lat": 29.2816, "lng": 107.9478}', '武隆区', '多云', '22°C', 'excited', 6, 6),
('武隆游玩', '今天在武隆游玩，天生三桥真的很壮观。', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', '{"lat": 29.2816, "lng": 107.9478}', '武隆区', '晴天', '24°C', 'happy', 6, 6),
('武隆返程', '今天从武隆返回重庆，结束了愉快的三天行程。', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', '{"lat": 29.5630, "lng": 106.5516}', '重庆市', '多云', '23°C', 'tired', 6, 6),
('杭州到千岛湖', '今天从杭州出发，骑行到千岛湖，沿途风景优美。', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6', '{"lat": 29.5474, "lng": 119.0268}', '千岛湖', '晴天', '25°C', 'happy', 7, 7),
('千岛湖游玩', '今天在千岛湖游玩，湖水清澈，风景如画。', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6', '{"lat": 29.5474, "lng": 119.0268}', '千岛湖', '晴天', '26°C', 'excited', 7, 7),
('千岛湖返程', '今天从千岛湖返回杭州，结束了愉快的两天行程。', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6', '{"lat": 30.2741, "lng": 120.1551}', '杭州市', '多云', '24°C', 'happy', 7, 7),
('南京到黄山', '今天从南京出发，开始了前往黄山的长途骑行。', 'https://images.unsplash.com/photo-1470004914212-05527e49370b', '{"lat": 31.0000, "lng": 118.0000}', '途中', '晴天', '22°C', 'excited', 8, 8),
('黄山游玩', '今天到达黄山，开始游玩黄山风景区。', 'https://images.unsplash.com/photo-1470004914212-05527e49370b', '{"lat": 30.1333, "lng": 118.1750}', '黄山市', '多云', '18°C', 'happy', 8, 8),
('黄山返程', '今天从黄山返回南京，结束了为期五天的长途骑行。', 'https://images.unsplash.com/photo-1470004914212-05527e49370b', '{"lat": 32.0603, "lng": 118.7969}', '南京市', '晴天', '23°C', 'tired', 8, 8),
('深圳到惠州', '今天从深圳出发，骑行到惠州，沿海路线风景优美。', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07', '{"lat": 22.9576, "lng": 114.4071}', '惠州市', '晴天', '28°C', 'happy', 9, 9),
('惠州游玩', '今天在惠州游玩，双月湾的风景真的很美。', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07', '{"lat": 22.5000, "lng": 114.8333}', '双月湾', '晴天', '29°C', 'excited', 9, 9),
('惠州返程', '今天从惠州返回深圳，结束了愉快的周末游。', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07', '{"lat": 22.5431, "lng": 114.0579}', '深圳市', '多云', '27°C', 'happy', 9, 9),
('武汉到神农架', '今天从武汉出发，开始了前往神农架的长途摩旅。', 'https://images.unsplash.com/photo-1562408590-e329310d97fc', '{"lat": 31.0000, "lng": 112.0000}', '途中', '晴天', '20°C', 'excited', 10, 10),
('神农架游玩', '今天到达神农架，开始游玩神农架国家森林公园。', 'https://images.unsplash.com/photo-1562408590-e329310d97fc', '{"lat": 31.7479, "lng": 110.4865}', '神农架', '多云', '16°C', 'happy', 10, 10),
('神农架返程', '今天从神农架返回武汉，结束了为期七天的长途摩旅。', 'https://images.unsplash.com/photo-1562408590-e329310d97fc', '{"lat": 30.5928, "lng": 114.3055}', '武汉市', '晴天', '22°C', 'tired', 10, 10);

-- 为每个用户添加 5 个准备清单
INSERT INTO mt_preparations (name, category, description, is_essential, is_packed, quantity, user_id) VALUES
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 1),
('骑行手套', 'safety', '保护手部', true, true, 1, 1),
('骑行服', 'clothing', '防风防雨', true, true, 1, 1),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 1),
('工具包', 'tools', '应急维修工具', true, true, 1, 1),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 2),
('骑行手套', 'safety', '保护手部', true, true, 1, 2),
('骑行服', 'clothing', '防风防雨', true, true, 1, 2),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 2),
('工具包', 'tools', '应急维修工具', true, true, 1, 2),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 3),
('骑行手套', 'safety', '保护手部', true, true, 1, 3),
('骑行服', 'clothing', '防风防雨', true, true, 1, 3),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 3),
('工具包', 'tools', '应急维修工具', true, true, 1, 3),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 4),
('骑行手套', 'safety', '保护手部', true, true, 1, 4),
('骑行服', 'clothing', '防风防雨', true, true, 1, 4),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 4),
('工具包', 'tools', '应急维修工具', true, true, 1, 4),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 5),
('骑行手套', 'safety', '保护手部', true, true, 1, 5),
('骑行服', 'clothing', '防风防雨', true, true, 1, 5),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 5),
('工具包', 'tools', '应急维修工具', true, true, 1, 5),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 6),
('骑行手套', 'safety', '保护手部', true, true, 1, 6),
('骑行服', 'clothing', '防风防雨', true, true, 1, 6),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 6),
('工具包', 'tools', '应急维修工具', true, true, 1, 6),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 7),
('骑行手套', 'safety', '保护手部', true, true, 1, 7),
('骑行服', 'clothing', '防风防雨', true, true, 1, 7),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 7),
('工具包', 'tools', '应急维修工具', true, true, 1, 7),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 8),
('骑行手套', 'safety', '保护手部', true, true, 1, 8),
('骑行服', 'clothing', '防风防雨', true, true, 1, 8),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 8),
('工具包', 'tools', '应急维修工具', true, true, 1, 8),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 9),
('骑行手套', 'safety', '保护手部', true, true, 1, 9),
('骑行服', 'clothing', '防风防雨', true, true, 1, 9),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 9),
('工具包', 'tools', '应急维修工具', true, true, 1, 9),
('头盔', 'safety', '骑行必备安全装备', true, true, 1, 10),
('骑行手套', 'safety', '保护手部', true, true, 1, 10),
('骑行服', 'clothing', '防风防雨', true, true, 1, 10),
('骑行靴', 'clothing', '保护脚部', true, true, 1, 10),
('工具包', 'tools', '应急维修工具', true, true, 1, 10);

-- 为每个行程添加行程分享
INSERT INTO mt_trip_shares (user_id, trip_id, title, summary, total_distance, is_shared) VALUES
(1, 1, '周末怀柔骑行分享', '两天的怀柔山路骑行，风景优美，弯道挑战性适中', 160.0, true),
(2, 2, '环太湖骑行分享', '三天的环太湖骑行，风景如画，路况良好', 300.0, true),
(3, 3, '广州珠海一日游分享', '广州到珠海的一日往返，沿海路线风景优美', 300.0, true),
(4, 4, '成都周边游分享', '成都到都江堰的周末游，山路很有挑战性', 180.0, true),
(5, 5, '西安骊山行分享', '西安到骊山的一日游，环山公路很有驾驶乐趣', 120.0, true),
(6, 6, '重庆武隆之旅分享', '重庆到武隆的三天行程，风景壮观', 540.0, false),
(7, 7, '杭州千岛湖之旅分享', '杭州到千岛湖的两天行程，风景优美', 300.0, false),
(8, 8, '南京黄山长途游分享', '南京到黄山的五天行程，长途摩旅的挑战', 1500.0, false),
(9, 9, '深圳惠州沿海游分享', '深圳到惠州的周末游，沿海路线风景优美', 240.0, true),
(10, 10, '武汉神农架长途摩旅分享', '武汉到神农架的七天行程，真正的摩旅体验', 2800.0, false);

-- =============================================
-- 4. 社交数据
-- =============================================

-- 插入 10 条帖子
INSERT INTO mt_posts (content, images, location, likes, comments, user_id) VALUES
('今天骑行怀柔山路，风景真美！#骑行 #山路', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad,https://images.unsplash.com/photo-1543349689-9a4d426bee8e', '北京市怀柔区', 25, 5, 1),
('环太湖骑行，风景如画，推荐给所有骑友！#骑行 #环湖', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa,https://images.unsplash.com/photo-1564013799919-ab600027ffc6', '苏州市太湖', 30, 8, 2),
('广州到珠海的沿海路线，海风拂面，太舒服了！#骑行 #沿海', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21,https://images.unsplash.com/photo-1534802177853-8a286d973a07', '珠海市', 20, 4, 3),
('成都到都江堰的山路，挑战性十足，骑行体验很棒！#骑行 #山路', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f,https://images.unsplash.com/photo-1556911220-bff31c812dba', '都江堰市', 18, 3, 4),
('骊山的环山公路，弯道之王，太爽了！#骑行 #弯道', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e,https://images.unsplash.com/photo-1558980394-a4155224a1ad', '西安市骊山', 22, 6, 5),
('重庆到武隆的路线，风景壮观，值得挑战！#骑行 #长途', 'https://images.unsplash.com/photo-1556911220-bff31c812dba,https://images.unsplash.com/photo-1541339907198-e08756dedf3f', '重庆市武隆区', 28, 7, 6),
('杭州到千岛湖的路线，风景优美，适合休闲骑行！#骑行 #休闲', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6,https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', '杭州市千岛湖', 24, 5, 7),
('南京到黄山的长途骑行，挑战自我，收获满满！#骑行 #长途', 'https://images.unsplash.com/photo-1470004914212-05527e49370b,https://images.unsplash.com/photo-1562408590-e329310d97fc', '黄山市', 32, 9, 8),
('深圳到惠州的沿海路线，周末骑行的绝佳选择！#骑行 #沿海', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07,https://images.unsplash.com/photo-1506929562872-bb421503ef21', '惠州市', 21, 4, 9),
('武汉到神农架的长途摩旅，真正的摩旅体验！#骑行 #摩旅', 'https://images.unsplash.com/photo-1562408590-e329310d97fc,https://images.unsplash.com/photo-1470004914212-05527e49370b', '神农架', 35, 10, 10);

-- 为每个帖子添加 3 条评论
-- 帖子 1 的评论
INSERT INTO mt_comments (content, post_id, user_id) VALUES
('这条路线我也骑过，确实很棒！', 1, 2),
('请问路况如何？适合新手吗？', 1, 3),
('风景真美，下次一起骑！', 1, 4);

-- 帖子 2 的评论
INSERT INTO mt_comments (content, post_id, user_id) VALUES
('环太湖骑行一直是我的梦想，请问需要几天时间？', 2, 1),
('路况很好，适合各种类型的摩托车', 2, 3),
('周末人多吗？', 2, 5);

-- 为帖子 3-10 也添加评论（简化处理）
INSERT INTO mt_comments (content, post_id, user_id) VALUES
('沿海路线风景确实很美', 3, 1),
('请问单程需要多长时间？', 3, 2),
('珠海的海边公路真的很棒', 3, 4),
('都江堰的山路确实有挑战性', 4, 1),
('适合什么级别的骑手？', 4, 2),
('成都周边还有其他好的路线吗？', 4, 3),
('骊山的环山公路我也很喜欢', 5, 1),
('弯道很多，需要注意安全', 5, 2),
('春秋季节骑行最合适', 5, 3),
('重庆到武隆的路线风景确实壮观', 6, 1),
('路况复杂，需要丰富的骑行经验', 6, 2),
('建议多带些补给', 6, 3),
('千岛湖的风景真的很美', 7, 1),
('适合全家出游', 7, 2),
('周末人多吗？', 7, 3),
('南京到黄山的长途骑行很有挑战性', 8, 1),
('需要准备哪些装备？', 8, 2),
('建议分几天完成', 8, 3),
('深圳到惠州的沿海路线很适合周末骑行', 9, 1),
('路况如何？', 9, 2),
('有哪些值得停留的地方？', 9, 3),
('神农架的风景真的太美了', 10, 1),
('长途摩旅需要注意什么？', 10, 2),
('建议什么季节去？', 10, 3);

-- 为帖子添加点赞
INSERT INTO mt_user_likes (user_id, target_type, target_id) VALUES
(1, 'post', 1),
(2, 'post', 1),
(3, 'post', 1),
(4, 'post', 1),
(5, 'post', 1),
(1, 'post', 2),
(2, 'post', 2),
(3, 'post', 2),
(4, 'post', 2),
(5, 'post', 2),
(1, 'post', 3),
(2, 'post', 3),
(3, 'post', 3),
(4, 'post', 3),
(5, 'post', 3),
(1, 'post', 4),
(2, 'post', 4),
(3, 'post', 4),
(4, 'post', 4),
(5, 'post', 4),
(1, 'post', 5),
(2, 'post', 5),
(3, 'post', 5),
(4, 'post', 5),
(5, 'post', 5),
(6, 'post', 6),
(7, 'post', 6),
(8, 'post', 6),
(9, 'post', 6),
(10, 'post', 6),
(6, 'post', 7),
(7, 'post', 7),
(8, 'post', 7),
(9, 'post', 7),
(10, 'post', 7),
(6, 'post', 8),
(7, 'post', 8),
(8, 'post', 8),
(9, 'post', 8),
(10, 'post', 8),
(6, 'post', 9),
(7, 'post', 9),
(8, 'post', 9),
(9, 'post', 9),
(10, 'post', 9),
(6, 'post', 10),
(7, 'post', 10),
(8, 'post', 10),
(9, 'post', 10),
(10, 'post', 10);

-- 更新帖子的点赞数和评论数
UPDATE mt_posts SET 
    likes = (SELECT COUNT(*) FROM mt_user_likes WHERE target_type = 'post' AND target_id = mt_posts.id),
    comments = (SELECT COUNT(*) FROM mt_comments WHERE post_id = mt_posts.id);

-- 插入 10 条足迹
INSERT INTO mt_footprints (user_id, latitude, longitude, location_name, province, city, district, image, note, visit_count, distance) VALUES
(1, 39.9042, 116.4074, '北京市区', '北京市', '北京市', '东城区', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', '北京市中心', 5, 0.0),
(1, 40.3194, 116.6314, '怀柔区', '北京市', '北京市', '怀柔区', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', '怀柔山路', 3, 80.5),
(2, 31.2304, 121.4737, '上海市', '上海市', '上海市', '黄浦区', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', '上海市中心', 4, 0.0),
(2, 31.2989, 120.5853, '苏州市', '江苏省', '苏州市', '姑苏区', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6', '苏州园林', 2, 120.0),
(3, 23.1291, 113.2644, '广州市', '广东省', '广州市', '天河区', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21', '广州市中心', 6, 0.0),
(3, 22.2766, 113.5491, '珠海市', '广东省', '珠海市', '香洲区', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07', '珠海渔女', 3, 150.0),
(4, 30.5728, 104.0668, '成都市', '四川省', '成都市', '锦江区', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f', '成都市中心', 5, 0.0),
(4, 31.0019, 103.6161, '都江堰市', '四川省', '成都市', '都江堰市', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', '都江堰景区', 2, 90.0),
(5, 34.3416, 108.9398, '西安市', '陕西省', '西安市', '碑林区', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', '西安市中心', 4, 0.0),
(5, 34.3681, 109.2247, '骊山', '陕西省', '西安市', '临潼区', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', '骊山环山公路', 3, 60.0);

-- 插入 10 条足迹成就
INSERT INTO mt_footprint_achievements (type, user_id, name, description, icon, target_count, current_count, unlocked) VALUES
('distance', 1, '初级骑行者', '累计骑行距离达到 100 公里', '🏍️', 100, 160, true),
('distance', 1, '中级骑行者', '累计骑行距离达到 500 公里', '🏍️', 500, 160, false),
('distance', 2, '初级骑行者', '累计骑行距离达到 100 公里', '🏍️', 100, 300, true),
('distance', 2, '中级骑行者', '累计骑行距离达到 500 公里', '🏍️', 500, 300, false),
('distance', 3, '初级骑行者', '累计骑行距离达到 100 公里', '🏍️', 100, 300, true),
('distance', 3, '中级骑行者', '累计骑行距离达到 500 公里', '🏍️', 500, 300, false),
('distance', 4, '初级骑行者', '累计骑行距离达到 100 公里', '🏍️', 100, 180, true),
('distance', 4, '中级骑行者', '累计骑行距离达到 500 公里', '🏍️', 500, 180, false),
('distance', 5, '初级骑行者', '累计骑行距离达到 100 公里', '🏍️', 100, 120, true),
('distance', 5, '中级骑行者', '累计骑行距离达到 500 公里', '🏍️', 500, 120, false);

-- =============================================
-- 5. 地图安全数据
-- =============================================

-- 插入 10 个危险区域
INSERT INTO mt_danger_zones (name, description, location, radius, type, severity, status, reporter_id) VALUES
('怀柔山路弯道', '连续弯道，视线不佳', '{"lat": 40.3384, "lng": 116.6372}', 200, 'curve', 'medium', 'active', 1),
('太湖大桥', '桥面湿滑，容易侧滑', '{"lat": 31.2833, "lng": 120.2917}', 150, 'bridge', 'light', 'active', 2),
('珠海沿海公路', '海风大，影响操控', '{"lat": 22.2569, "lng": 113.5411}', 300, 'coastal', 'medium', 'active', 3),
('都江堰山路', '坡度陡峭，需要低档行驶', '{"lat": 31.0019, "lng": 103.6161}', 250, 'hill', 'medium', 'active', 4),
('骊山环山公路', '急弯较多，需减速慢行', '{"lat": 34.3681, "lng": 109.2247}', 200, 'curve', 'medium', 'active', 5),
('武隆山路', '路况复杂，多处落石', '{"lat": 29.2816, "lng": 107.9478}', 350, 'mountain', 'high', 'active', 6),
('千岛湖景区', '游客较多，注意避让', '{"lat": 29.5474, "lng": 119.0268}', 150, 'tourist', 'light', 'active', 7),
('黄山景区', '山路狭窄，会车困难', '{"lat": 30.1333, "lng": 118.1750}', 200, 'mountain', 'medium', 'active', 8),
('惠州沿海公路', '急转弯，需减速', '{"lat": 22.5000, "lng": 114.8333}', 180, 'curve', 'light', 'active', 9),
('神农架山路', '海拔高，气温低', '{"lat": 31.7479, "lng": 110.4865}', 400, 'mountain', 'high', 'active', 10);

-- 插入 10 个禁停区域
INSERT INTO mt_no_parking_zones (name, description, location, radius, reporter_id) VALUES
('北京市区商圈', '商业区禁止停车', '{"lat": 39.9042, "lng": 116.4074}', 100, 1),
('上海外滩', '旅游区禁止停车', '{"lat": 31.2304, "lng": 121.4737}', 150, 2),
('广州天河商圈', '商业区禁止停车', '{"lat": 23.1291, "lng": 113.2644}', 100, 3),
('成都春熙路', '商业区禁止停车', '{"lat": 30.5728, "lng": 104.0668}', 100, 4),
('西安钟楼', '历史景区禁止停车', '{"lat": 34.3416, "lng": 108.9398}', 150, 5),
('重庆解放碑', '商业区禁止停车', '{"lat": 29.5630, "lng": 106.5516}', 100, 6),
('杭州西湖', '旅游区禁止停车', '{"lat": 30.2741, "lng": 120.1551}', 200, 7),
('南京夫子庙', '历史景区禁止停车', '{"lat": 32.0603, "lng": 118.7969}', 150, 8),
('深圳福田商圈', '商业区禁止停车', '{"lat": 22.5431, "lng": 114.0579}', 100, 9),
('武汉黄鹤楼', '历史景区禁止停车', '{"lat": 30.5928, "lng": 114.3055}', 150, 10);

-- 插入 10 个离线地图
INSERT INTO mt_offline_maps (name, description, min_lat, max_lat, min_lng, max_lng, map_provider, creator_id, status) VALUES
('北京市区地图', '北京市中心区域离线地图', 39.8, 40.0, 116.2, 116.6, 'amap', 1, 'completed'),
('上海市区地图', '上海市中心区域离线地图', 31.1, 31.3, 121.3, 121.6, 'amap', 2, 'completed'),
('广州市区地图', '广州市中心区域离线地图', 23.0, 23.2, 113.2, 113.4, 'amap', 3, 'completed'),
('成都市区地图', '成都市中心区域离线地图', 30.5, 30.7, 104.0, 104.2, 'amap', 4, 'completed'),
('西安市区地图', '西安市中心区域离线地图', 34.2, 34.4, 108.9, 109.1, 'amap', 5, 'completed'),
('重庆市区地图', '重庆市中心区域离线地图', 29.5, 29.7, 106.4, 106.7, 'amap', 6, 'completed'),
('杭州市区地图', '杭州市中心区域离线地图', 30.2, 30.3, 120.1, 120.3, 'amap', 7, 'completed'),
('南京市区地图', '南京市中心区域离线地图', 32.0, 32.1, 118.7, 118.9, 'amap', 8, 'completed'),
('深圳市区地图', '深圳市中心区域离线地图', 22.5, 22.6, 114.0, 114.2, 'amap', 9, 'completed'),
('武汉市区地图', '武汉市中心区域离线地图', 30.5, 30.7, 114.2, 114.4, 'amap', 10, 'completed');

-- 插入 10 个位置共享
INSERT INTO mt_location_shares (user_id, share_code, is_active, expires_at) VALUES
(1, 'SHARE123456', true, '2026-04-30 23:59:59'),
(2, 'SHARE234567', true, '2026-04-30 23:59:59'),
(3, 'SHARE345678', true, '2026-04-30 23:59:59'),
(4, 'SHARE456789', true, '2026-04-30 23:59:59'),
(5, 'SHARE567890', true, '2026-04-30 23:59:59'),
(6, 'SHARE678901', true, '2026-04-30 23:59:59'),
(7, 'SHARE789012', true, '2026-04-30 23:59:59'),
(8, 'SHARE890123', true, '2026-04-30 23:59:59'),
(9, 'SHARE901234', true, '2026-04-30 23:59:59'),
(10, 'SHARE012345', true, '2026-04-30 23:59:59');

-- 为每个位置共享添加 2 个位置更新
INSERT INTO mt_location_updates (share_id, latitude, longitude, speed, heading) VALUES
(1, 39.9042, 116.4074, 30.0, 90.0),
(1, 39.9142, 116.4174, 25.0, 95.0),
(2, 31.2304, 121.4737, 35.0, 180.0),
(2, 31.2404, 121.4837, 30.0, 185.0),
(3, 23.1291, 113.2644, 40.0, 45.0),
(3, 23.1391, 113.2744, 35.0, 50.0),
(4, 30.5728, 104.0668, 25.0, 135.0),
(4, 30.5828, 104.0768, 20.0, 140.0),
(5, 34.3416, 108.9398, 30.0, 225.0),
(5, 34.3516, 108.9498, 25.0, 230.0),
(6, 29.5630, 106.5516, 35.0, 315.0),
(6, 29.5730, 106.5616, 30.0, 320.0),
(7, 30.2741, 120.1551, 20.0, 45.0),
(7, 30.2841, 120.1651, 15.0, 50.0),
(8, 32.0603, 118.7969, 25.0, 135.0),
(8, 32.0703, 118.8069, 20.0, 140.0),
(9, 22.5431, 114.0579, 30.0, 225.0),
(9, 22.5531, 114.0679, 25.0, 230.0),
(10, 30.5928, 114.3055, 35.0, 315.0),
(10, 30.6028, 114.3155, 30.0, 320.0);

-- =============================================
-- 6. 车队数据
-- =============================================

-- 插入 10 个车队
INSERT INTO mt_teams (name, description, destination, start_time, end_time, max_members, status, cover_image, latitude, longitude, creator_id) VALUES
('北京骑行队', '北京周边骑行活动', '怀柔区', '2026-04-15 09:00:00', '2026-04-15 18:00:00', 10, 'open', 'https://images.unsplash.com/photo-1558980394-a4155224a1ad', 39.9042, 116.4074, 1),
('上海骑行队', '环太湖骑行活动', '苏州市', '2026-04-20 08:00:00', '2026-04-22 18:00:00', 8, 'open', 'https://images.unsplash.com/photo-1551632436-cbf8dd35adfa', 31.2304, 121.4737, 2),
('广州骑行队', '沿海骑行活动', '珠海市', '2026-04-10 09:00:00', '2026-04-10 18:00:00', 12, 'open', 'https://images.unsplash.com/photo-1506929562872-bb421503ef21', 23.1291, 113.2644, 3),
('成都骑行队', '周边山路骑行', '都江堰市', '2026-04-16 09:30:00', '2026-04-16 17:00:00', 6, 'open', 'https://images.unsplash.com/photo-1541339907198-e08756dedf3f', 30.5728, 104.0668, 4),
('西安骑行队', '骊山环山骑行', '骊山', '2026-04-14 10:00:00', '2026-04-14 16:00:00', 8, 'open', 'https://images.unsplash.com/photo-1543349689-9a4d426bee8e', 34.3416, 108.9398, 5),
('重庆骑行队', '武隆长途骑行', '武隆区', '2026-04-25 07:00:00', '2026-04-27 18:00:00', 10, 'open', 'https://images.unsplash.com/photo-1556911220-bff31c812dba', 29.5630, 106.5516, 6),
('杭州骑行队', '千岛湖骑行', '千岛湖', '2026-04-18 08:30:00', '2026-04-19 18:00:00', 8, 'open', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6', 30.2741, 120.1551, 7),
('南京骑行队', '黄山长途骑行', '黄山市', '2026-05-01 06:00:00', '2026-05-05 18:00:00', 12, 'open', 'https://images.unsplash.com/photo-1470004914212-05527e49370b', 32.0603, 118.7969, 8),
('深圳骑行队', '沿海骑行活动', '惠州市', '2026-04-17 09:00:00', '2026-04-18 18:00:00', 10, 'open', 'https://images.unsplash.com/photo-1534802177853-8a286d973a07', 22.5431, 114.0579, 9),
('武汉骑行队', '神农架长途摩旅', '神农架', '2026-05-10 05:00:00', '2026-05-16 18:00:00', 8, 'open', 'https://images.unsplash.com/photo-1562408590-e329310d97fc', 30.5928, 114.3055, 10);

-- 为每个车队添加 5 个成员
-- 车队 1 的成员
INSERT INTO mt_team_members (team_id, user_id, role, status) VALUES
(1, 1, 'leader', 'accepted'),
(1, 2, 'member', 'accepted'),
(1, 3, 'member', 'accepted'),
(1, 4, 'member', 'pending'),
(1, 5, 'member', 'pending');

-- 车队 2 的成员
INSERT INTO mt_team_members (team_id, user_id, role, status) VALUES
(2, 2, 'leader', 'accepted'),
(2, 1, 'member', 'accepted'),
(2, 3, 'member', 'accepted'),
(2, 4, 'member', 'pending'),
(2, 5, 'member', 'pending');

-- 车队 3 的成员
INSERT INTO mt_team_members (team_id, user_id, role, status) VALUES
(3, 3, 'leader', 'accepted'),
(3, 1, 'member', 'accepted'),
(3, 2, 'member', 'accepted'),
(3, 4, 'member', 'pending'),
(3, 5, 'member', 'pending');

-- 车队 4 的成员
INSERT INTO mt_team_members (team_id, user_id, role, status) VALUES
(4, 4, 'leader', 'accepted'),
(4, 1, 'member', 'accepted'),
(4, 2, 'member', 'accepted'),
(4, 3, 'member', 'pending'),
(4, 5, 'member', 'pending');

-- 车队 5 的成员
INSERT INTO mt_team_members (team_id, user_id, role, status) VALUES
(5, 5, 'leader', 'accepted'),
(5, 1, 'member', 'accepted'),
(5, 2, 'member', 'accepted'),
(5, 3, 'member', 'pending'),
(5, 4, 'member', 'pending');

-- 车队 6-10 的成员（简化处理）
INSERT INTO mt_team_members (team_id, user_id, role, status) VALUES
(6, 6, 'leader', 'accepted'),
(6, 7, 'member', 'accepted'),
(6, 8, 'member', 'accepted'),
(6, 9, 'member', 'pending'),
(6, 10, 'member', 'pending'),
(7, 7, 'leader', 'accepted'),
(7, 6, 'member', 'accepted'),
(7, 8, 'member', 'accepted'),
(7, 9, 'member', 'pending'),
(7, 10, 'member', 'pending'),
(8, 8, 'leader', 'accepted'),
(8, 6, 'member', 'accepted'),
(8, 7, 'member', 'accepted'),
(8, 9, 'member', 'pending'),
(8, 10, 'member', 'pending'),
(9, 9, 'leader', 'accepted'),
(9, 6, 'member', 'accepted'),
(9, 7, 'member', 'accepted'),
(9, 8, 'member', 'pending'),
(9, 10, 'member', 'pending'),
(10, 10, 'leader', 'accepted'),
(10, 6, 'member', 'accepted'),
(10, 7, 'member', 'accepted'),
(10, 8, 'member', 'pending'),
(10, 9, 'member', 'pending');

-- =============================================
-- 测试数据插入完成
-- =============================================

-- 提交事务
COMMIT;