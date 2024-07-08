package org.teadev.tunein_storage.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.teadev.tunein_storage.entity.MediaItem;
import org.teadev.tunein_storage.response.FileUploadResponse;
import org.teadev.tunein_storage.service.storage.StorageService;
import org.teadev.tunein_storage.utility.FileUtility;

import java.io.File;

@RestController
@RequestMapping("/storage")
@Log4j2
public class StorageController {
    
    @Autowired
    private StorageService storageService;
    
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam(value = "file") MultipartFile requestFile) {
        File file = FileUtility.multipartFileToLegacyFile(requestFile);
        MediaItem mediaItem = storageService.upload(file);
        FileUploadResponse response = FileUploadResponse
                .builder()
                .id(mediaItem.getId().toString())
                .url(mediaItem.getUrl())
                .createdAt(mediaItem.getCreatedAt())
                .build();
        return ResponseEntity.ok(response);
    }
    
}
