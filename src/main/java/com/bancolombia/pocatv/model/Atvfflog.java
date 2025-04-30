package com.bancolombia.pocatv.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "atvfflog")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Atvfflog {
    
    @Id
    @Column(name = "LOUSER", length = 10)
    private String louser;
    
    @Column(name = "LOFECHA")
    private LocalDate lofecha;

}
