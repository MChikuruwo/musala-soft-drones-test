package com.musala.musalatest.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class FileStorageProperties {

    @Value("upload-dir")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
}