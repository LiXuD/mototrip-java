package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DangerSeverity implements BaseEnum {
    LIGHT("light", "轻微"),
    MEDIUM("medium", "中等"),
    SEVERE("severe", "严重");

    private final String code;
    private final String desc;
}
