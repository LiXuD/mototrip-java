package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MotorcycleType implements BaseEnum {
    SCOOTER("scooter", "踏板"),
    STREET("street", "街车"),
    SPORT("sport", "仿赛"),
    ADV("adv", "拉力");

    private final String code;
    private final String desc;
}
