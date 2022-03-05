package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.RentCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentCarDao extends JpaRepository<RentCar, Integer> {

    List<RentCar> getRentCarByCar_CarId(int carId);

    RentCar getRentCarByCar_CarIdAndDateOfReceiptIsNull(int carId);

    boolean existsByRentCarId(int rentCarId);

}
