package INKOM.Backend.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "eventInventory")
public class EventInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String data;

    @Column
    private String eventId;

    @Column
    private String studentPartyId;

    @Column
    private int status;

    @Column
    private int stage;

    @Column(length = 500)
    private String organisationRemarks;

    @Column(length = 500)
    private String studentPartyRemarks;

    @Column
    private long submitted;

}


