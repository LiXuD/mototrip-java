package com.mototrip.trip.dto.request;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateTripRequest {
    private String name;
    private String description;
    private String coverImage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private BigDecimal totalDistance;
    private String notes;
    private Long routeId;
}
