package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TripStatus implements BaseEnum {
    PLANNING("planning", "计划中"),
    ONGOING("ongoing", "进行中"),
    COMPLETED("completed", "已完成");

    private final String code;
    private final String desc;
}
