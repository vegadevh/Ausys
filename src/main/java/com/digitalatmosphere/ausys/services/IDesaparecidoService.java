package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Desaparecido;

public interface IDesaparecidoService {
	
	public List<Desaparecido> findALL() throws DataAccessException;
	
	public void save(Desaparecido desaparecido) throws DataAccessException;
	
	public void delete(String id_desaparecido) throws DataAccessException;
	
	public Desaparecido findOne(String id_desaparecido) throws DataAccessException;

}
