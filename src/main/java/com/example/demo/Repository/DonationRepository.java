package com.example.demo.Repository;

import com.example.demo.Model.Entity.Donation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE financial_donations
        SET donation_date = :donationDate,
            donor         = :donor,
            amount        = :amount,
            donation_type = :donationType,
            note          = :note,
            updated_at    = NOW()
        WHERE donation_id = :donationId
        """, nativeQuery = true)
    int updateDonation(
            @Param("donationId") Long donationId,
            @Param("donationDate") String donationDate,
            @Param("donor") String donor,
            @Param("amount") java.math.BigDecimal amount,
            @Param("donationType") String donationType,
            @Param("note") String note
    );
}
