package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Departamento;

public interface IDepartamentoService {
	
	public List<Departamento> findAll() throws DataAccessException;
	
	public void save(Departamento departamento) throws DataAccessException;

}
