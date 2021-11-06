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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema="public", name="DESA_PERI")
public class DesaPeri {

	@Id
	@Column(name="id_desaperi")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_desaperi;
	
	@NotEmpty
	@Size(message="El campo debe tener como maximo 80 caracteres",max=80)
	@Column(name="tipo_de_caso")
	private String tipo_de_caso;
	
	@NotEmpty
	@Column(name="nombre")
	@Size(message="El campo debe tener como maximo 30 caracteres",max=30)
	private String nombre;
	
	@NotEmpty
	@Column(name="apellido")
	@Size(message="El campo debe tener como maximo 30 caracteres",max=30)
	private String apellido;
	
	@NotEmpty
	@Column(name="direccion")
	private String direccion;
	
	@NotEmpty
	@Column(name="sexo")
	private String sexo;
	
	@NotEmpty
	@Column(name="informacion_adicional")
	private String informacion_adicional;
	
	@Column(name="DUI")
	private String Dui;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Column(name="fecha_registro")
	private Date fecha_registro;
	
	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_desaparecido")
	private Desaparecido desaparecido;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_peritaje")
	private Peritaje peritaje;
	
	@Transient
	private String id_desaparecido;
	
	@Transient
	private String id_peritaje;

	public DesaPeri() {
	}

	public Integer getId_desaperi() {
		return id_desaperi;
	}

	public void setId_desaperi(Integer id_desaperi) {
		this.id_desaperi = id_desaperi;
	}

	public String getTipo_de_caso() {
		return tipo_de_caso;
	}

	public void setTipo_de_caso(String tipo_de_caso) {
		this.tipo_de_caso = tipo_de_caso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getInformacion_adicional() {
		return informacion_adicional;
	}

	public void setInformacion_adicional(String informacion_adicional) {
		this.informacion_adicional = informacion_adicional;
	}

	public String getDui() {
		return Dui;
	}

	public void setDui(String dui) {
		Dui = dui;
	}

	public Desaparecido getDesaparecido() {
		return desaparecido;
	}

	public void setDesaparecido(Desaparecido desaparecido) {
		this.desaparecido = desaparecido;
	}

	public Peritaje getPeritaje() {
		return peritaje;
	}

	public void setPeritaje(Peritaje peritaje) {
		this.peritaje = peritaje;
	}

	public String getId_desaparecido() {
		return id_desaparecido;
	}

	public void setId_desaparecido(String id_desaparecido) {
		this.id_desaparecido = id_desaparecido;
	}

	public String getId_peritaje() {
		return id_peritaje;
	}

	public void setId_peritaje(String id_peritaje) {
		this.id_peritaje = id_peritaje;
	}
	
	
	
}
