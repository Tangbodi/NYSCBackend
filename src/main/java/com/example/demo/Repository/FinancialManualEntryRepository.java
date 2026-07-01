package com.example.demo.Repository;

import com.example.demo.Model.Entity.FinancialManualEntry;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FinancialManualEntryRepository extends JpaRepository<FinancialManualEntry, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE financial_manual_entries
        SET entry_date = :entryDate,
            section    = :section,
            item       = :item,
            category   = :category,
            amount     = :amount,
            note       = :note,
            updated_at = NOW()
        WHERE entry_id = :entryId
        """, nativeQuery = true)
    int updateEntry(
            @Param("entryId") Long entryId,
            @Param("entryDate") String entryDate,
            @Param("section") String section,
            @Param("item") String item,
            @Param("category") String category,
            @Param("amount") BigDecimal amount,
            @Param("note") String note
    );
}
