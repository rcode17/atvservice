package com.bancolombia.pocatv.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AtvffarqId implements Serializable {
	   private static final long serialVersionUID = 1L;


	    private Integer aqcdsu;
	    private String aqcopr;
	    private String aqcodo;
	    private String aqfear;

}
