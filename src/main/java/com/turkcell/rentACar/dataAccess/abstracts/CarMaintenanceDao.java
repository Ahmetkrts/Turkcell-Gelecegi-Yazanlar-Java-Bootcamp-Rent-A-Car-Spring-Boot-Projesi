package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer> {

    List<CarMaintenance> getCarMaintenanceByCarCarId(int carId);

    CarMaintenance getCarMaintenanceByCarCarIdAndReturnDateIsNull(int carId);

    boolean existsById(int carMaintenanceId);
}
