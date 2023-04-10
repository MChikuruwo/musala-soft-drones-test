package com.musala.musalatest.business.dto;

import com.musala.musalatest.business.enums.DroneModel;
import com.musala.musalatest.business.enums.DroneState;

public record DroneResponse(String serialNumber, DroneModel model, int weightLimit, int batteryCapacity,
                            DroneState state) {
}
