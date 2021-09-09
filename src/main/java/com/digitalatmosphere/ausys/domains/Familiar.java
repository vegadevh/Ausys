package com.digitalatmosphere.ausys.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(schema="public", name="FAMILIARES")
public class Familiar {
	
	@Id
	@Column(name="id_familiar")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_familiar;
	
	@NotEmpty
	@Column(name="nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name="contacto")
	private String contacto;

	public Integer getId_familiar() {
		return id_familiar;
	}

	public void setId_familiar(Integer id_familiar) {
		this.id_familiar = id_familiar;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	
	

}
