package cz.sg_example.sgprusatd.enums;

/**
 * Obsahuje statusy zpráv.
 */
public enum MSG {
    INFO("Info"),
    WARNING("Warning"),
    ERROR("Error")
    ;

    public final String s;

    MSG(String s) {
        this.s = s;
    }

    public String getMSG() {
        return s;
    }
    
    @Override
    public String toString() {
        return s;
    }
}