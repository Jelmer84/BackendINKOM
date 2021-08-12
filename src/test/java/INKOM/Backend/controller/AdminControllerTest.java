package INKOM.Backend.controller;

import INKOM.Backend.exceptions.BadRequestException;
import INKOM.Backend.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private AdminController adminController;

    @Test()
    void supervisorSummary1() {
        when(inventoryService.adminTotal("", "")).thenThrow(new BadRequestException("Record not sufficient to compute total"));
        try {
            adminController.supervisorSummary("", "");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Record not sufficient to compute total");
        }
    }

    @Test()
    void supervisorSummary2() {
        when(inventoryService.adminTotal("", "")).thenReturn(new HashMap<>());
        ResponseEntity<Object> response = adminController.supervisorSummary("", "");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void overview() {
    }
}