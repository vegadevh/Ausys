package com.digitalatmosphere.ausys.repositories;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	//VER REGISTROS INDIVIDUALES
	@Query(nativeQuery=true, value="SELECT desaparecidos.id_desaparecido, desaparecidos.fecha_nacimiento, desaparecidos.nombre_familiar, desaparecidos.contacto_familiar, desaparecidos.id_division, desa_peri.direccion, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.sexo, desa_peri.informacion_adicional, desa_peri.dui, desa_peri.fecha_registro, desa_peri.id_desaperi\r\n"
			+ "FROM public.desaparecidos INNER JOIN public.desa_peri ON desaparecidos.id_desaparecido = desa_peri.id_desaparecido\r\n"
			+ "WHERE desaparecidos.id_desaparecido = :id_desaparecido AND desa_peri.id_desaperi = :id_desaperi ;")
	public List<Object[]> verRegistroDesaparecido(String id_desaparecido, Integer id_desaperi) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT desaparecidos.id_desaparecido, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro, desa_peri.id_desaperi\r\n"
			+ "FROM public.desaparecidos \r\n"
			+ "INNER JOIN public.desa_peri ON desaparecidos.id_desaparecido = desa_peri.id_desaparecido\r\n"
			+ "WHERE desa_peri.nombre LIKE %:nombre% OR desa_peri.apellido LIKE %:apellido% ")
	public List<Object[]> PeritajesXDesaparecidos(String nombre, String apellido) throws DataAccessException;
	
	//FILTROS
	@Query(value="SELECT * FROM desa_peri dp WHERE (lower(dp.nombre) like %:keyword% or lower(dp.apellido) like %:keyword%) and lower(dp.sexo) like %:sexo%", nativeQuery=true)
	public  List<DesaPeri> findByKeyword(@Param("keyword") String keyword,  String sexo);
	
	@Query(value="SELECT * FROM desa_peri dp WHERE (lower(dp.nombre) like %:keyword% or lower(dp.apellido) like %:keyword%) and ( lower(dp.sexo) like %:sexo%) and (dp.tipo_de_caso = :type) ;", nativeQuery=true)
	public  List<DesaPeri> findByKeywordAndtipe(@Param("keyword") String keyword,@Param("type") String type, String sexo);
	
	//CHARTS
	@Query(nativeQuery=true, value="SELECT SUM(CASE WHEN sexo = 'Mujer' then 1 else 0 end) as mujer,\r\n"
			+ "SUM(CASE WHEN sexo = 'Hombre' then 1 else 0 end) as hombre\r\n"
			+ "FROM public.desa_peri WHERE fecha_registro BETWEEN :inicio AND :fin")
	public List<Object[]> HombresMujeresPorFecha(Date inicio, Date fin) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT SUM(CASE WHEN tipo_de_caso = 'Análisis toxicólogico' then 1 else 0 end) as caso1,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Análisis toxicólogico Medio ambientales' then 1 else 0 end) as caso2,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Asistencia a vistas públicas' then 1 else 0 end) as caso3,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Examenes odontológicos' then 1 else 0 end) as caso4,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Desaparecido' then 1 else 0 end) as caso5\r\n"
			+ "FROM public.desa_peri")
	public List<Object[]> cantidadPorCasos() throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT SUM(CASE WHEN tipo_de_caso = 'Análisis toxicólogico' then 1 else 0 end) as caso1,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Análisis toxicólogico Medio ambientales' then 1 else 0 end) as caso2,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Asistencia a vistas públicas' then 1 else 0 end) as caso3,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Examenes odontológicos' then 1 else 0 end) as caso4,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Desaparecido' then 1 else 0 end) as caso5\r\n"
			+ "FROM public.desa_peri\r\n"
			+ "WHERE fecha_registro BETWEEN :inicio AND :fin")
	public List<Object[]> cantidadPorCasosRango(Date inicio, Date fin) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT SUM(CASE WHEN sexo = 'Mujer' then 1 else 0 end) as mujer,\r\n"
			+ "SUM(CASE WHEN sexo = 'Hombre' then 1 else 0 end) as hombre\r\n"
			+ "FROM public.desa_peri WHERE tipo_de_caso = :caso")
	public List<Object[]> HombresMujeresPorCaso(String caso) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT COALESCE(SUM(CASE WHEN sexo = 'Mujer' then 1 else 0 end), 0) as mujer,\r\n"
			+ "COALESCE (SUM(CASE WHEN sexo = 'Hombre' then 1 else 0 end),0) as hombre\r\n"
			+ "FROM public.desa_peri WHERE tipo_de_caso = :caso AND fecha_registro BETWEEN :inicio AND :fin")
	public List<Object[]> HombresMujeresPorCasoYRango(String caso, Date inicio, Date fin) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT SUM(CASE WHEN tipo_de_caso = 'Análisis toxicólogico' then 1 else 0 end) as caso1,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Análisis toxicólogico Medio ambientales' then 1 else 0 end) as caso2,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Asistencia a vistas públicas' then 1 else 0 end) as caso3,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Examenes odontológicos' then 1 else 0 end) as caso4,\r\n"
			+ "SUM(CASE WHEN tipo_de_caso = 'Desaparecido' then 1 else 0 end) as caso5\r\n"
			+ "FROM public.desa_peri WHERE sexo = :sexo")
	public List<Object[]> cantidadPorCasosSexo(String sexo) throws DataAccessException;
	
}
