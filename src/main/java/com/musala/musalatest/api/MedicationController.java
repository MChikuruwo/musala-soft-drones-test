package com.musala.musalatest.api;

import com.musala.musalatest.business.dto.MedicationRequest;
import com.musala.musalatest.business.dto.UploadFileResponse;
import com.musala.musalatest.business.model.FileStorage;
import com.musala.musalatest.service.FileStorageService;
import com.musala.musalatest.service.MedicationService;
import com.musala.musalatest.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/medication")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;
    private final FileStorageService fileStorageService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse addMedication(@RequestBody MedicationRequest medicationRequest) {
        return new ApiResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), medicationService.addMedication(medicationRequest));
    }

    @PutMapping(value = "/image-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse uploadImage(@RequestParam("medication-code") String code, @RequestParam("image") MultipartFile file) {

        FileStorage dbFile = fileStorageService.storeFile(code, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
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
