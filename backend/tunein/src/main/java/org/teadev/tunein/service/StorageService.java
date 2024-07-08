package org.teadev.tunein.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.teadev.tunein.dto.response.FileUploadResponse;
import org.teadev.tunein.exceptions.FileUploadException;


@Service
@Log4j2
public class StorageService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    private final String STORAGE_SERVER_PREFIX = "http://localhost:8085/storage";
    
    public FileUploadResponse uploadFile(MultipartFile file) {
        final String URL = STORAGE_SERVER_PREFIX.concat("/upload");
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", file.getResource());
        
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<FileUploadResponse> response = null;
        try {
            response = restTemplate.postForEntity(URL, request, FileUploadResponse.class);
        } catch (Exception e) {
            log.error("Failure while uploading file to storage server", e);
            throw new FileUploadException("Failure while uploading file to storage server");
        }
        
        if (response.getStatusCode() != HttpStatusCode.valueOf(HttpStatus.OK.value()) ||
                response.getBody() == null) {
            log.error("Invalid response received");
            if (response.getBody() != null) {
                log.error("Invalid response body {}", response.getBody());
            }
            throw new FileUploadException("Failure while uploading file to storage server");
        }
        
        return response.getBody();
    }
    
}
