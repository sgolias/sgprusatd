package cz.sg_example.sgprusatd.entity.dto;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tasks {

    @XmlElement(name = "task", type = SgtodoPostDTO.class)
    private List<SgtodoPostDTO> task;

    public List<SgtodoPostDTO> getTask() {
        return task;
    }

    public void setTask(List<SgtodoPostDTO> task) {
        this.task = task;
    }
}
