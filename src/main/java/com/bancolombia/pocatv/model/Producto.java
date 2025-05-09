package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_producto", nullable = false)
    private String codigoProducto;

    @Column(name = "codigo_documento", nullable = false)
    private String codigoDocumento;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;
}