package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "atvffcep")
@Data
public class Atvffcep {

	@Id
	@Column(name = "cpmes", nullable = false)
	private Integer cpmes;

	@Column(name = "cpano", nullable = false)
	private Integer cpano;

	@Column(name = "cpcumpli", nullable = false)
	private Integer cpcumpli;

	@Column(name = "cpcalid", nullable = false)
	private Integer cpcalid;

	@Column(name = "cpcopr", nullable = false, length = 2)
	private String cpcopr;

	@Column(name = "cpcodo", nullable = false, length = 3)
	private String cpcodo;

	@Column(name = "cpdsdo", nullable = false, length = 25)
	private String cpdsdo;
}
