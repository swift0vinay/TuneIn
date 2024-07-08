package org.teadev.tunein_storage.utility;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Log4j2
public class FileUtility {
    
    public static File multipartFileToLegacyFile(MultipartFile file) {
        File tempFile = null;
        final String extension = file.getOriginalFilename() == null ? null :
                FilenameUtils.EXTENSION_SEPARATOR_STR
                        .concat(FilenameUtils.getExtension(file.getOriginalFilename()));
        try {
            tempFile = File.createTempFile(FilenameUtils.getBaseName(file.getOriginalFilename()), extension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (OutputStream os = new FileOutputStream(tempFile)) {
            os.write(file.getBytes());
        } catch (Exception e) {
            log.error("Failed to convert multipart file to file");
            throw new RuntimeException("Failed to convert multipart file to file");
        }
        return tempFile;
    }
    
}
