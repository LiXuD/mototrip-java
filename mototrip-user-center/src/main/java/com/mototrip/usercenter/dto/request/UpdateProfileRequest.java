package com.mototrip.usercenter.dto.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String nickname;
    private String avatar;
    private String bio;
    private String motorcycle;
    private String ridingExperience;
    private String motorcycleType;
}
