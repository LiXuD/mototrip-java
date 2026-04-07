package com.mototrip.trip.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新准备物品请求")
public class UpdatePreparationRequest {
    @Schema(description = "物品名称")
    private String name;
    
    @Schema(description = "物品分类")
    private String category;
    
    @Schema(description = "物品描述")
    private String description;
    
    @Schema(description = "是否必需")
    private Boolean isEssential;
    
    @Schema(description = "是否已打包")
    private Boolean isPacked;
    
    @Schema(description = "数量")
    private Integer quantity;
}
