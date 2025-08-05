/*
 * 
 */
package cz.sg_example.sgprusatd.entity.tabs;

import java.time.LocalDateTime;

import cz.sg_example.sgprusatd.enums.StatusTasks;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tŕída Sgtodo simulující definici tabulky pro tasky.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Sgtodo extends TabAudit {

    private Long id_todo;
    @XmlElement
    private String code_proj;
    @XmlElement
    private String description;
    @XmlElement
    private StatusTasks status;
    private LocalDateTime completed_at;

    /**
     * Vrací key.
     * 
     * key = code_proj + "-" + id
     *
     * @return key
     */
    public String getKey() {
        return getCode_proj() + "-" + getId_todo();
    }

    /**
     * Nastavuej status úkolu
     * 
     * Pokud je úkol ve stavu COMPLETED a datum completed_at je null, pak nastaví
     * datum. Pokud je úkol v jiném stavu než COMPLETED, datum nastaví na null.
     *
     * @param status nový status
     */
    public void setStatus(StatusTasks status) {
        this.status = status;
        LocalDateTime now = null;
        if (StatusTasks.COMPLETED.equals(status)) {
            now = getCompleted_at() == null ? LocalDateTime.now() : getCompleted_at();
        }
        setCompleted_at(now);
    }
}
