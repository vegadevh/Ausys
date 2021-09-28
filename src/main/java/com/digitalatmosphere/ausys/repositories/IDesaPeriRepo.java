package com.digitalatmosphere.ausys.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalatmosphere.ausys.domains.DesaPeri;

public interface IDesaPeriRepo extends JpaRepository<DesaPeri, Integer>{

	@Query(nativeQuery=true, value="SELECT peritajes.id_peritaje, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro, desa_peri.id_desaperi \r\n"
			+ "FROM public.peritajes INNER JOIN public.desa_peri ON peritajes.id_peritaje = desa_peri.id_peritaje;")
	public List<Object[]> findAllPeritajes() throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT desaparecidos.id_desaparecido, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro, desa_peri.id_desaperi \r\n"
			+ "FROM public.desaparecidos INNER JOIN public.desa_peri ON desaparecidos.id_desaparecido = desa_peri.id_desaparecido;")
	public List<Object[]> findAllDesaparecidos() throws DataAccessException;
	
	//PERITAJES
	@Query(nativeQuery=true, value="SELECT peritajes.id_peritaje, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro, desa_peri.id_desaperi\r\n"
			+ "FROM public.peritajes INNER JOIN public.desa_peri ON peritajes.id_peritaje = desa_peri.id_peritaje\r\n"
			+ "WHERE desa_peri.nombre = :nombre ;")
	public List<Object[]> buscarNombrePeritaje(String nombre) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT peritajes.id_peritaje, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro, desa_peri.id_desaperi\r\n"
			+ "FROM public.peritajes INNER JOIN public.desa_peri ON peritajes.id_peritaje = desa_peri.id_peritaje\r\n"
			+ "WHERE peritajes.id_peritaje = :id ;")
	public List<Object[]> buscarIdPeritaje(String id) throws DataAccessException;
	
	//VER REGISTROS INDIVIDUALES
	@Query(nativeQuery=true, value="SELECT peritajes.id_peritaje, peritajes.identificado, peritajes.edad_estimada, peritajes.id_division, desa_peri.direccion, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.sexo, desa_peri.informacion_adicional, desa_peri.dui, desa_peri.fecha_registro, desa_peri.id_desaperi\r\n"
			+ "FROM public.peritajes INNER JOIN public.desa_peri ON peritajes.id_peritaje = desa_peri.id_peritaje\r\n"
			+ "WHERE peritajes.id_peritaje = :id_peritaje AND desa_peri.id_desaperi = :id_desaperi ;")
	public List<Object[]> verRegistroPeritaje(String id_peritaje, Integer id_desaperi) throws DataAccessException;
	
	//DESAPARECIDOS
	@Query(nativeQuery=true, value="SELECT desaparecidos.id_desaparecido, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro, desa_peri.id_desaperi\r\n"
			+ "FROM public.desaparecidos INNER JOIN public.desa_peri ON desaparecidos.id_desaparecido = desa_peri.id_desaparecido\r\n"
			+ "WHERE desa_peri.nombre = :nombre ;")
	public List<Object[]> buscarNombreDesaparecido(String nombre) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT desaparecidos.id_desaparecido, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro, desa_peri.id_desaperi\r\n"
			+ "FROM public.desaparecidos INNER JOIN public.desa_peri ON desaparecidos.id_desaparecido = desa_peri.id_desaparecido\r\n"
			+ "WHERE desaparecidos.id_desaparecido = :id ;")
	public List<Object[]> buscarIdDesaparecido(String id) throws DataAccessException;
	
}
