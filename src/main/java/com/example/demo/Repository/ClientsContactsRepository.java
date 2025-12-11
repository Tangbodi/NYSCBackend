package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientsContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsContactsRepository extends JpaRepository<ClientsContacts, Long> {

}
