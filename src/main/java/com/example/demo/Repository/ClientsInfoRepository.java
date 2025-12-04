package com.example.demo.Repository;

import com.example.demo.Model.Entity.ClientsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsInfoRepository extends JpaRepository<ClientsInfo, Long> {

}
