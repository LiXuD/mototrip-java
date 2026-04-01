package com.mototrip.trip.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateTripRequest {
    @NotBlank(message = "行程名称不能为空")
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
