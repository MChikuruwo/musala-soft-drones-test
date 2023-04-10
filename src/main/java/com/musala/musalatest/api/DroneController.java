package com.musala.musalatest.api;

import com.musala.musalatest.business.dto.DroneRequest;
import com.musala.musalatest.business.enums.DroneModel;
import com.musala.musalatest.service.DroneService;
import com.musala.musalatest.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drone")
@RequiredArgsConstructor
public class DroneController {

    private final DroneService droneService;

    @PostMapping("/register")
    public ApiResponse registerDrone(@RequestBody DroneRequest droneRequest) {
        return new ApiResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), droneService.register(droneRequest));
    }

    @GetMapping("/serialNumber")
    public ApiResponse findDroneBySerialNumber(@RequestParam("serial") String serialNumber){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), droneService.findDroneBySerialNumber(serialNumber));

    }

    @GetMapping("/drone-model")
    public ApiResponse findDroneBySerialNumber(@RequestParam("model") DroneModel model){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), droneService.findDroneByModel(model));
    }

    @GetMapping("/all")
    public ApiResponse findAllDrones(){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), droneService.findAll());
    }

    @GetMapping("/available")
    public ApiResponse findAvailableDrones(){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), droneService.listAvailableDrones());
    }

    @GetMapping("/battery")
    public ApiResponse checkBatteryLevel(@RequestParam("serial-number") String serialNumber) throws InterruptedException {

        droneService.checkDroneBatteryLevel(serialNumber);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
