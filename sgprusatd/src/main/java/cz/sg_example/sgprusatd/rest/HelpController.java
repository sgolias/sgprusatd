package cz.sg_example.sgprusatd.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping({"/sgprusatd/help", "/sgprusatd/info"})
public class HelpController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE) // nebo TEXT_PLAIN_VALUE
    public ResponseEntity<String> getHelpContent() {
        try {
            ClassPathResource resource = new ClassPathResource("static/help/sgprusatd_help.html");
            String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Chyba při načítání help souboru: " + e.getMessage());
        }
    }
}
