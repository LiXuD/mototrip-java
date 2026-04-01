package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TeamDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String coverImage;
    private Integer maxMembers;
    private Integer currentMemberCount;
    private String status;
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
