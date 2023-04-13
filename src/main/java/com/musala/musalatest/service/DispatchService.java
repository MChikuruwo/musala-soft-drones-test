package com.musala.musalatest.service;

import com.musala.musalatest.Exceptions.DroneBatteryLowException;
import com.musala.musalatest.Exceptions.WeightOverloadException;
import com.musala.musalatest.business.dao.DispatchRepository;
import com.musala.musalatest.business.dao.DroneRepository;
import com.musala.musalatest.business.dto.DispatchRequest;
import com.musala.musalatest.business.dto.DispatchResponse;
import com.musala.musalatest.business.dto.DroneResponse;
import com.musala.musalatest.business.enums.DispatchStatus;
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

    private final DroneRepository droneRepository;


    public DispatchResponse loadDroneForDispatch(DispatchRequest dispatchRequest) {

        var medications = new ArrayList<Medication>();

        var drone = droneService.findDroneBySerialNumber(dispatchRequest.droneSerialNumber());

        var medication = medicationService.findMedicationByCode(dispatchRequest.medicationCode());

        medications.add(medication);

        var dispatch = Dispatch.builder()
                .drone(drone)
                .medication(medications)
                .reference(OtherUtils.reference())
                .status(DispatchStatus.LOADING)
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

        return new DispatchResponse(drone,medications,dispatch.getReference());
    }

    public String confirmLoadForDispatch(String dispatchReference, String droneSerialNumber){

        var drone = droneService.findDroneBySerialNumber(droneSerialNumber);

        var dispatch = dispatchRepository.findDispatchByReference(dispatchReference);

        if (dispatch.isEmpty()) throw new RuntimeException("Dispatch entity not found");

        dispatch.get().setStatus(DispatchStatus.LOADED);

        drone.setState(DroneState.LOADED);

        droneRepository.save(drone);

        dispatchRepository.save(dispatch.get());

        return "Dispatch Load confirmed";
    }

    public String deployDroneForDispatch(String dispatchReference, String droneSerialNumber){

        var drone = droneService.findDroneBySerialNumber(droneSerialNumber);

        var dispatch = dispatchRepository.findDispatchByReference(dispatchReference);

        if (dispatch.isEmpty()) throw new RuntimeException("Dispatch entity not found");

        dispatch.get().setStatus(DispatchStatus.DELIVERING);

        drone.setState(DroneState.DELIVERING);

        droneRepository.save(drone);

        dispatchRepository.save(dispatch.get());

        return "Drone deployed for dispatch";
    }

    public String confirmDelivery(String dispatchReference, String droneSerialNumber){

        var drone = droneService.findDroneBySerialNumber(droneSerialNumber);

        var dispatch = dispatchRepository.findDispatchByReference(dispatchReference);

        if (dispatch.isEmpty()) throw new RuntimeException("Dispatch entity not found");

        dispatch.get().setStatus(DispatchStatus.DELIVERED);

        drone.setState(DroneState.DELIVERED);

        droneRepository.save(drone);

        dispatchRepository.save(dispatch.get());

        return "Dispatch delivery confirmed";
    }

    public String completeDispatch(String dispatchReference, String droneSerialNumber){

        var drone = droneService.findDroneBySerialNumber(droneSerialNumber);

        var dispatch = dispatchRepository.findDispatchByReference(dispatchReference);

        if (dispatch.isEmpty()) throw new RuntimeException("Dispatch entity not found");

        dispatch.get().setStatus(DispatchStatus.DELIVERED);

        drone.setState(DroneState.RETURNING);

        droneRepository.save(drone);

        dispatchRepository.save(dispatch.get());

        return "Dispatch completed";
    }

    public String cancelDispatch(String dispatchReference, String droneSerialNumber){

        var drone = droneService.findDroneBySerialNumber(droneSerialNumber);

        var dispatch = dispatchRepository.findDispatchByReference(dispatchReference);

        if (dispatch.isEmpty()) throw new RuntimeException("Dispatch entity not found");

        if (dispatch.get().getStatus()==DispatchStatus.DELIVERING) throw new RuntimeException("Dispatch cannot be cancelled in DELIVERING state");

        dispatch.get().setStatus(DispatchStatus.CANCELLED);

        drone.setState(DroneState.RETURNING);

        droneRepository.save(drone);

        dispatchRepository.save(dispatch.get());

        return "Dispatch cancelled";
    }

}
