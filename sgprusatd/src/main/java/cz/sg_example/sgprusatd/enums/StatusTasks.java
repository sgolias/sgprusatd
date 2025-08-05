package cz.sg_example.sgprusatd.enums;

/**
 * Obsahuje hodnoty statusů todo úkolů. {@code status_td}.
 */
public enum StatusTasks {
	CANCELLED(-10L),
	DRAFT(0L),
	STARTED(10L),
	IN_PROGRESS(20L),
	PAUSED(50L),
	COMPLETED(99L);
    ;

    public final Long hodnota;

    StatusTasks(Long hodnota) {
        this.hodnota = hodnota;
    }

    public Long getHodnota() {
        return hodnota;
    }
}