package INKOM.Backend.controller;

import INKOM.Backend.domain.EventInventory;
import INKOM.Backend.domain.EventInventoryStage;
import INKOM.Backend.domain.EventInventoryStatus;
import INKOM.Backend.payload.response.EventSummary;
import INKOM.Backend.payload.response.MessageResponse;
import INKOM.Backend.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @Test
    void submit() {
    }

    @Test
    void updateEventInventory() {
    }

    @Test
    void approve() {
    }

    @Test
    void fetchEventInventoryForStudentParty() {
        Optional<EventInventory> event = Optional.empty();
        when(inventoryService.findEventForStudentPartyBefore("-1")).thenReturn(event);
        ResponseEntity<Object> response = inventoryController.fetchEventInventoryForStudentParty("-1", EventInventoryStage.BEFORE.value());
        assertEquals(response.getStatusCode(), HttpStatus.ALREADY_REPORTED);

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(messageResponse.getMessage(), "Dit evenement is al beoordeeld");
    }

    @Test
    void fetchEventInventoryForStudentParty1() {
        Optional<EventInventory> event = Optional.empty();
        when(inventoryService.findEventForStudentPartyAfter("-1")).thenReturn(event);
        ResponseEntity<Object> response = inventoryController.fetchEventInventoryForStudentParty("-1", EventInventoryStage.AFTER.value());
        assertEquals(response.getStatusCode(), HttpStatus.ALREADY_REPORTED);
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(messageResponse.getMessage(), "Dit evenement is al beoordeeld");
    }

    @Test
    void fetchEventInventoryForStudentParty2() {
        Optional<EventInventory> event = Optional.empty();
        when(inventoryService.findEventForStudentPartyCoin("-1")).thenReturn(event);
        ResponseEntity<Object> response = inventoryController.fetchEventInventoryForStudentParty("-1", EventInventoryStage.COIN.value());
        assertEquals(response.getStatusCode(), HttpStatus.ALREADY_REPORTED);

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(messageResponse.getMessage(), "Dit evenement is al beoordeeld");
    }

    @Test
    void fetchEventInventoryForStudentParty4() {
        EventInventory value = new EventInventory();
        value.setStatus(EventInventoryStatus.REJECTED.value());
        Optional<EventInventory> event = Optional.of(value);
        when(inventoryService.findEventForStudentPartyCoin("1")).thenReturn(event);
        ResponseEntity<Object> response = inventoryController.fetchEventInventoryForStudentParty("1", EventInventoryStage.COIN.value());
        assertEquals(response.getStatusCode(), HttpStatus.ALREADY_REPORTED);
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals(messageResponse.getMessage(), "Dit evenement is al beoordeeld");
    }

    @Test
    void fetchEventInventoryForStudentParty3() {
        Optional<EventInventory> event = Optional.of(new EventInventory());
        when(inventoryService.findEventForStudentPartyCoin("1")).thenReturn(event);
        ResponseEntity<Object> response = inventoryController.fetchEventInventoryForStudentParty("1", EventInventoryStage.COIN.value());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        EventInventory eventInventory = (EventInventory) response.getBody();
        assertNotNull(eventInventory);
    }

    @Test
    void supervisorSummary() {
        when(inventoryService.supervisorSummary(-1l)).thenReturn(new ArrayList<>());
        List<EventSummary> eventSummaries = inventoryController.supervisorSummary(-1l);
        assertEquals(eventSummaries.size(), 0);
    }

    @Test
    void deleteAllEventData() {
        ResponseEntity<MessageResponse> response = inventoryController.deleteAllEventData();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getMessage(), "Alle tellingen zijn verwijderd!");
    }
}