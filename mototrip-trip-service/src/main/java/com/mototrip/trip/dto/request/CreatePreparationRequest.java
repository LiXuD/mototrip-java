package com.mototrip.trip.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePreparationRequest {
    @NotBlank(message = "物品名称不能为空")
    private String name;
    private String category;
    private String description;
    private Boolean isEssential;
    private Boolean isPacked;
    private Integer quantity;
}
