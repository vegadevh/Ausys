package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Division;

public interface IDivisionService {
	
	public List<Division> findAll() throws DataAccessException;
	
	public void save(Division division) throws DataAccessException;

}
