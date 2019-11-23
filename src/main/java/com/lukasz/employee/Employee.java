package com.lukasz.employee;

import com.lukasz.parking.Parking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long employeeId;
    private String name;
    private String surname;
    private String position;

    @JoinColumn(name = "parkingId")
    @ManyToOne
    private Parking parking;

    public Employee(String name, String surname, String position, Parking parking){
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.parking = parking;
    }
}