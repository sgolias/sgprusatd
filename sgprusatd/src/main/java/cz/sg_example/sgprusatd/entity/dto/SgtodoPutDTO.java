package cz.sg_example.sgprusatd.entity.dto;

import cz.sg_example.sgprusatd.enums.StatusTasks;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SgtodoPutDTO {
    private String description;
    private StatusTasks status;
}
