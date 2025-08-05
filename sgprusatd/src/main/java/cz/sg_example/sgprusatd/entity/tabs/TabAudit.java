package cz.sg_example.sgprusatd.entity.tabs;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Tŕída TabAudit doplňující do tabulek auditní data.
 */
@Data
public abstract class TabAudit {

    protected String ts_who;
    protected LocalDateTime ts_when;
    protected String ts_who_in;
    protected LocalDateTime ts_when_in;

    private final String defaultUser;

    public TabAudit() {
        this.defaultUser = "me";
    }

    protected String resolveUser(String user) {
        return (user != null && !user.isEmpty()) ? user : defaultUser;
    }

    public void auditInsert(String user) {
        String who = resolveUser(user);
        LocalDateTime now = LocalDateTime.now();

        this.ts_who_in = who;
        this.ts_when_in = now;
        this.ts_who = who;
        this.ts_when = now;
    }

    public void auditUpdate(String user) {
        this.ts_who = resolveUser(user);
        this.ts_when = LocalDateTime.now();
    }
    
    public void audit(boolean update) {
        if (update) auditUpdate(null);
        else auditInsert(null);
    }
}
