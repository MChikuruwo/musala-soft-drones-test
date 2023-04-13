package com.musala.musalatest.business.model;

import com.musala.musalatest.business.enums.DispatchStatus;
import com.musala.musalatest.util.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dispatch extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String reference;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Drone drone;

    @Enumerated(EnumType.STRING)
    private DispatchStatus status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Medication> medication;
}
