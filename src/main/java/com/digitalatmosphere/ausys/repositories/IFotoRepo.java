package com.digitalatmosphere.ausys.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalatmosphere.ausys.domains.Foto;

public interface IFotoRepo extends JpaRepository<Foto, Integer> {
	
	//FOTOS DESAPARECIDOS
	@Query(nativeQuery=true, value="SELECT fotos.foto\r\n"
			+ "FROM public.fotos INNER JOIN public.desaparecidos ON fotos.id_desaparecido = desaparecidos.id_desaparecido\r\n"
			+ "WHERE desaparecidos.id_desaparecido = :id ;")
	public List<Object[]> fotosDesaparecido(String id) throws DataAccessException;

}
