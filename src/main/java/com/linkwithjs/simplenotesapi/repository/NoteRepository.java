package com.linkwithjs.simplenotesapi.repository;

import com.linkwithjs.simplenotesapi.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {

}
