package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PublicUserDTO implements Serializable {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String bio;
    private String motorcycle;
    private String ridingExperience;
    private String motorcycleType;
    private Integer followersCount;
    private Integer followingCount;
    private LocalDateTime createdAt;
}
