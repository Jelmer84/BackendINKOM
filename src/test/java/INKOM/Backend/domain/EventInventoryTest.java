package INKOM.Backend.domain;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventInventoryTest {


    @Test
    private void produceCorrectEntity(){

        EventInventory e = new EventInventory();
        e.setSubmitted(1);
        e.setStage(1);
        e.setEventId("Opening");
        e.setStudentPartyId("S.V. KOKO");

        assert e.getSubmitted() == 1;
        assert e.getStage() == 1;


    }
}
