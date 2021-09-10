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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema="public", name="DESAPARECIDOS")
public class Desaparecido {
	
	@Id
	@Column(name="id_desaparecido")
	@NotEmpty
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id_desaparecido;
	
	@NotNull
	@Past
	@Column(name="fecha_nacimiento")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fecha_nacimiento;
	
	@NotEmpty
	@Column(name="nombre_familiar")
	private String nombre_familiar;
	
	@NotEmpty
	@Column(name="contacto_familiar")
	private String contacto_familiar;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_division")
	private Division division;
	
	@Transient
	private Integer id_division;

	

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
