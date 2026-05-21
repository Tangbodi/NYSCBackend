package com.example.demo.Repository;

import com.example.demo.Model.Entity.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {

    List<EventDetails> findByClientId(Long clientId);

    List<EventDetails> findByStaffId(Long staffId);

    @Modifying
    @Query(value = "UPDATE event_details SET " +
            "type = :type, " +
            "date = :date, " +
            "start_time = :startTime, " +
            "end_time = :endTime, " +
            "pay_code = :payCode, " +
            "client_name = :clientName, " +
            "staff_member = :staffMember, " +
            "service = :service, " +
            "place_of_service = :placeOfService, " +
            "last_modified_by = :lastModifiedBy, " +
            "tag = :tag, " +
            "staff_reminders = :staffReminders, " +
            "verifications = :verifications, " +
            "cancellations = :cancellations, " +
            "modified_at = :modifiedAt " +
            "WHERE event_id = :eventId",
            nativeQuery = true)
    void UpdateEventDetails(
            @Param("eventId") Long eventId,
            @Param("type") String type,
            @Param("date") String date,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("payCode") String payCode,
            @Param("clientName") String clientName,
            @Param("staffMember") String staffMember,
            @Param("service") String service,
            @Param("placeOfService") String placeOfService,
            @Param("lastModifiedBy") String lastModifiedBy,
            @Param("tag") String tag,
            @Param("staffReminders") String staffReminders,
            @Param("verifications") String verifications,
            @Param("cancellations") String cancellations,
            @Param("modifiedAt") Instant modifiedAt
    );
}
