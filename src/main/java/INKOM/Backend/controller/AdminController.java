package INKOM.Backend.controller;

import INKOM.Backend.domain.EventInventory;
import INKOM.Backend.exceptions.BadRequestException;
import INKOM.Backend.payload.response.MessageResponse;
import INKOM.Backend.service.InventoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/inventory")
public class AdminController {

    private final InventoryService inventoryService;

    @Autowired
    public AdminController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping(value = "/totals/{eventId}/{studentPartyId}")
    public ResponseEntity<Object> supervisorSummary(@PathVariable("eventId") String eventId,
                                                    @PathVariable("studentPartyId") String studentPartyId) {
        Map<String, Object> totals = null;
        try {
            totals = inventoryService.adminTotal(eventId, studentPartyId);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Data (nog) niet beschikbaar voor het maken van een totalen tabel."));
        }
        return ResponseEntity.ok(totals);
    }

    @GetMapping(value = "/overview/{eventId}/{studentPartyId}/{stage}")
    @ApiOperation(value = "Indiviual count", notes = "Induvidual coun, can be from every stage ")
    public ResponseEntity<Object> overview(@PathVariable("eventId") String eventId,
                                           @PathVariable("studentPartyId") String studentPartyId,
                                           @PathVariable("stage") int stage) {
        Optional<EventInventory> event = inventoryService.findOverView(eventId, studentPartyId, stage);

        return event.<ResponseEntity<Object>>map(eventInventory -> ResponseEntity.ok().body(eventInventory))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse("Data (nog) niet beschikbaar.")));
    }
}
