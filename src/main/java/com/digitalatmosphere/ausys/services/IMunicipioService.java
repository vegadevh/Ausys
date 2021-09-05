package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Municipio;

public interface IMunicipioService {
	
	public List<Municipio> findAll() throws DataAccessException;
	
	public void save(Municipio municipio) throws DataAccessException;

}
