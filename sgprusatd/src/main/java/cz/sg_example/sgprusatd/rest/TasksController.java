package cz.sg_example.sgprusatd.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.sg_example.sgprusatd.entity.dto.SgprusatdWrapper;
import cz.sg_example.sgprusatd.entity.dto.SgtodoPostDTO;
import cz.sg_example.sgprusatd.entity.dto.SgtodoPutDTO;
import cz.sg_example.sgprusatd.entity.tabs.Sgtodo;
import cz.sg_example.sgprusatd.service.TasksService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@RestController
@RequestMapping("/sgprusatd/tasks")
public class TasksController {
    private static final Logger logger = LoggerFactory.getLogger(TasksController.class);
    
    private final TasksService service;

    public TasksController(TasksService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sgtodo> getTasksByStatus(@RequestParam(required = false) String status, @RequestParam(required = false) String code_proj) {
        if (status == null && code_proj == null) {
            logger.info("GET /sgprusatd/tasks - Dotaz na všechny tasky");
            return service.getAllTasks(); // bez parametru vrací vše
        } else {
            List<String> params = new ArrayList<>();
            if (status != null) params.add("status=" + status);
            if (code_proj != null)   params.add("proj=" + code_proj);

            String msg = "GET /sgprusatd/tasks?" + String.join("&", params);
            logger.info(msg);
            return service.getTasksByStatusAndProj(status, code_proj);
        }
    }
    

    @GetMapping("/{key}")
    public Sgtodo getTasksByKey(@PathVariable String key) {
    	logger.info("GET /sgprusatd/tasks/" + key);
        return service.getTasksByKey(key);
    }
    
    @PostMapping
    public ResponseEntity<Sgtodo> createTask(@RequestBody SgtodoPostDTO dto) {
        logger.info("POST /sgprusatd/tasks - Vytvářím nový task: " + dto);

        Sgtodo created = service.createTask(dto);
        if (created != null) {
            logger.info("Vytvořený nový task: " + created.getKey() + " " + created);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else
            return ResponseEntity.badRequest().build();

    }
    
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> importTasks(@RequestBody String xmlTasks) {
        try {
            // Validace XML proti XSD
            validateXmlAgainstXsd(xmlTasks, "/xsd/sgprusatd.xsd");

            // Unmarshal XML na objekt
            JAXBContext context = JAXBContext.newInstance(SgprusatdWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xmlTasks);
            SgprusatdWrapper wrapper = (SgprusatdWrapper) unmarshaller.unmarshal(reader);

            if (wrapper == null || wrapper.getTasks() == null || wrapper.getTasks().isEmpty()) {
                return ResponseEntity.badRequest().body("XML neobsahuje žádné úkoly.");
            }

            // Import
            for (SgtodoPostDTO dto : wrapper.getTasks()) {
                Sgtodo created = service.createTask(dto);
                logger.info("Importuji task: " + created.getCode_proj());
            }

            return ResponseEntity.ok("XML je validní a úkoly byly importovány.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Neplatné XML: " + e.getMessage());
        }
    }
    
    private void validateXmlAgainstXsd(String xml, String xsdPath) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(getClass().getResource(xsdPath));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new StringReader(xml)));
    }
    

    // PUT /sgprusatd/tasks/{key} - aktualizace položky
    @PutMapping("/{key}")
    public ResponseEntity<Object> updateTask(@PathVariable String key, @RequestBody SgtodoPutDTO dto) {
        return service.updateTask(key, dto);
    }

    // DELETE /sgprusatd/tasks/{key} - odstranění položky
    @DeleteMapping("/{key}")
    public ResponseEntity<Object> deleteTask(@PathVariable String key) {
        logger.info("DELETE /sgprusatd/tasks/" + key);
        return service.remove(key);
    }

}
