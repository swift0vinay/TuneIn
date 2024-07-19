package org.teadev.tunein.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "posts")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(nullable = false)
    @CreationTimestamp
    private Date createdAt;
    
    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
    
    @Column
    private String body;
    
    @Column
    private String files;
    
    @OneToMany(orphanRemoval = true, mappedBy = "post")
    private List<CommentEntity> comments;
    
    @OneToMany(orphanRemoval = true, mappedBy = "post")
    private List<LikeEntity> likes;
    
    @Formula(value = "(SELECT COUNT(*) from likes where likes.post_id=id and likes.comment_id is NULL)")
    private Long likeCount = 0L;
    
    @Transient
    public Long getLikeCount() {
        return likeCount;
    }
    
}
