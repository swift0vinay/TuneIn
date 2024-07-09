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
    private Integer id;
    
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
    
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<CommentEntity> comments;
    
    @Formula(value = "(SELECT COUNT(*) from likes where likes.post_id=id)")
    private Long likeCount = 0L;
    
}
