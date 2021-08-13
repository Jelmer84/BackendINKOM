package INKOM.Backend.service;

import INKOM.Backend.domain.EventInventory;
import INKOM.Backend.domain.EventInventoryStage;
import INKOM.Backend.domain.EventInventoryStatus;
import INKOM.Backend.exceptions.BadRequestException;
import INKOM.Backend.payload.response.EventSummary;
import INKOM.Backend.repository.InventoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Optional<EventInventory> findOverView(String eventId, String studentPartyId, int stage) {
        return inventoryRepository.findByEventIdAndStudentPartyIdAndStage(eventId, studentPartyId, stage);
    }

    public Optional<EventInventory> findEventForStudentPartyBefore(String studentID) {
        return inventoryRepository
                .findByStudentPartyIdAndStatusLessThanAndStage(studentID,
                        EventInventoryStatus.APPROVED.value(), EventInventoryStage.BEFORE.value());
    }

    public Optional<EventInventory> findEventForStudentPartyAfter(String studentID) {
        return inventoryRepository
                .findByStudentPartyIdAndStatusLessThanAndStage(studentID,
                        EventInventoryStatus.APPROVED.value(), EventInventoryStage.AFTER.value());
    }

    public Optional<EventInventory> findEventForStudentPartyCoin(String studentID) {
        return inventoryRepository
                .findByStudentPartyIdAndStatusLessThanAndStage(studentID,
                        EventInventoryStatus.APPROVED.value(), EventInventoryStage.COIN.value());
    }

    public EventInventory findEvent(long id) {
        return inventoryRepository.getById(id);
    }

    public void approve(String eventId, String studentId, int stage, String studentPartyRemarks) throws Exception {
        Optional<EventInventory> eventInventory = inventoryRepository.findByEventIdAndStudentPartyIdAndStage(eventId, studentId, stage);
        EventInventory inventory = eventInventory.orElseThrow();
        inventory.setStatus(EventInventoryStatus.APPROVED.value());
        inventory.setStudentPartyRemarks(studentPartyRemarks);
        inventoryRepository.save(inventory);
    }

    public void reject(String eventId, String studentId, int stage) throws Exception {
        Optional<EventInventory> eventInventory = inventoryRepository.findByEventIdAndStudentPartyIdAndStage(eventId, studentId, stage);
        EventInventory inventory = eventInventory.orElseThrow();
        inventory.setStatus(EventInventoryStatus.REJECTED.value());
        inventoryRepository.save(inventory);
    }

    public void submit(EventInventory e) throws IllegalArgumentException, BadRequestException {
        // check if count has already been submitted and has not been review by SP
        final Optional<EventInventory> storedEvent = inventoryRepository
                .findByStudentPartyIdAndEventIdAndStage(
                        e.getStudentPartyId(), e.getEventId(), e.getStage());
        long id = -1;
        if (storedEvent.isPresent()) {
            EventInventory eventInventory = storedEvent.get();
            if (eventInventory.getStatus() == EventInventoryStatus.SUBMITTED.value())
                throw new BadRequestException("Evenement is al opgeslagen en wacht op de goedkeuring van de studentenpartij");
            else if (eventInventory.getStatus() == EventInventoryStatus.APPROVED.value())
                throw new BadRequestException(" Evenement is al geteld en kan niet worden geupdated");
            id = eventInventory.getId();
        }

        // save if it has not been submitted before or is updating after been reviewed
        if (id > -1)
            e.setId(storedEvent.get().getId());
        inventoryRepository.save(e);
    }

    public void update(EventInventory eventInventory) {
        inventoryRepository.save(eventInventory);
    }

    private String getStatus(int status) {
        return EventInventoryStatus.values()[status - 1].toString();
    }

    private String getStage(int stage) {
        return EventInventoryStage.values()[stage].toString();
    }

    public List<EventSummary> supervisorSummary(long supervisorId) {
        List<EventInventory> events = inventoryRepository.findAllBySubmitted(supervisorId);
        List<EventSummary> eventSummary = events
                .stream()
                .map((e) -> new EventSummary(e.getId(), e.getEventId(), e.getStudentPartyId(), getStatus(e.getStatus()),
                        getStage(e.getStage()), e.getOrganisationRemarks(), e.getStudentPartyRemarks()))
                .collect(Collectors.toList());
        System.out.println(eventSummary);
        return eventSummary;
    }

    public Map<String, Object> adminTotal(String eventId, String studentPartyId) throws BadRequestException {
        int status = EventInventoryStatus.APPROVED.value();
        if (inventoryRepository.
                countByEventIdAndStudentPartyIdAndStatus(eventId,
                        studentPartyId,
                        status) != 3) {
            throw new BadRequestException("Data (nog) niet beschikbaar.");
        }

        List<EventInventory> events = inventoryRepository
                .findByEventIdAndStudentPartyIdAndStatus(eventId, studentPartyId, status);

        Map<String, Object> total = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        Map data = null;
        Map coin = null;
        String weekday = null;
        for (EventInventory event : events) {
            try {
                data = mapper.readValue(event.getData(), Map.class);
                if (weekday == null)
                    weekday = ((Map) data.get("selectedWeekday")).get("weekday").toString();
                data.remove("crates");
                data.remove("bottles");
                data.remove("kegs");
                data.remove("tanks");
                data.remove("selectedWeekday");

                // check if it contain the keys
                if (data.containsKey("totalBottles")) {
                    if (!(data.get("totalBottles") instanceof Map)) {
                        data.put("totalBottles", createCratesOrBottleDefaultData());
                    }
                }

                if (data.containsKey("totalCrates")) {
                    if (!(data.get("totalCrates") instanceof Map)) {
                        data.put("totalCrates", createCratesOrBottleDefaultData());
                    }
                }

                if (data.containsKey("totalKegs")) {
                    if (!(data.get("totalKegs") instanceof Map)) {
                        data.put("totalKegs", createKegsDefaultData());
                    }
                }

                if (data.containsKey("totalTanks")) {
                    if (!(data.get("totalTanks") instanceof Map)) {
                        data.put("totalTanks", createTankDefaultData());
                    }
                }

                // check if the keys are number(0) or object
                // change the content of the keys to default value
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (event.getStage() == EventInventoryStage.BEFORE.value()) {
                total.put("before", data);
            }
            if (event.getStage() == EventInventoryStage.AFTER.value()) {
                total.put("after", data);
                total.put("organisationRemarks", event.getOrganisationRemarks());
                total.put("studentPartyRemarks", event.getStudentPartyRemarks());
            }
            if (event.getStage() == EventInventoryStage.COIN.value()) {
                total.put("coin", data);
                coin = data;
            }
        }

        Map<String, Integer> coins = new HashMap<>();
        coins.put("Coins", Integer.valueOf(coin.get("coins").toString()));
        ((Map) total.get("after")).put("totalCoins", coins);
        total.put("event", eventId);
        total.put("studentParty", studentPartyId);
        total.put("weekday", weekday);
        return total;
    }

    //Default data zo that calculations in the front-end will work.
    private Map<String, Integer> createCratesOrBottleDefaultData() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Water_Rood", 0);
        map.put("Water_Blauw", 0);
        map.put("Pepsi", 0);
        map.put("Pepsi_Max", 0);
        map.put("Sisi", 0);
        map.put("Ice_Tea_Normal", 0);
        map.put("Ice_Tea_Green_110cl", 0);
        map.put("Ice_Tea_Green_150cl", 0);
        map.put("Red_Bull", 0);
        map.put("Red_Bull_Sugar_Free", 0);
        map.put("Red_Bull_Tropical", 0);
        map.put("Desperados", 0);
        return map;
    }

    private Map<String, Double> createTankDefaultData() {
        Map<String, Double> map = new HashMap<>();
        map.put("Tankbier", 0D);
        return map;
    }

    private Map<String, Double> createKegsDefaultData() {
        Map<String, Double> map = new HashMap<>();
        map.put("Fust_Jilz", 0D);
        map.put("Fust_Radler", 0D);
        map.put("Fust_Pils", 0D);
        return map;
    }

    public void deleteAllEvents() {
        inventoryRepository.deleteAll();
    }
}
