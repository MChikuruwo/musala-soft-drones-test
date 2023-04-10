package com.musala.musalatest.business.model;

import com.musala.musalatest.business.enums.DroneModel;
import com.musala.musalatest.business.enums.DroneState;
import com.musala.musalatest.util.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Drone extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @Max(value = 500)
    private int weightLimit;

    @Max(value = 100)
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState state;
}
