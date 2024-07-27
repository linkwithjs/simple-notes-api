package com.linkwithjs.simplenotesapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="todos")
public class TodoEntity extends BaseEntity {
    private String title;
    private boolean completed=false;
}
