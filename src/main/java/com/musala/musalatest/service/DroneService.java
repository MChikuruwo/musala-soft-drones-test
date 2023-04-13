package com.musala.musalatest.service;

import com.musala.musalatest.Exceptions.DroneUnavailableException;
import com.musala.musalatest.Exceptions.DronesUnavailableException;
import com.musala.musalatest.Exceptions.WeightOverloadException;
import com.musala.musalatest.business.dao.DroneRepository;
import com.musala.musalatest.business.dto.DroneRequest;
import com.musala.musalatest.business.dto.DroneResponse;
import com.musala.musalatest.business.enums.DroneModel;
import com.musala.musalatest.business.enums.DroneState;
import com.musala.musalatest.business.model.Drone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DroneService {

    private final DroneRepository droneRepository;

    public DroneResponse register(DroneRequest droneRequest) {

        var drone = Drone.builder()
                .model(droneRequest.model())
                .serialNumber(droneRequest.serialNumber())
                .weightLimit(droneRequest.weightLimit())
                .state(DroneState.IDLE)
                .batteryCapacity(100)
                .build();

        if (drone.getWeightLimit() > 500) throw new WeightOverloadException("drone weight cannot exceed 500!");

        droneRepository.save(drone);

        return new DroneResponse(drone.getSerialNumber(), drone.getModel(), drone.getWeightLimit(), drone.getBatteryCapacity(), drone.getState());
    }

    public Drone findDroneBySerialNumber(String serialNumber) {

        var drone = droneRepository.findDroneBySerialNumber(serialNumber);

        if (drone.isEmpty())
            throw new DroneUnavailableException("Drone with serial number: " + serialNumber + " not found.");

        return drone.get();

    }

    public List<DroneResponse> findDroneByModel(DroneModel model) {

        var drones = droneRepository.findDronesByModel(model);

        if (drones.isEmpty()) throw new DroneUnavailableException("Drones unavailable");

        return drones.stream().map(drone -> (new DroneResponse(drone.getSerialNumber(), drone.getModel(),
                drone.getWeightLimit(), drone.getBatteryCapacity(), drone.getState()))).collect(Collectors.toList());

    }

    public List<DroneResponse> findAll() {
        var drones = droneRepository.findAll();

        if (drones.isEmpty()) throw new DronesUnavailableException("Drones unavailable at the moment");

        return drones.stream().map(a -> new DroneResponse(a.getSerialNumber(), a.getModel(), a.getWeightLimit(), a.getBatteryCapacity(), a.getState())).collect(Collectors.toList());
    }

    public List<DroneResponse> listAvailableDrones() {

        var availableDrones = new ArrayList<Drone>();

        var idleDrones = droneRepository.findAllByStateEquals(DroneState.IDLE);

        var loadedDrones = droneRepository.findAllByStateEquals(DroneState.LOADED);

        var returningDrones = droneRepository.findAllByStateEquals(DroneState.RETURNING);

        var deliveredDrones = droneRepository.findAllByStateEquals(DroneState.DELIVERED);

        availableDrones.addAll(idleDrones);

        availableDrones.addAll(loadedDrones);

        availableDrones.addAll(returningDrones);

        availableDrones.addAll(deliveredDrones);

        if (availableDrones.isEmpty()) throw new DronesUnavailableException("No drones available at the moment");

        return availableDrones.stream().map(a -> new DroneResponse(a.getSerialNumber(), a.getModel(), a.getWeightLimit(), a.getBatteryCapacity(), a.getState())).collect(Collectors.toList());
    }


}