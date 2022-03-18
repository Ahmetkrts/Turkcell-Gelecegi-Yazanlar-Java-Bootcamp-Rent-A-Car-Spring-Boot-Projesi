package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.OrderedAdditional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedAdditionalDao extends JpaRepository<OrderedAdditional, Integer> {
    List<OrderedAdditional> findAllByOrderedAdditionalId(int orderedAdditionalId);

    List<OrderedAdditional> getByRentCarRentCarId(int rentCarId);
}
