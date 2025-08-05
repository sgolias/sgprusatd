package cz.sg_example.sgprusatd.entity.dto;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sgprusatd")
@XmlAccessorType(XmlAccessType.FIELD)
public class SgprusatdWrapper {

    @XmlElement(name = "tasks")
    private TasksWrapper tasksWrapper;

    public List<SgtodoPostDTO> getTasks() {
        return tasksWrapper != null ? tasksWrapper.getTasks() : null;
    }

    public void setTasks(List<SgtodoPostDTO> tasks) {
        if (this.tasksWrapper == null) {
            this.tasksWrapper = new TasksWrapper();
        }
        this.tasksWrapper.setTasks(tasks);
    }

    // Vnořený wrapper pro <tasks>
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TasksWrapper {
        @XmlElement(name = "task")
        private List<SgtodoPostDTO> tasks;

        public List<SgtodoPostDTO> getTasks() {
            return tasks;
        }

        public void setTasks(List<SgtodoPostDTO> tasks) {
            this.tasks = tasks;
        }
    }
}
