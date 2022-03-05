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
@Table(name = "rent_cars")
public class RentCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_car_id")
    private int rentCarId;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;
    @Column(name = "date_of_receipt")
    private Date dateOfReceipt;

    @Column(name = "rent_first_name")
    private String rentFirstName;

    @Column(name = "rent_last_name")
    private String rentLastName;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;


}
