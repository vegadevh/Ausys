package com.digitalatmosphere.ausys.dto;

public class RegistroDTO {
	
	private String id_registro;
	private String identificado;
	private String edad_estimada;
	private String municipio;
	private String direccion;
	private String tipo_de_caso;
	private String nombre;
	private String apellido;
	private String sexo;
	private String informacion_adicional;
	private String dui;
	private String fecha_registro;
	private String id;
	
	private String fecha_nacimiento;
	private String nombre_familiar;
	private String contacto_familiar;
	
	
	
	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_registro() {
		return id_registro;
	}
	public void setId_registro(String id_registro) {
		this.id_registro = id_registro;
	}
	public String getIdentificado() {
		return identificado;
	}
	public void setIdentificado(String identfiicado) {
		this.identificado = identfiicado;
	}
	public String getEdad_estimada() {
		return edad_estimada;
	}
	public void setEdad_estimada(String edad_estimada) {
		this.edad_estimada = edad_estimada;
	}
	
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
		return dui;
	}
	public void setDui(String dui) {
		this.dui = dui;
	}
	public String getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	
}
