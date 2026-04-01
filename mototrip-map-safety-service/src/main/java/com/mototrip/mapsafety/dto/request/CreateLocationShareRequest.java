package com.mototrip.mapsafety.dto.request;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateLocationShareRequest {
    private LocalDateTime expiresAt;
}
