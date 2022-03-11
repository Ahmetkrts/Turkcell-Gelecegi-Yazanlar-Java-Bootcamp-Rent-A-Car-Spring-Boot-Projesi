package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.Additional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalDao extends JpaRepository<Additional, Integer> {
    boolean existsByAdditionalId(int additionalId);

    boolean existsByName(String additionalName);
}
