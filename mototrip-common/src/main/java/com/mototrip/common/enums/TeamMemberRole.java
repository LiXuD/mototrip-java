package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TeamMemberRole implements BaseEnum {
    MEMBER("member", "成员"),
    LEADER("leader", "队长");

    private final String code;
    private final String desc;
}
