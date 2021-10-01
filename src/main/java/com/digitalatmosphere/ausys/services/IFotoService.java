package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Foto;
import com.digitalatmosphere.ausys.dto.fotografiaDTO;

public interface IFotoService {
	
	public void save(Foto foto) throws DataAccessException;
	
	public List<fotografiaDTO> fotosDesaparecido(String id) throws DataAccessException;

}
