package org.teadev.tunein.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "likes")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;
    
    @ManyToOne
    @JoinColumn(name = "post_id")
    PostEntity post;
    
    @ManyToOne
    @JoinColumn(name = "comment_id")
    CommentEntity comment;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;
    
    @CreationTimestamp
    private Date createdAt;
    
}
