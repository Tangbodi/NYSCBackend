package com.example.demo.Repository;

import com.example.demo.Model.Entity.ProgramTarget;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramTargetRepository extends JpaRepository<ProgramTarget, Long> {

    List<ProgramTarget> findByProgramId(Long programId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM program_targets WHERE program_id = :programId", nativeQuery = true)
    void deleteByProgramId(@Param("programId") Long programId);
}
