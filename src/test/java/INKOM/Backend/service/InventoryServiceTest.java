package INKOM.Backend.service;

import INKOM.Backend.domain.EventInventory;
import INKOM.Backend.domain.EventInventoryStage;
import INKOM.Backend.domain.EventInventoryStatus;
import INKOM.Backend.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @InjectMocks InventoryService inventoryService;
    @Mock InventoryRepository inventoryRepository;

    @Test
    void findOverView() {
        Optional<EventInventory> eventInventory = Optional.empty();
        String eventId = "-1";
        String studentPartyId = "-1";
        int stage = 1;
        when(inventoryRepository.findByEventIdAndStudentPartyIdAndStage(eventId, studentPartyId, stage)).thenReturn(eventInventory);
        Optional<EventInventory> overView = inventoryService.findOverView(eventId, studentPartyId, stage);
        assertEquals(overView, Optional.empty());
    }

    @Test
    void findEventForStudentPartyBefore() {

        Optional<EventInventory> eventInventory = Optional.empty();
        String studentID = "-1";

        when(inventoryRepository
                .findByStudentPartyIdAndStatusLessThanAndStage(studentID,
                        EventInventoryStatus.APPROVED.value(), EventInventoryStage.BEFORE.value())).thenReturn(eventInventory);
        Optional<EventInventory> overView = inventoryService.findEventForStudentPartyBefore(studentID);

        assertEquals(overView, Optional.empty());
    }

    @Test
    void findEventForStudentPartyAfter() {

        Optional<EventInventory> eventInventory = Optional.empty();
        String studentID = "-1";

        when(inventoryRepository
                .findByStudentPartyIdAndStatusLessThanAndStage(studentID,
                        EventInventoryStatus.APPROVED.value(), EventInventoryStage.AFTER.value())).thenReturn(eventInventory);
        Optional<EventInventory> overView = inventoryService.findEventForStudentPartyAfter(studentID);

        assertEquals(overView, Optional.empty());
    }

    @Test
    void findEventForStudentPartyCoin() {

        Optional<EventInventory> eventInventory = Optional.empty();
        String studentID = "-1";

        when(inventoryRepository
                .findByStudentPartyIdAndStatusLessThanAndStage(studentID,
                        EventInventoryStatus.APPROVED.value(), EventInventoryStage.COIN.value())).thenReturn(eventInventory);
        Optional<EventInventory> overView = inventoryService.findEventForStudentPartyCoin(studentID);

        assertEquals(overView, Optional.empty());
    }

    @Test
    void findEvent() {
        EventInventory eventInventory = null;
        long id = -1L;

        when(inventoryRepository.getById(-1l)).thenReturn(eventInventory);
        EventInventory overView = inventoryService.findEvent(id);

        assertEquals(overView, null);
    }
}