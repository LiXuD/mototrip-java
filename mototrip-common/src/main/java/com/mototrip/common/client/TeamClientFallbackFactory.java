package com.mototrip.common.client;

import com.mototrip.common.client.dto.TeamDTO;
import com.mototrip.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TeamClientFallbackFactory implements FallbackFactory<TeamClient> {

    @Override
    public TeamClient create(Throwable cause) {
        log.error("TeamClient fallback triggered: {}", cause.getMessage());
        return new TeamClient() {
            @Override
            public Result<TeamDTO> getTeamById(Long id) {
                log.warn("Fallback: getTeamById({})", id);
                return Result.error(500, "团队服务暂时不可用");
            }
        };
    }
}
