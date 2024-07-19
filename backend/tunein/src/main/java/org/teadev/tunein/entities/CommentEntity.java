package org.teadev.tunein.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import java.util.Date;
import java.util.List;

@Table(name = "comments")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @OneToMany(orphanRemoval = true, mappedBy = "comment")
    private List<LikeEntity> likes;
    
    private String body;
    
    @CreationTimestamp
    private Date createdAt;
    
    @Formula(value = "(SELECT COUNT(*) from likes where likes.comment_id=id)")
    private Long likeCount = 0L;
    
    @Transient
    public Long getLikeCount() {
        return likeCount;
    }
    
}
