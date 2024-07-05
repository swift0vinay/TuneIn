package org.teadev.tunein.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name = "likes")
@Entity
@Data
public class LikeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;
    
    @ManyToOne
    @JoinColumn(name = "post_id")
    PostEntity postId;
    
    @ManyToOne
    @JoinColumn(name = "comment_id")
    CommentEntity commentId;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity userId;
    
    @CreationTimestamp
    private Date createdAt;
    
}
