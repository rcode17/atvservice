package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atvffcae")
@IdClass(AtvffCaeId.class)
public class AtvffCae {

    @Id
    @Column(name = "caano", nullable = false)
    private Integer caano;

    @Id
    @Column(name = "cames", nullable = false)
    private Integer cames;

    @Column(name = "cacumpli", nullable = false)
    private Integer cacumpli;

    @Column(name = "cacalid", nullable = false)
    private Integer cacalid;

    @Column(name = "canombre", length = 30)
    private String canombre;

    @Column(name = "caregion", length = 6)
    private String caregion;
}