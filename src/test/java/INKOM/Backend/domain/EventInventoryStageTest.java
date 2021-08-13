package INKOM.Backend.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventInventoryStageTest {

    @Test
    void valueOf() {
        assertTrue(EventInventoryStage.valueOf("COIN") == EventInventoryStage.COIN);
    }
}

