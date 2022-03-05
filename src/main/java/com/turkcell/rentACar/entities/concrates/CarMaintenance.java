package com.turkcell.rentACar.entities.concrates;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_maintenances")

public class CarMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_maintanence_id")
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "return_date")
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;


}
