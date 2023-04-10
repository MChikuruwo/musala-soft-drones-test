package com.musala.musalatest.api;

import com.musala.musalatest.business.dto.MedicationRequest;
import com.musala.musalatest.service.MedicationService;
import com.musala.musalatest.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/medication")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse addMedication(@RequestBody MedicationRequest medicationRequest) {
        return new ApiResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), medicationService.addMedication(medicationRequest));
    }

    @PutMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse uploadImage(@RequestParam("code") String code, @RequestParam("image") MultipartFile file) {

        medicationService.uploadImage(code, file);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @GetMapping("/by-name")
    public ApiResponse findMedicationByName(@RequestParam("name") String name) {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), medicationService.findMedicationByName(name));
    }

    @GetMapping("/by-code")
    public ApiResponse findMedicationByCode(@RequestParam("code") String code) {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), medicationService.findMedicationByCode(code));
    }

    @GetMapping("/all")
    public ApiResponse findDroneBySerialNumber() {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), medicationService.findAll());
    }
}
