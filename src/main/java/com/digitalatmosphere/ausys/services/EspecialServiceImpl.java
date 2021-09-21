package com.digitalatmosphere.ausys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Especial;
import com.digitalatmosphere.ausys.repositories.IEspecialRepo;

public class EspecialServiceImpl implements IEspecialService {
	
	@Autowired
	public IEspecialRepo especialRepo;

	@Override
	public void save(Especial especial) throws DataAccessException {
		especialRepo.save(especial);
	}

}
