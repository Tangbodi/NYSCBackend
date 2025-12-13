package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientsFunders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ClientsFundersRepository extends JpaRepository<ClientsFunders, Long> {

    @Query(value = "SELECT *\n" +
            "FROM nysc.clients_funders cf\n" +
            "WHERE client_id = :id", nativeQuery = true)
    List<Map<String, Object>> findByClientId(@Param("id") Long clientId);
}
