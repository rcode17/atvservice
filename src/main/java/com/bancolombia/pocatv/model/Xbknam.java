package com.bancolombia.pocatv.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "XBKNAM")
public class Xbknam {
	@Id
	@Column(name = "xnnmky", nullable = false, precision = 5)
	private Integer xnnmky; // Clave principal

	@Column(name = "xnclcd", nullable = false, length = 255)
	private String xnclcd; // Descripción del campo XNCLCD

	@Column(name = "xnname", nullable = false, length = 255)
	private String xnname; // Descripción del campo XNNAME

	@Column(name = "xnadd1", nullable = false, length = 255)
	private String xnadd1; // Descripción del campo XNADD1

	@Column(name = "xnadd2", nullable = false, length = 255)
	private String xnadd2; // Descripción del campo XNADD2

	@Column(name = "xncdct", nullable = false, length = 255)
	private String xncdct; // Descripción del campo XNCDCT

	@Column(name = "xncity", nullable = false, length = 255)
	private String xncity; // Descripción del campo XNCITY

	@Column(name = "xnstte", nullable = false, length = 255)
	private String xnstte; // Descripción del campo XNSTTE

	@Column(name = "xnctry", nullable = false, length = 255)
	private String xnctry; // Descripción del campo XNCTRY

	@Column(name = "xnzip", nullable = false, length = 255)
	private String xnzip; // Descripción del campo XNZIP

	@Column(name = "xnnobp", nullable = false, length = 255)
	private String xnnobp; // Descripción del campo XNNOBP

	@Column(name = "xnnofp", nullable = false, length = 255)
	private String xnnofp; // Descripción del campo XNNOFP

	@Column(name = "xnnokp", nullable = false, length = 255)
	private String xnnokp; // Descripción del campo XNNOKP

	@Column(name = "xnnoap", nullable = false, length = 255)
	private String xnnoap; // Descripción del campo XNNOAP

	@Column(name = "xnrttx", nullable = false, length = 255)
	private String xnrttx; // Descripción del campo XNRTTX

	@Column(name = "xntin", nullable = false, length = 255)
	private String xntin; // Descripción del campo XNTIN

	@Column(name = "xnstin", nullable = false, length = 255)
	private String xnstin; // Descripción del campo XNSTIN

	@Column(name = "xnadbr", nullable = false, length = 255)
	private String xnadbr; // Descripción del campo XNADBR

	@Column(name = "xnfrst", nullable = false, length = 255)
	private String xnfrst; // Descripción del campo XNFRST

	@Column(name = "xnatrn", nullable = false, length = 255)
	private String xnatrn; // Descripción del campo XNATRN

	@Column(name = "xnafra", nullable = false, length = 255)
	private String xnafra; // Descripción del campo XNAFRA

	@Column(name = "xnlncd", nullable = false, length = 255)
	private String xnlncd; // Descripción del campo XNLNCD

	@Column(name = "xnvaid", nullable = false, length = 255)
	private String xnvaid; // Descripción del campo XNVAID

	@Column(name = "xntaxr", nullable = false, length = 255)
	private String xntaxr; // Descripción del campo XNTAXR

	@Column(name = "xntaxn", nullable = false, length = 255)
	private String xntaxn; // Descripción del campo XNTAXN

	@Column(name = "xncdon", nullable = false, length = 255)
	private String xncdon; // Descripción del campo XNCDON

	@Column(name = "xnddon", nullable = false, length = 255)
	private String xnddon; // Descripción del campo XNDDON

	@Column(name = "xnsdon", nullable = false, length = 255)
	private String xnsdon; // Descripción del campo XNSDON

	@Column(name = "xnlnon", nullable = false, length = 255)
	private String xnlnon; // Descripción del campo XNLNON

	@Column(name = "xnglon", nullable = false, length = 255)
	private String xnglon; // Descripción del campo XNGLON

	@Column(name = "xniron", nullable = false, length = 255)
	private String xniron; // Descripción del campo XNIRON

	@Column(name = "xnhdpr", nullable = false, length = 255)
	private String xnhdpr; // Descripción del campo XNHDPR

	@Column(name = "xnmcpr", nullable = false, length = 255)
	private String xnmcpr; // Descripción del campo XNMCPR

	@Column(name = "xnmacd", nullable = false, length = 255)
	private String xnmacd; // Descripción del campo XNMACD

	@Column(name = "xncdci", nullable = false, length = 255)
	private String xncdci; // Descripción del campo XNCDCI

	@Column(name = "xncdmr", nullable = false, length = 255)
	private String xncdmr; // Descripción del campo XNCDMR

	@Column(name = "xncdst", nullable = false, length = 255)
	private String xncdst; // Descripción del campo XNCDST

	@Column(name = "xncdu1", nullable = false, length = 255)
	private String xncdu1; // Descripción del campo XNCDU1

	@Column(name = "xncdu2", nullable = false, length = 255)
	private String xncdu2; // Descripción del campo XNCDU2

	@Column(name = "xncdu3", nullable = false, length = 255)
	private String xncdu3; // Descripción del campo XNCDU3

	@Column(name = "xnendt", nullable = false, length = 255)
	private String xnendt; // Descripción del campo XNENDT

	@Column(name = "xnentm", nullable = false, length = 255)
	private String xnentm; // Descripción del campo XNENTM

	@Column(name = "xnenus", nullable = false, length = 255)
	private String xnenus; // Descripción del campo XNENUS

	@Column(name = "xnenws", nullable = false, length = 255)
	private String xnenws; // Descripción del campo XNENWS

	@Column(name = "xndtlm", nullable = false, length = 255)
	private String xndtlm; // Descripción del campo XNDTLM

}
