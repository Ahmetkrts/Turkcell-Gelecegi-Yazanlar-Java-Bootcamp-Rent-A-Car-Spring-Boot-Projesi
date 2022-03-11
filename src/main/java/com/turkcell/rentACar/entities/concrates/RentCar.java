package com.turkcell.rentACar.entities.concrates;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    private LocalDate dateOfIssue;
    @Column(name = "date_of_receipt")
    private LocalDate dateOfReceipt;

    @Column(name = "rent_first_name")
    private String rentFirstName;

    @Column(name = "rent_last_name")
    private String rentLastName;
    @Column(name = "total_fee")
    private double totalFee;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @OneToMany(mappedBy = "rentCar")
    private List<OrderedAdditional> orderedAdditionals;

    @ManyToOne
    @JoinColumn(name = "from_city_id")
    private City fromCity;
    @ManyToOne
    @JoinColumn(name = "to_city_id")
    private City toCity;


}
