package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DangerStatus implements BaseEnum {
    ACTIVE("active", "活跃"),
    RESOLVED("resolved", "已解决"),
    IGNORED("ignored", "已忽略");

    private final String code;
    private final String desc;
}
