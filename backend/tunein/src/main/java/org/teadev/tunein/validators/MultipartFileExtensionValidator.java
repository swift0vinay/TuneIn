package org.teadev.tunein.validators;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.teadev.tunein.dto.request.PostEntityRequestDto;

import java.util.List;

@Component
@Log4j2
public class MultipartFileExtensionValidator implements Validator {
    
    private static final String[] ACCEPTED_EXTENSIONS = {"mp3", "mp4", "heic", "jpeg", "jpg", "png"};
    
    private static final Integer MAX_MEDIA_COUNT = 5;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return PostEntityRequestDto.class.equals(clazz);
    }
    
    private boolean isAcceptedExtension(String fileName) {
        for (String extension : ACCEPTED_EXTENSIONS) {
            if (fileName.toLowerCase().endsWith("." + extension)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        log.info("Validating post entity");
        PostEntityRequestDto dto = (PostEntityRequestDto) target;
        List<MultipartFile> files = dto.getFiles();
        if (files == null) {
            log.info("Files value is NULL");
            return;
        }
        if (files.size() > MAX_MEDIA_COUNT) {
            errors.rejectValue("files", "MAX_ITEMS_EXCEEDED", "Maximum 5 items can be uploaded at a time");
            return;
        }
        files.forEach(file -> {
            log.info(file.getOriginalFilename());
            if (!isAcceptedExtension(file.getOriginalFilename())) {
                errors.rejectValue("files", "INVALID_EXTENSION", "Non-acceptable file extension found for file " + file.getOriginalFilename());
            }
        });
    }
    
}
