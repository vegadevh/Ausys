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
import javax.validation.constraints.Size;

@Entity
@Table(schema="public", name="PERITAJES")
public class Peritaje {

	@Id
	@Column(name="id_peritaje")
	@NotEmpty
	@Size(message="El campo debe tener como maximo 15 caracteres",max=15)
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
	@JoinColumn(name="id_municipio")
	private Municipio municipio;
	
	@Transient
	private Integer id_municipio;

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

	public String getEstadoDelegate() {
		if(this.identificado == null) return "";
		else {
			return identificado == true ? "Identificado" : "No identificado";
		}
	}
	
}
