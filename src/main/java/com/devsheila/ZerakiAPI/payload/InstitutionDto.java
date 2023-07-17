package com.devsheila.ZerakiAPI.payload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Set;

import lombok.Data;


@Data
@Schema(description = "InstitutionDto Model Information")
public class InstitutionDto {
    private long id;

    @Schema(description = "Institution Title")
    private String name;

}
