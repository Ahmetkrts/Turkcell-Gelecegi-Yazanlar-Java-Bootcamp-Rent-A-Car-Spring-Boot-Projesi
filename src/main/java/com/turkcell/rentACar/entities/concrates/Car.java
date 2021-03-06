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
@Table(name = "Cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    @Column(name = "daily_price")
    private int dailyPrice;

    @Column(name = "model_year")
    private int modelYear;

    @Column(name = "description")
    private String description;

    @Column(name = "distance")
    private double distance;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToMany(mappedBy = "car")
    private List<CarMaintenance> carMaintenances;

    @OneToMany(mappedBy = "car")
    private List<RentCar> rentCars;

    @OneToMany(mappedBy = "car")
    private List<CarDamage> carDamages;


    //Car-- id,dailyPrice,modelYear,description,Brand,Color
}
