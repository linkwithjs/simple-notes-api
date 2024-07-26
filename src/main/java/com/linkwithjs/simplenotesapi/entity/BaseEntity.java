package com.linkwithjs.simplenotesapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;


@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updatedAt;
    private final boolean isDeleted = false;

}
