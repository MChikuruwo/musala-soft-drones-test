package com.musala.musalatest.business.dto;

import com.musala.musalatest.business.model.Drone;
import com.musala.musalatest.business.model.Medication;

import java.util.List;

public record DispatchResponse(Drone drone, List<Medication> medications) {
}
