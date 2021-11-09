package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Especial;
import com.digitalatmosphere.ausys.dto.EspecialDTO;

public interface IEspecialService {

	public void save(Especial especial) throws DataAccessException;
	
	public List<EspecialDTO> especialDesaparecido(String id) throws DataAccessException;
	
	public List<EspecialDTO> especialPeritaje(String id) throws DataAccessException;
	
	public void eliminarEspecialDesaparecido(String id_desaparecido) throws DataAccessException;
	
	public void eliminarEspecialPeritaje(String id_peritaje) throws DataAccessException;
}
