package com.bancolombia.pocatv.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "Atvfftgesu")
public class Atvfftgesu {

    @Id
    @Column(name = "gcodco")
    private Integer gcodco;

    @Column(name = "gusuar", length = 10)
    private String gusuar;

    @Column(name = "gfecom")
    private Integer gfecom;

    @Column(name = "ghoras")
    private Integer ghoras;

    @Column(name = "gcomen", length = 240)
    private String gcomen;

    @Column(name = "gsucur")
    private Integer gsucur;

    @Column(name = "gprodu", length = 2)
    private String gprodu;

    @Column(name = "gdocum", length = 3)
    private String gdocum;

    @Column(name = "gfearq")
    private Integer gfearq;

    @Column(name = "gdifer", precision = 15, scale = 2)
    private BigDecimal gdifer;
}
