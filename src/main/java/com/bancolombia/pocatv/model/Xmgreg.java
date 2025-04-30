package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "xmgreg")
public class Xmgreg {

  // Identificador único para cada registro, se incrementa automáticamente
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Código de la Región de Administración (ej. código breve), es obligatorio
  @Column(name = "xmcdmr", nullable = false, length = 10)
  private String xmcdmr;

  // Nombre de la Región de Administración, es obligatorio
  @Column(name = "xmnmr", nullable = false, length = 100)
  private String xmnmr;

  // Descripción adicional de la Región de Administración, no es obligatorio
  @Column(name = "xmsnmr", length = 255)
  private String xmsnmr;

 }
