package com.musala.musalatest.service;

import com.musala.musalatest.Exceptions.FileStorageException;
import com.musala.musalatest.Exceptions.MedicationAlreadyExistsException;
import com.musala.musalatest.Exceptions.MedicationUnavailableException;
import com.musala.musalatest.business.dao.MedicationRepository;
import com.musala.musalatest.business.dto.MedicationRequest;
import com.musala.musalatest.business.dto.MedicationResponse;
import com.musala.musalatest.business.model.FileStorage;
import com.musala.musalatest.business.model.Medication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;

//    private final FileStorageService fileStorageService;

    public MedicationResponse addMedication(MedicationRequest medicationRequest){

        var medication = Medication.builder()
                .name(medicationRequest.name())
                .weight(medicationRequest.weight())
                .code(medicationRequest.code())
                .build();

        medicationRepository.save(medication);


        return new MedicationResponse(medicationRequest.name(), medicationRequest.weight(), medicationRequest.code());
    }


    public MedicationResponse findMedicationByName(String name){
        var medication = medicationRepository.findMedicationByName(name);

        if (medication.isEmpty()) throw new MedicationUnavailableException("Medication with name: "+ name+ " unavailable");

        return new MedicationResponse(medication.get().getName(),medication.get().getWeight(),medication.get().getCode());
    }

    public Medication findMedicationByCode(String code){
        var medication = medicationRepository.findMedicationByCode(code);

        if (medication.isEmpty()) throw new MedicationUnavailableException("Medication with code: "+ code+ " unavailable");

        return medication.get();
    }

    public List<MedicationResponse> findAll(){
        var medications = medicationRepository.findAll();

        if (medications.isEmpty()) throw new MedicationUnavailableException("medications unavailable at the moment");

        return medications.stream().map(a->new MedicationResponse(a.getName(),a.getWeight(), a.getCode())).collect(Collectors.toList());
    }
}
