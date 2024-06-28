package com.arekproject.studiaprojekt.repository;

import com.arekproject.studiaprojekt.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    @Query("from Notes as n where n.userDtls.id=:uid")
    Page<Notes> findByNotesByUser(@Param("uid") int uid, Pageable p);
}
