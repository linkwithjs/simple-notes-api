package com.linkwithjs.simplenotesapi.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private final boolean isDeleted = false;
}
