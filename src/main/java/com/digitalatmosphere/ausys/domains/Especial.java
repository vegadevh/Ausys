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
import javax.validation.constraints.NotEmpty;

@Entity
@Table(schema="public", name="SE_ESPECIALES")
public class Especial {
	
	@Id
	@Column(name="id_especial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_especial;
	
	@NotEmpty
	@Column(name="especial")
	private String nombre_especial;

	@NotEmpty
	@Column(name="descripcion")
	private String descripcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_peritaje")
	private Peritaje peritaje;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_desaparecido")
	private Desaparecido desaparecido;
	
	@Transient
	private String id_peritaje;
	
	@Transient
	private String id_desaparecido;
	
	public String getNombre_especial() {
		return nombre_especial;
	}

	public void setNombre_especial(String nombre_especial) {
		this.nombre_especial = nombre_especial;
	}

	public Integer getId_especial() {
		return id_especial;
	}

	public void setId_especial(Integer id_especial) {
		this.id_especial = id_especial;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Peritaje getPeritaje() {
		return peritaje;
	}

	public void setPeritaje(Peritaje peritaje) {
		this.peritaje = peritaje;
	}

	public Desaparecido getDesaparecido() {
		return desaparecido;
	}

	public void setDesaparecido(Desaparecido desaparecido) {
		this.desaparecido = desaparecido;
	}

	public String getId_peritaje() {
		return id_peritaje;
	}

	public void setId_peritaje(String id_peritaje) {
		this.id_peritaje = id_peritaje;
	}

	public String getId_desaparecido() {
		return id_desaparecido;
	}

	public void setId_desaparecido(String id_desaparecido) {
		this.id_desaparecido = id_desaparecido;
	}
	
	

}
