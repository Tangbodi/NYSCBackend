package com.example.demo.Repository;

import com.example.demo.Model.Entity.MedicaidRemittanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicaidRemittanceRepository extends JpaRepository<MedicaidRemittanceRecord, Long> {

    @Query(value = """
        SELECT * FROM medicaid_remittance_records
        WHERE YEAR(date_of_service) = :year
          AND status IN ('PAID', 'VOID', 'ADJT')
        ORDER BY cycle ASC, date_of_service ASC
        """, nativeQuery = true)
    List<MedicaidRemittanceRecord> findByYear(@Param("year") int year);
}
