package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserMode implements BaseEnum {
    NEWBIE("newbie", "新手模式"),
    EXPERIENCED("experienced", "有经验模式"),
    PASSENGER("passenger", "乘客模式");

    private final String code;
    private final String desc;
}
