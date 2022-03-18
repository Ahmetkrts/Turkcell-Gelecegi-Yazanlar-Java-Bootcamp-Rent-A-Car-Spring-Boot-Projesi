package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao extends JpaRepository<Car, Integer> {

    List<Car> findByDailyPriceLessThanEqual(int dailyPrice);

    boolean existsByCarId(int carId);

}
