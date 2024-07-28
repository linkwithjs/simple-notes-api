package com.linkwithjs.simplenotesapi.entity;

import com.linkwithjs.simplenotesapi.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="todos")
public class TodoEntity extends BaseEntity {
    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;
}
