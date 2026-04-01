package com.mototrip.trip.dto.request;
import lombok.Data;

@Data
public class UpdatePreparationRequest {
    private String name;
    private String category;
    private String description;
    private Boolean isEssential;
    private Boolean isPacked;
    private Integer quantity;
}
