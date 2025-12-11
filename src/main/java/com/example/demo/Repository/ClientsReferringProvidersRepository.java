package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientsReferringProviders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsReferringProvidersRepository extends JpaRepository<ClientsReferringProviders, Long> {

}
