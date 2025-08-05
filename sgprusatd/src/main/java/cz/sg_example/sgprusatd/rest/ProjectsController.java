package cz.sg_example.sgprusatd.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.sg_example.sgprusatd.entity.tabs.Sgproj;
import cz.sg_example.sgprusatd.service.ProjectsService;

@RestController
@RequestMapping("/sgprusatd/projects")
public class ProjectsController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
    
    private final ProjectsService service;

    public ProjectsController(ProjectsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sgproj> getAllProjects() {
    	logger.info("GET /sgprusatd/projects/ - Dotaz na všechny projekty");
        return service.getAllProjects();
    }

    @GetMapping("/{key}")
    public Sgproj getTasksById(@PathVariable String key) {
    	logger.info("GET /sgprusatd/projects/" + key);
        return service.getProjectsByKey(key);
    }
    

}
