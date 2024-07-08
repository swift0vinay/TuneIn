package org.teadev.tunein_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teadev.tunein_storage.entity.MediaItem;

import java.util.UUID;

public interface StorageRepository extends JpaRepository<MediaItem, UUID> {

}
