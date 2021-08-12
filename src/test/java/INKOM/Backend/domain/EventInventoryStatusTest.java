package INKOM.Backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventInventoryStatusTest {

    @Test
    void valueOf() {
        assertTrue(EventInventoryStatus.valueOf("SUBMITTED") == EventInventoryStatus.SUBMITTED);
    }
}
