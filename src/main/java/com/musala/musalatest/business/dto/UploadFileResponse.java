package com.musala.musalatest.business.dto;

public record UploadFileResponse(String fileName,String fileDownloadUri,String fileType,long size) {
}
