package com.digitalatmosphere.ausys.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.digitalatmosphere.ausys.domains.Foto;

public interface IFotoRepo extends JpaRepository<Foto, Integer> {
	
	//FOTOS DESAPARECIDOS
	@Query(nativeQuery=true, value="SELECT fotos.foto\r\n"
			+ "FROM public.fotos INNER JOIN public.desaparecidos ON fotos.id_desaparecido = desaparecidos.id_desaparecido\r\n"
			+ "WHERE desaparecidos.id_desaparecido = :id ;")
	public List<Object[]> fotosDesaparecido(String id) throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT fotos.foto\r\n"
			+ "FROM public.fotos INNER JOIN public.peritajes ON fotos.id_peritaje = peritajes.id_peritaje\r\n"
			+ "WHERE peritajes.id_peritaje = :id ;")
	public List<Object[]> fotosPeritaje(String id) throws DataAccessException;
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery=true, value="DELETE FROM public.fotos\r\n"
			+ "	WHERE id_peritaje = :id_peritaje ;")
	public void eliminarFotosPeritaje(String id_peritaje) throws DataAccessException;
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery=true, value="DELETE FROM public.fotos\r\n"
			+ "	WHERE id_desaparecido = :id_desaparecido ;")
	public void eliminarFotosDesaparecido(String id_desaparecido) throws DataAccessException;

}
