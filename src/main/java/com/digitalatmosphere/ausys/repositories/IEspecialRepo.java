package com.digitalatmosphere.ausys.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.digitalatmosphere.ausys.domains.Especial;

public interface IEspecialRepo extends JpaRepository<Especial, Integer> {

	@Query(nativeQuery=true, value="SELECT se_especiales.especial, se_especiales.descripcion\r\n"
			+ "FROM public.se_especiales INNER JOIN public.desaparecidos ON se_especiales.id_desaparecido = desaparecidos.id_desaparecido\r\n"
			+ "WHERE desaparecidos.id_desaparecido = :id ;")
	public List<Object[]> especialDesaparecido(String id) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT se_especiales.especial, se_especiales.descripcion\r\n"
			+ "FROM public.se_especiales INNER JOIN public.peritajes ON se_especiales.id_peritaje = peritajes.id_peritaje\r\n"
			+ "WHERE peritajes.id_peritaje = :id ;")
	public List<Object[]> especialPeritaje(String id) throws DataAccessException;
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery=true, value="DELETE FROM public.se_especiales\r\n"
			+ "WHERE id_desaparecido = :id_desaparecido ;")
	public void eliminarEspecialDesaparecido(String id_desaparecido) throws DataAccessException;
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery=true, value="DELETE FROM public.se_especiales\r\n"
			+ "WHERE id_peritaje = :id_peritaje ;")
	public void eliminarEspecialPeritaje(String id_peritaje) throws DataAccessException;
}
