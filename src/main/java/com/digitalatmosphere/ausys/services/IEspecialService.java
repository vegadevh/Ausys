package com.digitalatmosphere.ausys.services;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Especial;

public interface IEspecialService {

	public void save(Especial especial) throws DataAccessException;
}
