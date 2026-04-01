package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiaryMood implements BaseEnum {
    HAPPY("happy", "开心"),
    EXCITED("excited", "兴奋"),
    CALM("calm", "平静"),
    TIRED("tired", "疲惫"),
    NEUTRAL("neutral", "一般");

    private final String code;
    private final String desc;
}
