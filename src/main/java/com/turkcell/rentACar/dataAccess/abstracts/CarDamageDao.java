package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.CarDamage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDamageDao extends JpaRepository<CarDamage, Integer> {

    List<CarDamage> getByCar_CarId(int carId);
}
