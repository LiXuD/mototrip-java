package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PreparationCategory implements BaseEnum {
    TOOL("tool", "工具"),
    SAFETY("safety", "安全"),
    CLOTHING("clothing", "衣物"),
    ELECTRONICS("electronics", "电子产品"),
    DOCUMENTS("documents", "证件"),
    OTHER("other", "其他");

    private final String code;
    private final String desc;
}
