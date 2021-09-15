package com.digitalatmosphere.ausys.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalatmosphere.ausys.domains.DesaPeri;

public interface IDesaPeriRepo extends JpaRepository<DesaPeri, Integer>{

	@Query(nativeQuery=true, value="SELECT id_desaperi, tipo_de_caso, nombre, apellido, direccion, sexo, informacion_adicional, dui, fecha_registro, id_desaparecido, id_peritaje\r\n"
			+ "	FROM public.desa_peri WHERE id_peritaje IS NOT NULL;")
	public List<DesaPeri> findAllPeritajes() throws DataAccessException;
	
	@Query(nativeQuery=true, value="SELECT id_desaperi, tipo_de_caso, nombre, apellido, direccion, sexo, informacion_adicional, dui, fecha_registro, id_desaparecido, id_peritaje\r\n"
			+ "	FROM public.desa_peri WHERE id_desaparecido IS NOT NULL;")
	public List<DesaPeri> findAllDesaparecidos() throws DataAccessException;
}
