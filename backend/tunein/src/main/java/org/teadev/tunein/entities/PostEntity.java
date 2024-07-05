package org.teadev.tunein.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Table(name = "posts")
@Entity
@Data
public class PostEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;
    
    @Column(nullable = false)
    @CreationTimestamp
    private Date createdAt;
    
    @Column(nullable = false)
    private Date updatedAt;
    
    @Column
    private String body;
    
    @Transient
    private List<MultipartFile> files;
    
    @Column
    private List<String> filePaths;
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments;
    
}
