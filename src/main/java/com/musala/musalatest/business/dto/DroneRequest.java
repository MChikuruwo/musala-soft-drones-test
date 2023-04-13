package com.musala.musalatest.business.dto;

import com.musala.musalatest.business.enums.DroneModel;

public record DroneRequest(String serialNumber, DroneModel model, int weightLimit) {
}
