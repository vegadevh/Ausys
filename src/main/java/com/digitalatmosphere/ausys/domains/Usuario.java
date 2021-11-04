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
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(schema="public", name="USUARIOS")
public class Usuario {

	@Id
	@Column(name="id_usuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_usuario;
	
	@NotEmpty
	@Column(name="username")
	@Size(message="Como maximo puede ingresar 50 caracteres.", max=50)
	private String username;
	
	@NotEmpty
	@Column(name="password")
	private String password;
	
	
	@Column(name="enabled_u")
	private Boolean enabled_u;
	
	@NotEmpty
	@Column(name="nombre")
	@Size(message="Como maximo puede ingresar 50 caracteres.", max=50)
	private String nombre;
	
	@NotEmpty
	@Column(name="apellido")
	@Size(message="Como maximo puede ingresar 50 caracteres.", max=50)
	private String apellido;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_rol")
	private Rol rol;
	
	@Transient
	private Integer id_rol;

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled_u() {
		return enabled_u;
	}

	public void setEnabled_u(Boolean enabled_u) {
		this.enabled_u = enabled_u;
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Integer getId_rol() {
		return id_rol;
	}

	public void setId_rol(Integer id_rol) {
		this.id_rol = id_rol;
	}
	
	public String getEstadoDelegate() {
		if(this.enabled_u == null) return "Valor invalido";
		else {
			return enabled_u == true ? "Disponible": "No Disponible";
		}
		
	}
	
}
