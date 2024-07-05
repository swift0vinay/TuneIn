package org.teadev.tunein.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.teadev.tunein.entities.enums.RoleType;

import java.util.Date;

@Table(name = "roles")
@Entity
@Data
@ToString
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Integer id;
    
    @Column(nullable = false)
    private String description;
    
    @Column(unique = true, nullable = false)
    private RoleType roleType;
    
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    
    @UpdateTimestamp
    private Date updatedAt;
    
}
