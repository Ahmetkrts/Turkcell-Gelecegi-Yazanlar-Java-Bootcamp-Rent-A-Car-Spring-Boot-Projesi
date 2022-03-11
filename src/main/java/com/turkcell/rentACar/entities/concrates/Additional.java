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
@Table(name = "additionals")
public class Additional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_id")
    private int additionalId;
    @Column(name = "name")
    private String name;
    @Column(name = "daily_price")
    private double dailyPrice;

    @OneToMany(mappedBy = "additional")
    private List<OrderedAdditional> orderedAdditionals;


}
