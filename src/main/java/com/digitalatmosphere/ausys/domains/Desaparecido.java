package com.digitalatmosphere.ausys.domains;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema="public", name="DESAPARECIDOS")
public class Desaparecido {
	
	@Id
	@Column(name="id_desaparecido")
	@NotEmpty
	@Size(message="El campo debe tener como maximo 15 caracteres",max=15)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id_desaparecido;
	
	@NotNull
	@Past
	@Column(name="fecha_nacimiento")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fecha_nacimiento;
	
	@NotEmpty
	@Column(name="nombre_familiar")
	@Size(message="Como maximo puede ingresar 160 caracteres.", max=160)
	private String nombre_familiar;
	
	@NotEmpty
	@Column(name="contacto_familiar")
	private String contacto_familiar;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_municipio")
	private Municipio municipio;
	
	@Transient
	private Integer id_municipio;

	

	public String getNombre_familiar() {
		return nombre_familiar;
	}

	public void setNombre_familiar(String nombre_familiar) {
		this.nombre_familiar = nombre_familiar;
	}

	public String getContacto_familiar() {
		return contacto_familiar;
	}

	public void setContacto_familiar(String contacto_familiar) {
		this.contacto_familiar = contacto_familiar;
	}

	public String getId_desaparecido() {
		return id_desaparecido;
	}

	public void setId_desaparecido(String id_desaparecido) {
		this.id_desaparecido = id_desaparecido;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
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
