package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "atvffmesal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AtvffmesalId.class)  // Añadir esta anotación
public class Atvffmesal {
    
    @Id
    @Column(name = "sdeofic", length = 3, nullable = false)
    private String sdeofic;
    
    @Id
    @Column(name = "sdtdoc", length = 3, nullable = false)
    private String sdtdoc;
    
    @Column(name = "sdfech", length = 8, nullable = false)
    private String sdfech;
    
    @Column(name = "sdtpro", length = 2, nullable = false)
    private String sdtpro;
    
    @Column(name = "sddisp", columnDefinition = "NUMERIC(15,2)")
    private BigDecimal sddisp;
    
    @Column(name = "sdeofco", length = 5, nullable = false)
    private String sdeofco;
    
    @Column(name = "sdusu1", length = 2, nullable = false)
    private String sdusu1;
    
    @Column(name = "sdusu2", length = 20, nullable = false)
    private String sdusu2;
    
    @Column(name = "sdusu3", length = 20, nullable = false)
    private String sdusu3;
    
    @Column(name = "sdusu4", columnDefinition = "NUMERIC(15,2)")
    private BigDecimal sdusu4;
    
    @Column(name = "sdusu5", columnDefinition = "NUMERIC(15,2)")
    private BigDecimal sdusu5;
}