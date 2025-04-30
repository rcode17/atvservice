package com.bancolombia.pocatv.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ATVFFCOMEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atvffcomen {

    @Id
    @Column(name = "COMCON")
    private Integer comcon;

    @Column(name = "COMJUS", length = 240)
    private String comjus;
}