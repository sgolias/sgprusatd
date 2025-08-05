package cz.sg_example.sgprusatd.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import cz.sg_example.sgprusatd.entity.tabs.Sgproj;

@Component
public class ProjectsStorage {

    private final Map<String, Sgproj> sgprojMap = new ConcurrentHashMap<>();

    public Map<String, Sgproj> getAll() {
        return sgprojMap;
    }

    public Sgproj get(String key) {
        return sgprojMap.get(key);
    }
    
    public List<Sgproj> values() {
        return new ArrayList<>(sgprojMap.values());
    }

    public void put(String key, Sgproj row) {
        row.audit(sgprojMap.containsKey((String) key));
        sgprojMap.put(key, row);
    }

    public void remove(String key) {
        sgprojMap.remove(key);
    }

    public boolean containsKey(String key) {
        return sgprojMap.containsKey(key);
    }
}
