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

import cz.sg_example.sgprusatd.entity.tabs.Sgproj;
import cz.sg_example.sgprusatd.enums.MSG;
import cz.sg_example.sgprusatd.repository.ProjectsStorage;

@Service
public class ProjectsService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectsService.class);

    private final ProjectsStorage projects;

    public ProjectsService(ProjectsStorage projects) {
        this.projects = projects;
        
        // simulovaná data
        //projects.put("TD", new Sgproj((Long) (-1L), "TD"));
        //projects.put("DT", new Sgproj((Long) (-2L), "DT"));
    }

    /**
     * Generuje všechny projekty.
     *
     * @return všechny projekty
     */
    public List<Sgproj> getAllProjects() {
        return new ArrayList<>(projects.values());
    }

    /**
     * Vráti projekt podle key.
     *
     * @param key key (TD-xxxx)
     * @return úkol podle key
     */
    public Sgproj getProjectsByKey(String key) {
        Sgproj project = projects.get(key);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msgNotFound(key));
        }
        return project;
    }

    public void put(String key, Sgproj project) {
        projects.put(key, project);
    }

    public ResponseEntity<Object> remove(String key) {
        if (!containsKey(key)) {
            Map<MSG, String> error = Map.of(MSG.ERROR, msgNotFound(key));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        projects.remove(key);
        return ResponseEntity.noContent().build();
    }

    public String msgNotFound(String key) {
        return "Projekt s KEY " + key + " nebyl nalezen";
    }

    public boolean containsKey(String key) {
        return projects.containsKey(key);
    }

}
