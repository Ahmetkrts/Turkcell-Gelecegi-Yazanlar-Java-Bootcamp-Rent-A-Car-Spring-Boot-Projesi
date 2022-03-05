package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarDao extends JpaRepository<Car, Integer> {
    List<Car> findByDailyPriceLessThanEqual(int dailyPrice);

    boolean existsByCarId(int carId);

}
