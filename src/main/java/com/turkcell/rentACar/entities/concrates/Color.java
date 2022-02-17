package com.turkcell.rentACar.entities.concrates;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private int colorId;
    @Column(name = "name", unique = true)
    private String name;
    @OneToMany(mappedBy = "color")
    private List<Car> cars;


}
