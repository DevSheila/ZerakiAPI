package com.devsheila.ZerakiAPI.payload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Set;

import lombok.Data;


@Data
@Schema(description = "CourseDto Model Information")
public class CourseDto {
    private long id;

    @Schema(description = "CourseDto Post Title")
    private String name;

}


