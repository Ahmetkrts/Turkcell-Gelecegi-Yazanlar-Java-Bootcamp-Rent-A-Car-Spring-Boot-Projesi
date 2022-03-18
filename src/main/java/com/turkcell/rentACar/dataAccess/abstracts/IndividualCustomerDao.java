package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.IndividualCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {
}
