package com.digitalatmosphere.ausys.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(schema="public", name="DIVISIONES")
public class Division {
	
	@Id
	@Column(name="id_division")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_division;
	
	@Column(name="division")
	private String division;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_municipio")
	private Municipio municipio;
	
	@Transient
	private Integer id_municipio;

	public Integer getId_division() {
		return id_division;
	}

	public void setId_division(Integer id_division) {
		this.id_division = id_division;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Integer getId_municipio() {
		return id_municipio;
	}

	public void setId_municipio(Integer id_municipio) {
		this.id_municipio = id_municipio;
	}
	
	

}
