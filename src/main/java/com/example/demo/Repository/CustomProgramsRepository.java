package com.example.demo.Repository;

import com.example.demo.Model.Entity.CustomPrograms;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomProgramsRepository extends JpaRepository<CustomPrograms, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE custom_programs
        SET library              = :library,
            domain               = :domain,
            program_name         = :programName,
            program_goal         = :programGoal,
            objective_one        = :objectiveOne,
            objective_two        = :objectiveTwo,
            objective_three      = :objectiveThree,
            exercise             = :exercise,
            generalization       = :generalization,
            error_correction     = :errorCorrection,
            supplies             = :supplies,
            teaching_strategies  = :teachingStrategies,
            troubleshooting      = :troubleshooting,
            helpful_hints        = :helpfulHints,
            modified_at          = NOW()
        WHERE program_id = :id
        """, nativeQuery = true)
    int UpdateCustomProgram(
            @Param("id") Long id,
            @Param("library") String library,
            @Param("domain") String domain,
            @Param("programName") String programName,
            @Param("programGoal") String programGoal,
            @Param("objectiveOne") String objectiveOne,
            @Param("objectiveTwo") String objectiveTwo,
            @Param("objectiveThree") String objectiveThree,
            @Param("exercise") String exercise,
            @Param("generalization") String generalization,
            @Param("errorCorrection") String errorCorrection,
            @Param("supplies") String supplies,
            @Param("teachingStrategies") String teachingStrategies,
            @Param("troubleshooting") String troubleshooting,
            @Param("helpfulHints") String helpfulHints
    );
}
