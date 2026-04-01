package com.mototrip.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DangerType implements BaseEnum {
    LANDSLIDE("landslide", "山体滑坡"),
    FALLING_ROCKS("falling_rocks", "落石"),
    STEEP_SLOPE("steep_slope", "陡坡"),
    SHARP_TURN("sharp_turn", "急弯"),
    WATER_SIDE("water_side", "水边"),
    CLIFF_SIDE("cliff_side", "悬崖边"),
    ICE_SNOW("ice_snow", "冰雪"),
    FLOOD("flood", "洪水"),
    CONSTRUCTION("construction", "施工"),
    OTHER("other", "其他");

    private final String code;
    private final String desc;
}
