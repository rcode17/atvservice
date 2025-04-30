package com.bancolombia.pocatv.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "atvffchars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atvffchars {
    
    @Id
    @Column(name = "plano", length = 108)
    private String plano;
}