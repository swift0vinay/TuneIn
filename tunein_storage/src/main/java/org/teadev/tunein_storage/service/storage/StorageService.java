package org.teadev.tunein_storage.service.storage;

import org.teadev.tunein_storage.entity.MediaItem;

import java.io.File;

public interface StorageService {
    
    /*
    Upload the file to storage server
    and return the file url.
     */
    public MediaItem upload(File file);
    
    /*
    Delete the requested directory
     */
    public boolean deleteDir(String name);
    
    
    /*
    Delete the requested file
     */
    public boolean deleteFile(String name);
    
    
    /*
    Fetch the file from the storage using
    requested url.
     */
    public File getFile(String filePath);
    
}
