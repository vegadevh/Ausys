package com.digitalatmosphere.ausys.domains;

import java.util.Date;

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
@Table(schema="public", name="DESAPARECIDOS")
public class Desaparecido {
	
	@Id
	@Column(name="id_desaparecido")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_desaparecido;
	
	@Column(name="edad")
	private Integer edad;
	
	@Column(name="fecha_nacimiento")
	private Date fecha_nacimiento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_division")
	private Division division;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_familiar")
	private Familiar familiar;
	
	@Transient
	private Integer id_division;
	
	@Transient
	private Integer id_familiar;

	public Integer getId_desaparecido() {
		return id_desaparecido;
	}

	public void setId_desaparecido(Integer id_desaparecido) {
		this.id_desaparecido = id_desaparecido;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
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

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public Integer getId_division() {
		return id_division;
	}

	public void setId_division(Integer id_division) {
		this.id_division = id_division;
	}

	public Integer getId_familiar() {
		return id_familiar;
	}

	public void setId_familiar(Integer id_familiar) {
		this.id_familiar = id_familiar;
	}
	
	

}