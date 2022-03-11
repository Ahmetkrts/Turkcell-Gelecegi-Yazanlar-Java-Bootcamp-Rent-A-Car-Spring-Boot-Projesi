package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concrates.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {
    boolean existsById(int cityId);

    boolean existsByName(String cityName);
}
