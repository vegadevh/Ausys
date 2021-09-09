package com.digitalatmosphere.ausys.services;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Familiar;

public interface IFamiliarService {
	
	public void save(Familiar familiar) throws DataAccessException;
	
//	public Integer findLastId() throws DataAccessException;

}
