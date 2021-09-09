package com.digitalatmosphere.ausys.repositories;


import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalatmosphere.ausys.domains.Familiar;

public interface IFamiliarRepo extends JpaRepository<Familiar, Integer> {
	
//	@Query(nativeQuery=true, value="SELECT currval(pg_get_serial_sequence('public.familiares','id_familiar'));")
//	public Integer returnId() throws DataAccessException;

}
