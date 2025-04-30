package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "atvffchsal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AtvffchsalId.class)
public class Atvffchsal {
    
    @Column(name = "sdfech", length = 8, nullable = false)
    private String sdfech; // FECHA SALDO
    
    @Column(name = "sdtpro", length = 2, nullable = false)
    private String sdtpro; // PRODUCTO
    
    @Column(name = "sdtdoc", length = 3, nullable = false)
    @Id
    private String sdtdoc; // DOCUMENTO
    
    @Column(name = "sddisp", precision = 15, scale = 2, nullable = false)
    private BigDecimal sddisp; // SALDO DISP.
    
    @Column(name = "sdeofic", length = 3, nullable = false)
    @Id
    private String sdeofic; // OF. FISICA
    
    @Column(name = "sdeofco", length = 5, nullable = false)
    private String sdeofco; // OF. CONTABLE
    
    @Column(name = "sdusu1", length = 2, nullable = false)
    private String sdusu1; // USUARIO 1
    
    @Column(name = "sdusu2", length = 20, nullable = false)
    private String sdusu2; // USUARIO 2
    
    @Column(name = "sdusu3", length = 20, nullable = false)
    private String sdusu3; // USUARIO 3
    
    @Column(name = "sdusu4", precision = 15, scale = 2, nullable = false)
    private BigDecimal sdusu4; // USUARIO 4
    
    @Column(name = "sdusu5", precision = 15, scale = 2, nullable = false)
    private BigDecimal sdusu5; // USUARIO 5
}
