package com.mototrip.mapsafety.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "创建位置共享请求")
public class CreateLocationShareRequest {
    @Schema(description = "过期时间")
    private LocalDateTime expiresAt;
}
