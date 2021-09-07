package com.digitalatmosphere.ausys.domains;

//import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(schema="public", name="PERITAJES")
public class Peritaje {

	@Id
	@Column(name="id_peritaje")
	@NotEmpty
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id_peritaje;
	
	@Column(name="identificado")
	private Boolean identificado;
	
	@NotNull
	@Min(0)
	@Max(110)
	@Column(name="edad_estimada")
	private Integer edad_estimada;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_division")
	private Division division;
	
	@Transient
	private Integer id_division;

	public String getId_peritaje() {
		return id_peritaje;
	}

	public void setId_peritaje(String id_peritaje) {
		this.id_peritaje = id_peritaje;
	}

	public Boolean getIdentificado() {
		return identificado;
	}

	public void setIdentificado(Boolean identificado) {
		this.identificado = identificado;
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
