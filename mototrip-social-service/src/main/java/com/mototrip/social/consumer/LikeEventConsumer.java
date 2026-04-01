package com.mototrip.social.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "MOTOTRIP_LIKE_EVENT",
        consumerGroup = "mototrip-social-consumer-group"
)
public class LikeEventConsumer implements RocketMQListener<Map<String, Object>> {

    @Override
    public void onMessage(Map<String, Object> message) {
        log.info("收到点赞事件: {}", message);
        try {
            Long userId = Long.valueOf(message.get("userId").toString());
            Long targetId = Long.valueOf(message.get("targetId").toString());
            String targetType = message.get("targetType").toString();
            String action = message.get("action").toString(); // "like" or "unlike"

            // TODO: Implement notification logic
            // - If action is "like", send notification to target owner
            // - Could integrate with WebSocket for real-time notifications
            // - Could store in notification table for push notifications

            log.info("点赞事件处理完成: userId={}, targetId={}, targetType={}, action={}",
                    userId, targetId, targetType, action);
        } catch (Exception e) {
            log.error("点赞事件处理失败: {}", message, e);
        }
    }
}
