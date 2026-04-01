package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class FootprintStatsDTO implements Serializable {
    private long totalFootprints;
    private long provinceCount;
    private long cityCount;
    private double totalDistance;
}
