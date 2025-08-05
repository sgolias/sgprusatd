package cz.sg_example.sgprusatd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cz.sg_example.sgprusatd.entity.dto.SgtodoPostDTO;
import cz.sg_example.sgprusatd.entity.dto.SgtodoPutDTO;
import cz.sg_example.sgprusatd.entity.tabs.Sgproj;
import cz.sg_example.sgprusatd.entity.tabs.Sgtodo;
import cz.sg_example.sgprusatd.enums.MSG;
import cz.sg_example.sgprusatd.enums.StatusTasks;
import cz.sg_example.sgprusatd.repository.ProjectsStorage;
import cz.sg_example.sgprusatd.repository.TasksStorage;
import cz.sg_example.sgprusatd.sequences.Sequence;
import lombok.NonNull;

@Service
public class TasksService {
    private static final Logger logger = LoggerFactory.getLogger(TasksService.class);

    private final ProjectsStorage projects;
    private final TasksStorage tasks;
    private final Sequence tabID;

    public TasksService(ProjectsStorage projects, TasksStorage tasks, Sequence tabID) {
        this.projects = projects;
        this.tasks = tasks;
        this.tabID = tabID;
        
        // simulovaná data
        //tasks.put("TD--1", new Sgtodo((Long) (-1L), "TD", "Začátek projekdu", StatusTasks.DRAFT, null));
        //tasks.put("TD--2", new Sgtodo((Long) (-2L), "TD", "1. úkol", StatusTasks.DRAFT, null));

    }

    /**
     * Generuje všechny tásky.
     *
     * @return všechny tásky
     */
    public List<Sgtodo> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Vráti úkol podle key.
     *
     * @param key key (TD-xxxx)
     * @return úkol podle key
     */
    public Sgtodo getTasksByKey(String key) {
        Sgtodo task = tasks.get(key);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msgNotFound(key));
        }
        return task;
    }

    public Sgtodo createTask(SgtodoPostDTO dto) {
        
        @NonNull
        String code_proj = dto.getCode_proj() == null ? "TD" : dto.getCode_proj();
        @NonNull
        StatusTasks status = dto.getStatus() == null ? StatusTasks.DRAFT : dto.getStatus(); 
        
        Sgtodo task = Sgtodo.builder()
                .id_todo(tabID.getNextVal())
                .code_proj(code_proj)
                .description(dto.getDescription())
                .build();
        // nastavení statusu a completed_at
        task.setStatus(status);

        // založení nového projektu
        if (!projects.containsKey(task.getCode_proj())) projects.put(code_proj, new Sgproj(tabID.getNextVal(), code_proj));
        
        // Uložení do mapy podle key = code_proj-id
        String key = task.getKey();
        tasks.put(key, task);
        return task;
    }

    public ResponseEntity<Object> updateTask(String key, SgtodoPutDTO dto) {
        
        if (!containsKey(key)) {
            Map<MSG, String> error = Map.of(MSG.ERROR, msgNotFound(key));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        Sgtodo task = tasks.get(key);

        var description = dto.getDescription() == null ? task.getDescription() : dto.getDescription(); 
        StatusTasks status = dto.getStatus() == null ? task.getStatus() : dto.getStatus(); 
        
        if (description != null) task.setDescription(description);
        if (status != null) task.setStatus(status);

        // Uložení do mapy podle key = code_proj-id
        tasks.put(key, task);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    public void put(String key, Sgtodo task) {
        tasks.put(key, task);
    }

    public ResponseEntity<Object> remove(String key) {
        if (!containsKey(key)) {
            Map<MSG, String> error = Map.of(MSG.ERROR, msgNotFound(key));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        tasks.remove(key);
        return ResponseEntity.noContent().build();
    }

    public String msgNotFound(String key) {
        return "Úkol s KEY " + key + " nebyl nalezen";
    }

    public boolean containsKey(String key) {
        return tasks.containsKey(key);
    }

    public List<Sgtodo> getTasksByStatusAndProj(String statusStr, String proj) {
        try {
            StatusTasks status = null;
            if (statusStr != null) status = StatusTasks.valueOf(statusStr.toUpperCase());
            return tasks.findByStatusAndProj(status, proj);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Neplatný status: " + statusStr);
        }
    }
}

