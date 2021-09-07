package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Peritaje;

public interface IPeritajeService {

	public List<Peritaje> findALL() throws DataAccessException;
	
	public void save(Peritaje peritaje) throws DataAccessException;
	
	public Peritaje findOne(String id_peritaje) throws DataAccessException;
}
