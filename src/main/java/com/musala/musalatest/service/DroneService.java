package com.musala.musalatest.service;

import com.musala.musalatest.Exceptions.DroneUnavailableException;
import com.musala.musalatest.Exceptions.DronesUnavailableException;
import com.musala.musalatest.business.dao.DroneRepository;
import com.musala.musalatest.business.dto.DroneRequest;
import com.musala.musalatest.business.dto.DroneResponse;
import com.musala.musalatest.business.enums.DroneModel;
import com.musala.musalatest.business.enums.DroneState;
import com.musala.musalatest.business.model.Drone;
import com.musala.musalatest.util.ScheduledTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .batteryCapacity(droneRequest.batteryCapacity())
                .build();

        droneRepository.save(drone);

        return new DroneResponse(drone.getSerialNumber(), drone.getModel(), drone.getWeightLimit(), drone.getBatteryCapacity(), drone.getState());
    }

    public Drone findDroneBySerialNumber(String serialNumber) {

        var drone = droneRepository.findDroneBySerialNumber(serialNumber);

        if (drone.isEmpty())
            throw new DroneUnavailableException("Drone with serial number: " + serialNumber + " not found.");

        return drone.get();

    }

    public DroneResponse findDroneByModel(DroneModel model) {

        var drone = droneRepository.findDroneByModel(model);

        if (drone.isEmpty()) throw new DroneUnavailableException("Drone model: " + model + " not found.");

        return new DroneResponse(drone.get().getSerialNumber(), drone.get().getModel(), drone.get().getWeightLimit(), drone.get().getBatteryCapacity(), drone.get().getState());

    }

    public List<DroneResponse> findAll() {
        var drones = droneRepository.findAll();

        if (drones.isEmpty()) throw new DronesUnavailableException("Drones unavailable at the moment");

        return drones.stream().map(a -> new DroneResponse(a.getSerialNumber(), a.getModel(), a.getWeightLimit(), a.getBatteryCapacity(), a.getState())).collect(Collectors.toList());
    }

    public List<DroneResponse> listAvailableDrones() {

        var drones = droneRepository.findAllByStateEquals(DroneState.IDLE);

        if (drones.isEmpty()) throw new DronesUnavailableException("No drones available at the moment");

        return drones.stream().map(a -> new DroneResponse(a.getSerialNumber(), a.getModel(), a.getWeightLimit(), a.getBatteryCapacity(), a.getState())).collect(Collectors.toList());
    }

    public void checkDroneBatteryLevel(String serialNumber) throws InterruptedException {
        var drone = findDroneBySerialNumber(serialNumber);

        var batteryLife = drone.getBatteryCapacity();

        var time = new Timer();

        ScheduledTask st = new ScheduledTask();

        time.schedule(st, 0, 180000);

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//                batteryLife[0]--;
//            }
//        };
//
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//        executor.scheduleAtFixedRate(runnable, 0, 3, TimeUnit.MINUTES);


        //add battery checking logic here, have a decremental function as well for a given interval
        for (batteryLife = 100; batteryLife >= 0; batteryLife--) {
            System.out.println("Battery Health remaining: " + batteryLife);
            Thread.sleep(2000);
            if (batteryLife == 0) {
                System.out.println("Battery drained");
                System.exit(0);
            }

        }
    }
}