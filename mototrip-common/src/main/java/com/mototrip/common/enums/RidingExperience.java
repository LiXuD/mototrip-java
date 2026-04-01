package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RidingExperience implements BaseEnum {
    NEWBIE("newbie", "新手"),
    EXPERIENCED("experienced", "有经验"),
    EXPERT("expert", "专家");

    private final String code;
    private final String desc;
}
