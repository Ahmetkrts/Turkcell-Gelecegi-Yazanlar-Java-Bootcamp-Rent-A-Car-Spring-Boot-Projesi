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

    @Column(name = "car_return_date")
    private LocalDate carReturnDate;

    @Column(name = "start_distance")
    private double startDistance;

    @Column(name = "return_distance")
    private double returnDistance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "from_city_id")
    private City fromCity;

    @ManyToOne
    @JoinColumn(name = "to_city_id")
    private City toCity;


    @OneToMany(mappedBy = "rentCar")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "rentCar")
    private List<OrderedAdditional> orderedAdditionals;


}
