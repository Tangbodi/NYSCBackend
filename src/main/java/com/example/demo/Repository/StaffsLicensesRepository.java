package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffsLicenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffsLicensesRepository extends JpaRepository<StaffsLicenses, Long> {

}
