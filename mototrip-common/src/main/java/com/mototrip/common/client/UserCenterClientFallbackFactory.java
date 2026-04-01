package com.mototrip.common.client;

import com.mototrip.common.client.dto.PublicUserDTO;
import com.mototrip.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserCenterClientFallbackFactory implements FallbackFactory<UserCenterClient> {

    @Override
    public UserCenterClient create(Throwable cause) {
        log.error("UserCenterClient fallback triggered: {}", cause.getMessage());
        return new UserCenterClient() {
            @Override
            public Result<PublicUserDTO> getUserById(Long id) {
                log.warn("Fallback: getUserById({})", id);
                return Result.error(500, "用户服务暂时不可用");
            }

            @Override
            public Result<List<PublicUserDTO>> getUsersByIds(List<Long> ids) {
                log.warn("Fallback: getUsersByIds({})", ids);
                return Result.error(500, "用户服务暂时不可用");
            }
        };
    }
}
