package com.mototrip.common.client.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class WarningDTO implements Serializable {
    private Map<String, Object> nightWarning;
    private List<Map<String, Object>> dangerZones;
    private List<Map<String, Object>> noParkingZones;
}
