package com.mototrip.mapsafety.service;
import com.mototrip.mapsafety.dto.response.WarningResponse;
import java.util.Map;

public interface WarningService {
    WarningResponse getAggregatedWarnings(double lat, double lng, double radius);
}
