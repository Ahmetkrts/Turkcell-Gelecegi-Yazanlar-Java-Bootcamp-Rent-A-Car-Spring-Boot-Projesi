package com.turkcell.rentACar.entities.concrates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private int id;

    private String name;

    @OneToMany(mappedBy = "fromCity")
    private List<RentCar> fromRentCars;

    @OneToMany(mappedBy = "toCity")
    private List<RentCar> toRentCars;


}
