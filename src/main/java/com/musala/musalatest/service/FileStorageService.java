package com.musala.musalatest.service;

import com.musala.musalatest.Exceptions.FileNotFoundException;
import com.musala.musalatest.Exceptions.FileStorageException;
import com.musala.musalatest.business.dao.FileRepository;
import com.musala.musalatest.business.dao.MedicationRepository;
import com.musala.musalatest.business.model.FileStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Objects;

/**
 * @author M.Chikuruwo
 */

@Slf4j
@Component
@Service
@AllArgsConstructor
public class FileStorageService {

    private final FileRepository dbFileRepository;
    private final MedicationRepository medicationRepository;

    public FileStorage storeFile(String medicationCode,MultipartFile file) {

        var medication = medicationRepository.findMedicationByCode(medicationCode);


        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            FileStorage dbFile = new FileStorage(fileName, file.getContentType(), file.getBytes());

            medication.get().setFileStorage(dbFile);

            medicationRepository.save(medication.get());

            return dbFileRepository.save(dbFile);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }

    public FileStorage getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
}