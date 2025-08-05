package cz.sg_example.sgprusatd.sequences;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

/**
 * Test Sequence.
 */
class SequenceTest {

    private Sequence sequence;

    /**
     * Sets the up.
     */
    @BeforeEach
    void setUp() {
        sequence = new Sequence(); // Přímo vytvořím instanci (nepoužívám Spring)
    }

    /**
     * Test get next value.
     */
    @Test
    void testGetNextVal() {
        Long val1 = sequence.getNextVal();
        Long val2 = sequence.getNextVal();
        Long val3 = sequence.getNextVal();

        assertEquals(1L, val1);
        assertEquals(2L, val2);
        assertEquals(3L, val3);
    }
}
