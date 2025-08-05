package cz.sg_example.sgprusatd.repository;

import org.springframework.stereotype.Component;

import cz.sg_example.sgprusatd.entity.tabs.Sgtodo;
import cz.sg_example.sgprusatd.enums.StatusTasks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TasksStorage {

    private final Map<String, Sgtodo> sgtodoMap = new ConcurrentHashMap<>();


    public Map<String, Sgtodo> getAll() {
        return sgtodoMap;
    }

    public Sgtodo get(String key) {
        return sgtodoMap.get(key);
    }
    
    public List<Sgtodo> values() {
        return new ArrayList<>(sgtodoMap.values());
    }

    public void put(String key, Sgtodo row) {
        row.audit(sgtodoMap.containsKey((String) key));
        sgtodoMap.put(key, row);
    }

    public void remove(String key) {
        sgtodoMap.remove(key);
    }

    public boolean containsKey(String key) {
        return sgtodoMap.containsKey(key);
    }

    
    /**
     * Vrátí seznam tasků s hledaným statusem a přiřazeným na projekt.
     * 
     * Pokud jsou položky {@code null}, tak se nezohledňují
     *
     * @param byStatus hledaný status
     * @param byProj hledaný kód projektu
     * @return seznam Sgtodo
     */
    public List<Sgtodo> findByStatusAndProj(StatusTasks byStatus, String byProj) {
        List<Sgtodo> result = new ArrayList<>();
        for (Sgtodo task : sgtodoMap.values()) {
            if ((byStatus == null || byStatus.equals(task.getStatus())) 
                    && (byProj == null || byProj.equals(task.getCode_proj()))) {
                result.add(task);
            }
        }
        return result;
    }
}
