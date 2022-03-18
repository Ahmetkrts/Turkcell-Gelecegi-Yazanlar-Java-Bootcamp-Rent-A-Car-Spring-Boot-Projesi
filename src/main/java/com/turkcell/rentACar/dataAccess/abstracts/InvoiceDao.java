package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
    List<Invoice> getByCustomerUserId(int customerId);

    List<Invoice> getByCreateDateBetween(LocalDate startingDate, LocalDate endingDate);

    boolean existsByCustomer_UserId(int customerId);

}
