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

import org.hibernate.annotations.Type;

@Entity
@Table(schema="public", name="FOTOS")
public class Foto {
	
	@Id
	@Column(name="id_foto")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_foto;
	
//	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name="foto")
	private byte[] foto;

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

	public Integer getId_foto() {
		return id_foto;
	}

	public void setId_foto(Integer id_foto) {
		this.id_foto = id_foto;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
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
