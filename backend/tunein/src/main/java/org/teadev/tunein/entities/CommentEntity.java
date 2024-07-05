package org.teadev.tunein.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "comments")
@Entity
@Data
public class CommentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postId;
    
    private String body;
    
    @CreationTimestamp
    private Date createdAt;
    
}
