package com.mototrip.team.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "MOTOTRIP_TEAM_NOTIFICATION",
        consumerGroup = "mototrip-team-consumer-group"
)
public class TeamNotificationConsumer implements RocketMQListener<Map<String, Object>> {

    @Override
    public void onMessage(Map<String, Object> message) {
        log.info("收到车队通知: {}", message);
        try {
            Long teamId = Long.valueOf(message.get("teamId").toString());
            Long userId = Long.valueOf(message.get("userId").toString());
            String type = message.get("type").toString();
            // type: join_request, approved, rejected, member_left, team_disbanded

            // TODO: Implement notification logic
            // - Send notification to team creator for join requests
            // - Send notification to member when approved/rejected
            // - Notify all members when someone leaves or team is disbanded

            log.info("车队通知处理完成: teamId={}, userId={}, type={}", teamId, userId, type);
        } catch (Exception e) {
            log.error("车队通知处理失败: {}", message, e);
        }
    }
}
