package com.digitalatmosphere.ausys.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalatmosphere.ausys.domains.DesaPeri;

public interface IDesaPeriRepo extends JpaRepository<DesaPeri, Integer>{

	@Query(nativeQuery=true, value="SELECT peritajes.id_peritaje, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro \r\n"
			+ "FROM public.peritajes INNER JOIN public.desa_peri ON peritajes.id_peritaje = desa_peri.id_peritaje;")
	public List<Object[]> findAllPeritajes() throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT desaparecidos.id_desaparecido, desa_peri.tipo_de_caso, desa_peri.nombre, desa_peri.apellido, desa_peri.fecha_registro \r\n"
			+ "FROM public.desaparecidos INNER JOIN public.desa_peri ON desaparecidos.id_desaparecido = desa_peri.id_desaparecido;")
	public List<Object[]> findAllDesaparecidos() throws DataAccessException;
	
}
