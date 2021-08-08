package INKOM.Backend.controller;

import INKOM.Backend.domain.EventInventory;
import INKOM.Backend.domain.EventInventoryStage;
import INKOM.Backend.domain.EventInventoryStatus;
import INKOM.Backend.exceptions.BadRequestException;
import INKOM.Backend.payload.response.EventSummary;
import INKOM.Backend.payload.response.MessageResponse;
import INKOM.Backend.service.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @PostMapping("/submit")
    public ResponseEntity<MessageResponse> submit(@RequestBody Map<String, Object> body) throws JsonProcessingException {
        EventInventory eventInventory = new EventInventory();
        eventInventory.setEventId(body.get("eventId").toString());
        eventInventory.setStudentPartyId(body.get("studentPartyId").toString());
        Object organisationRemarks = body.get("organisationRemarks");
        if (organisationRemarks!=null)
            eventInventory.setOrganisationRemarks(organisationRemarks.toString());

        eventInventory.setStage(Integer.parseInt(body.get("stage").toString()));
        String data = new ObjectMapper().writeValueAsString(body.get("data"));
        eventInventory.setData(data);
        eventInventory.setStatus(EventInventoryStatus.SUBMITTED.value());
        eventInventory.setSubmitted(Integer.parseInt(body.get("submitted").toString()));
        try {
            inventoryService.submit(eventInventory);
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body(new MessageResponse("Submitted successfully"));
    }

    @PutMapping
    public ResponseEntity<MessageResponse> updateEventInventory(@RequestBody Map<String, Object> body){
        EventInventory eventInventory = new EventInventory();
        eventInventory.setEventId(body.get("eventId").toString());
        eventInventory.setStudentPartyId(body.get("studentPartyId").toString());
        eventInventory.setData(body.get("data").toString());
        eventInventory.setStatus(EventInventoryStatus.SUBMITTED.value());
        inventoryService.update(eventInventory);

        return ResponseEntity.ok().body(new MessageResponse("Updated"));
    }

    @PutMapping("/approve")
    public ResponseEntity<MessageResponse> approve(@RequestBody Map<String, Object> body ){
        String eventId = body.get("eventId").toString();
        boolean approval = Boolean.parseBoolean(body.get("approve").toString());
        int stage = Integer.parseInt(body.get("stage").toString());
        String studentId = body.get("studentPartyId").toString();
        Object studentPartyRemarks = body.get("studentPartyRemarks");

        try{
            if(approval)
                inventoryService.approve(eventId, studentId, stage, studentPartyRemarks == null ? null : studentPartyRemarks.toString());
            else
                inventoryService.reject(eventId,studentId ,stage);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Bad request"));
        }
        return ResponseEntity.ok().body(new MessageResponse("Status changed"));
    }

//    @GetMapping
//    public Optional<EventInventory> fetchEventInventory(@RequestParam String eventId){
//        return inventoryService.findEvent(eventId);
//    }

    @GetMapping(value = "/{studentId}/{stage}")
    public ResponseEntity<Object> fetchEventInventoryForStudentParty(@PathVariable("studentId") String studentID,
                                                                                       @PathVariable("stage") int stage){

        Optional<EventInventory> event = null;

        if(stage == EventInventoryStage.BEFORE.value()){
            event = inventoryService.findEventForStudentPartyBefore(studentID);
        }else if(stage == EventInventoryStage.AFTER.value()){
            event = inventoryService.findEventForStudentPartyAfter(studentID);
        }else if(stage == EventInventoryStage.COIN.value()){
            event = inventoryService.findEventForStudentPartyCoin(studentID);
        }

        if(!event.isPresent())
            return ResponseEntity
                    .status(HttpStatus.ALREADY_REPORTED)
                    .body(new MessageResponse("you have already reviewed this event"));
        else if(event.get().getStatus() == EventInventoryStatus.REJECTED.value()){
            return ResponseEntity
                    .status(HttpStatus.ALREADY_REPORTED)
                    .body(new MessageResponse("you have already reviewed this event"));
        }

        return ResponseEntity.ok().body(event.get());
    }

    @GetMapping(value = "/supervisor-summary/{supervisorId}")
    public List<EventSummary> supervisorSummary(@PathVariable("supervisorId") long supervisorId){
        return inventoryService.supervisorSummary(supervisorId);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteAllEventData(){
        inventoryService.deleteAllEvents();
        return ResponseEntity.ok(new MessageResponse("EventInventory deleted!"));
    }

}
