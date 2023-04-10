package com.musala.musalatest.service;

import com.musala.musalatest.Exceptions.DroneBatteryLowException;
import com.musala.musalatest.Exceptions.WeightOverloadException;
import com.musala.musalatest.business.dao.DispatchRepository;
import com.musala.musalatest.business.dto.DispatchRequest;
import com.musala.musalatest.business.dto.DispatchResponse;
import com.musala.musalatest.business.dto.DroneResponse;
import com.musala.musalatest.business.enums.DroneState;
import com.musala.musalatest.business.model.Dispatch;
import com.musala.musalatest.business.model.Medication;
import com.musala.musalatest.util.OtherUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DispatchService {

    private final DispatchRepository dispatchRepository;

    private final MedicationService medicationService;

    private final DroneService droneService;


    public DispatchResponse loadDroneForDispatch(DispatchRequest dispatchRequest) {

        var medications = new ArrayList<Medication>();

        var drone = droneService.findDroneBySerialNumber(dispatchRequest.serialNumber());

        var medication = medicationService.findMedicationByCode(dispatchRequest.code());

        medications.add(medication);

        var dispatch = Dispatch.builder()
                .drone(drone)
                .medication(medications)
                .reference(OtherUtils.reference())
                .build();

        drone.setState(DroneState.LOADING);
        //set conditions to be satisfied

        if (drone.getBatteryCapacity() <= 25)
            throw new DroneBatteryLowException("Drone battery low to load!");

        //get array of weights here
        var weight = Collections.singletonList(medication.getWeight());

        //get total weight here
        var sumWeight = weight.stream().mapToInt(Integer::intValue).sum();

        //evaluate if sum is greater than limit
        if (sumWeight>drone.getWeightLimit()) throw new WeightOverloadException("Load exceeds drone weight!");

        dispatchRepository.save(dispatch);

        return new DispatchResponse(drone,medications);
    }

}
