package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TeamStatus implements BaseEnum {
    OPEN("open", "开放"),
    FULL("full", "已满"),
    COMPLETED("completed", "已完成"),
    CANCELLED("cancelled", "已取消");

    private final String code;
    private final String desc;
}
