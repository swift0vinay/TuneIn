package org.teadev.tunein_storage.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Table(name = "media")
@Entity
@Builder
@Getter
public class MediaItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @CreationTimestamp
    @Column(nullable = false)
    private Date createdAt;
    
    @Column(nullable = false)
    private String extension;
    
    @Column(nullable = false)
    private String url;
    
}
