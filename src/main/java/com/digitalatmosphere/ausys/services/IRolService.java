package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Rol;


public interface IRolService {
	
	public List<Rol> findALL() throws DataAccessException;
	
}
