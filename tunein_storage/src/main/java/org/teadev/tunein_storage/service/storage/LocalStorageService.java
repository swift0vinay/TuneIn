package org.teadev.tunein_storage.service.storage;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.teadev.tunein_storage.constants.ErrorMessage;
import org.teadev.tunein_storage.entity.MediaItem;
import org.teadev.tunein_storage.exceptions.StorageServerException;
import org.teadev.tunein_storage.repository.StorageRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class LocalStorageService implements StorageService {
    
    @Value("${storage.root}")
    private String LOCAL_STORAGE_PATH;
    
    @Autowired
    private StorageRepository storageRepository;
    
    @PostConstruct
    public void init() {
        try {
            File file = new File(LOCAL_STORAGE_PATH);
            FileUtils.forceMkdir(file);
            log.info("Storage server created");
        } catch (Exception e) {
            log.error(ErrorMessage.FAILED_TO_CREATE_STORAGE_SERVER_MESSAGE);
            throw new RuntimeException(ErrorMessage.FAILED_TO_CREATE_STORAGE_SERVER_MESSAGE);
        }
    }
    
    private Optional<String> uploadFile(File requestFile) {
        final String extension = FilenameUtils.getExtension(requestFile.getName());
        final String filePath = LOCAL_STORAGE_PATH
                .concat(File.separator)
                .concat(UUID.randomUUID().toString())
                .concat(FilenameUtils.EXTENSION_SEPARATOR_STR)
                .concat(extension);
        try {
            File file = new File(filePath);
            try (OutputStream os = new FileOutputStream(file)) {
                os.write(FileUtils.readFileToByteArray(requestFile));
            } catch (Exception e) {
                log.error("Failed to write the new file {}", requestFile.getName());
                throw new RuntimeException("Failed to write the new file " + requestFile.getName());
            }
            log.info("Saving file {} to local storage", file.getAbsolutePath());
            return Optional.of(filePath.substring(LOCAL_STORAGE_PATH.length()));
        } catch (Exception e) {
            log.error("Failed to save file on storage - " + e);
        }
        return Optional.empty();
    }
    
    @Override
    public boolean deleteDir(String name) {
        try {
            FileUtils.deleteDirectory(new File(name));
            return true;
        } catch (Exception e) {
            log.error("Failed to remove dir {} - " + e, name);
        }
        return false;
    }
    
    @Override
    public boolean deleteFile(String name) {
        return FileUtils.deleteQuietly(
                new File(LOCAL_STORAGE_PATH.concat(File.separator).concat(name))
        );
    }
    
    @Override
    public MediaItem upload(File file) {
        Optional<String> filePath = uploadFile(file);
        if (filePath.isEmpty()) {
            log.error("Failed to upload file " + file.getName());
            throw new StorageServerException("Failed to upload file " + file.getName());
        }
        MediaItem mediaItem = MediaItem.builder()
                .url(filePath.get())
                .extension(FilenameUtils.getExtension(file.getName()))
                .build();
        storageRepository.save(mediaItem);
        return mediaItem;
    }
    
    @Override
    public File getFile(String filePath) {
        return null;
    }
    
}
