package com.digitalatmosphere.ausys.dto;


public class DesaparecidoDTO {
	
	private String id_desaparecido;
	private String tipo_de_caso;
	private String nombre;
	private String apellido;
	private String fecha_registro;
	
	public String getId_desaparecido() {
		return id_desaparecido;
	}
	public void setId_desaparecido(String id_desaparecido) {
		this.id_desaparecido = id_desaparecido;
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
	public String getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	
	
}
