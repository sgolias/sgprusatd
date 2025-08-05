package cz.sg_example.sgprusatd.sequences;

import org.springframework.stereotype.Component;

/**
 * Vytvoření sekvence.
 */

@Component("tabID") // pojmenujeme bean jako "tabID"
public class Sequence {

	private Long nextval = 0L;

	/**
	 * Gets the next value.
	 *
	 * @return the next value
	 */
	public synchronized Long getNextVal() {
		nextval++;
		return nextval;
	}
}