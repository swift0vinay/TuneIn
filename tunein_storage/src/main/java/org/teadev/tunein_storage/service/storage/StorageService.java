package org.teadev.tunein_storage.service.storage;

import org.teadev.tunein_storage.entity.MediaItem;

import java.io.File;
import java.util.List;

public interface StorageService {
    
    /*
    Upload the file to storage server
    and return the file url.
     */
    MediaItem upload(File file);
    
    /*
    Delete the requested directory
     */
    boolean deleteDir(String name);
    
    
    /*
    Delete the requested file
     */
    boolean deleteFile(String name);
    
    
    /*
    Fetch the file from the storage using
    requested url.
     */
    File getFile(String filePath);
    
    List<String> listFiles();
    
}
