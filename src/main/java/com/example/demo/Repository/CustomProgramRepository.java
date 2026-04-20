package com.example.demo.Repository;

import com.example.demo.Model.Entity.CustomProgram;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomProgramRepository extends JpaRepository<CustomProgram, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE custom_programs
        SET library = :library,
            domain = :domain,
            program_name = :programName,
            program_goal = :programGoal,
            modified_at = NOW()
        WHERE program_id = :id
        """, nativeQuery = true)
    int UpdateCustomProgram(
            @Param("id") Long id,
            @Param("library") String library,
            @Param("domain") String domain,
            @Param("programName") String programName,
            @Param("programGoal") String programGoal
    );
}
