package com.bancolombia.pocatv.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atvffoas")
public class Atvffoas {

	@EmbeddedId
	private AtvffoasId id;

	@Column(name = "oacumpli", nullable = false)
	private Integer oacumpli;

	@Column(name = "oacalid", nullable = false)
	private Integer oacalid;

	@Column(name = "oanombre", nullable = false, length = 30)
	private String oanombre;

}
