package INKOM.Backend.payload.response;

public class EventSummary {

    private Long id;

    private String eventId;

    private String studentPartyId;

    private String status;

    private String stage;

    private String organisationRemarks;

    private String studentPartyRemarks;


    public EventSummary(Long id, String eventId, String studentPartyId, String status, String stage, String
            organisationRemarks, String studentPartyRemarks) {
        this.id = id;
        this.eventId = eventId;
        this.studentPartyId = studentPartyId;
        this.status = status;
        this.stage = stage;
        this.organisationRemarks = organisationRemarks;
        this.studentPartyRemarks = studentPartyRemarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStudentPartyId() {
        return studentPartyId;
    }

    public void setStudentPartyId(String studentPartyId) {
        this.studentPartyId = studentPartyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

}
