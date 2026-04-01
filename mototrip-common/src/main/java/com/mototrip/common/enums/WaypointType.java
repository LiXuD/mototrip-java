package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WaypointType implements BaseEnum {
    SCENIC("scenic", "景点"),
    RESTAURANT("restaurant", "餐厅"),
    HOTEL("hotel", "酒店"),
    GAS("gas", "加油站"),
    REPAIR("repair", "维修"),
    OTHER("other", "其他");

    private final String code;
    private final String desc;
}
