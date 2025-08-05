package cz.sg_example.sgprusatd.entity.dto;

import cz.sg_example.sgprusatd.enums.StatusTasks;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class SgtodoPostDTO {
    private String code_proj;

    @NonNull
    private String description;

    private StatusTasks status;
}
