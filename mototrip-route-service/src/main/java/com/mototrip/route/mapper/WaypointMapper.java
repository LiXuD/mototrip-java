package com.mototrip.route.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mototrip.route.entity.Waypoint;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WaypointMapper extends BaseMapper<Waypoint> {
}
