package com.linkwithjs.simplenotesapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="notes")
public class Note {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

}
