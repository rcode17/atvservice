package com.bancolombia.pocatv.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AtvffoasId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "oames")
    private Integer oames;
    
    @Column(name = "oaano")
    private Integer oaano;
    
    @Column(name = "oaxnnmky")
    private Integer oaxnnmky;
}