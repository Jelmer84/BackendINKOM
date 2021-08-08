package INKOM.Backend.domain;

public enum EventInventoryStage {

    BEFORE(0),
    AFTER(1),
    COIN(2);

    int stage;

    EventInventoryStage(int stage) {
        this.stage = stage;
    }

    public int value(){
        return stage;
    }
}
