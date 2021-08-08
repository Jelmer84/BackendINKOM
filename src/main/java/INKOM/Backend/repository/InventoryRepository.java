package INKOM.Backend.repository;

import INKOM.Backend.domain.EventInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface InventoryRepository extends JpaRepository<EventInventory, Long> {

    Optional<EventInventory> findByEventIdAndStudentPartyIdAndStage(String eventId, String studentId,int stage);
    Optional<EventInventory>  findByEventIdAndStudentPartyId(String eventId, String studentPartyId);
    Optional<EventInventory>  findByStudentPartyIdAndStatusLessThanAndStage( String studentPartyId, int status, int stage);
    //Optional<List<EventInventory>>  findByStudentPartyIdAndStatusLessThan(String studentPartyId, int status);
    Optional<EventInventory> findByStudentPartyId(String studentPartyId);

    Optional<EventInventory> findByStudentPartyIdAndEventIdAndStage( String studentPartyId, String eventId, int stage);
//    Optional<EventInventory> findByStudentPartyIdAndEventIdAndStage(String eventId, String studentPartyId, int stage);


    List<EventInventory> findAllBySubmitted(long id);

    List<EventInventory> findByEventIdAndStudentPartyIdAndStatus(String eventId, String studentId, int status);

    long countByEventIdAndStudentPartyIdAndStatus(String eventId, String studentId, int status);
}
