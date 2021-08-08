package INKOM.Backend.domain;

public enum EventInventoryStatus {

    SUBMITTED(1),
    REJECTED(2),
    APPROVED(3);

    int status;

    EventInventoryStatus(int status) {
        this.status = status;
    }

    public int value(){
        return status;
    }
}


