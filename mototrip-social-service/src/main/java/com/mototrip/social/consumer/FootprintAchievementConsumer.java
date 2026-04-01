package com.mototrip.social.consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mototrip.social.entity.Footprint;
import com.mototrip.social.entity.FootprintAchievement;
import com.mototrip.social.mapper.FootprintAchievementMapper;
import com.mototrip.social.mapper.FootprintMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "MOTOTRIP_FOOTPRINT_ACHIEVEMENT",
        consumerGroup = "mototrip-social-consumer-group"
)
@RequiredArgsConstructor
public class FootprintAchievementConsumer implements RocketMQListener<Long> {

    private final FootprintMapper footprintMapper;
    private final FootprintAchievementMapper achievementMapper;

    @Override
    public void onMessage(Long userId) {
        log.info("收到足迹成就检查任务, userId={}", userId);
        try {
            List<Footprint> footprints = footprintMapper.selectList(
                    new LambdaQueryWrapper<Footprint>().eq(Footprint::getUserId, userId)
            );

            // Get existing achievements to avoid duplicates
            List<FootprintAchievement> existingAchievements = achievementMapper.selectList(
                    new LambdaQueryWrapper<FootprintAchievement>().eq(FootprintAchievement::getUserId, userId)
            );
            List<String> existingTypes = existingAchievements.stream()
                    .map(FootprintAchievement::getType)
                    .collect(Collectors.toList());

            long totalCount = footprints.size();
            long provinceCount = footprints.stream()
                    .map(Footprint::getProvince)
                    .filter(Objects::nonNull)
                    .distinct()
                    .count();
            long cityCount = footprints.stream()
                    .map(Footprint::getCity)
                    .filter(Objects::nonNull)
                    .distinct()
                    .count();

            // Check and award achievements
            checkAndAward(userId, existingTypes, "first_footprint", "初次足迹", totalCount >= 1);
            checkAndAward(userId, existingTypes, "footprint_10", "足迹达人", totalCount >= 10);
            checkAndAward(userId, existingTypes, "footprint_50", "足迹先锋", totalCount >= 50);
            checkAndAward(userId, existingTypes, "footprint_100", "足迹大师", totalCount >= 100);
            checkAndAward(userId, existingTypes, "province_3", "跨省骑行", provinceCount >= 3);
            checkAndAward(userId, existingTypes, "province_10", "全国巡游", provinceCount >= 10);
            checkAndAward(userId, existingTypes, "city_10", "城市探索者", cityCount >= 10);
            checkAndAward(userId, existingTypes, "city_30", "城市征服者", cityCount >= 30);

            log.info("足迹成就检查完成, userId={}", userId);
        } catch (Exception e) {
            log.error("足迹成就检查失败, userId={}", userId, e);
        }
    }

    private void checkAndAward(Long userId, List<String> existingTypes, String type, String name, boolean condition) {
        if (condition && !existingTypes.contains(type)) {
            FootprintAchievement achievement = new FootprintAchievement();
            achievement.setUserId(userId);
            achievement.setType(type);
            achievement.setName(name);
            achievement.setDescription("解锁成就: " + name);
            achievementMapper.insert(achievement);
            log.info("用户 {} 解锁成就: {} ({})", userId, name, type);
        }
    }
}
