package cz.sg_example.sgprusatd.entity.tabs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Tŕída Sgproj simulující definici tabulky pro projekty.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sgproj extends TabAudit {

    private Long id_proj;
    @NonNull
    private String code_proj;

    /**
     * Vrací key.
     * 
     * key = code_proj
     *
     * @return key
     */
    public String getKey() {
        return getCode_proj();
    }

}
