package com.mototrip.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeoUtils {

    private static final BigDecimal EARTH_RADIUS = new BigDecimal("6371");

    public static BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        double dLat = Math.toRadians(lat2.doubleValue() - lat1.doubleValue());
        double dLon = Math.toRadians(lon2.doubleValue() - lon1.doubleValue());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1.doubleValue())) * Math.cos(Math.toRadians(lat2.doubleValue()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS.multiply(BigDecimal.valueOf(c)).setScale(2, RoundingMode.HALF_UP);
    }
}
