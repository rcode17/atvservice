package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atvffcaa")
public class Atvffcaa {

 @Id
 @Column(name = "aaanos", nullable = false)
 private Integer aaanos; // Años

 @Column(name = "aaenero", nullable = false)
 private Integer aaenero; // Enero

 @Column(name = "aafeb", nullable = false)
 private Integer aafeb; // Febrero

 @Column(name = "aamarzo", nullable = false)
 private Integer aamarzo; // Marzo

 @Column(name = "aaabril", nullable = false)
 private Integer aaabril; // Abril

 @Column(name = "aamayo", nullable = false)
 private Integer aamayo; // Mayo

 @Column(name = "aajunio", nullable = false)
 private Integer aajunio; // Junio

 @Column(name = "aajulio", nullable = false)
 private Integer aajulio; // Julio

 @Column(name = "aaagosto", nullable = false)
 private Integer aaagosto; // Agosto

 @Column(name = "aasep", nullable = false)
 private Integer aasep; // Septiembre

 @Column(name = "aaoctubre", nullable = false)
 private Integer aaoctubre; // Octubre

 @Column(name = "aanov", nullable = false)
 private Integer aanov; // Noviembre

 @Column(name = "aadic", nullable = false)
 private Integer aadic; // Diciembre

 @Column(name = "aanombre", nullable = false, length = 25)
 private String aanombre; // Nombre

 @Column(name = "aaregion", nullable = false, length = 6)
 private String aaregion; // Región

 @Column(name = "aaproma", nullable = false)
 private Integer aaproma; // Promedio
}