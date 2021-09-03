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
@Table(schema="public", name="PERITAJES")
public class Peritaje {

	@Id
	@Column(name="id_peritaje")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_peritaje;
	
	@Column(name="identificado")
	private Boolean identificado;
	
	@Column(name="es_menor")
	private Boolean es_menor;
	
	@Column(name="edad_estimada")
	private Integer edad_estimada;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_division")
	private Division division;
	
	@Transient
	private Integer id_division;

	public Integer getId_peritaje() {
		return id_peritaje;
	}

	public void setId_peritaje(Integer id_peritaje) {
		this.id_peritaje = id_peritaje;
	}

	public Boolean getIdentificado() {
		return identificado;
	}

	public void setIdentificado(Boolean identificado) {
		this.identificado = identificado;
	}

	public Boolean getEs_menor() {
		return es_menor;
	}

	public void setEs_menor(Boolean es_menor) {
		this.es_menor = es_menor;
	}

	public Integer getEdad_estimada() {
		return edad_estimada;
	}

	public void setEdad_estimada(Integer edad_estimada) {
		this.edad_estimada = edad_estimada;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Integer getId_division() {
		return id_division;
	}

	public void setId_division(Integer id_division) {
		this.id_division = id_division;
	}
	
	
}
