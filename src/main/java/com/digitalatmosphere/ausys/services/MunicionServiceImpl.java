package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Municipio;
import com.digitalatmosphere.ausys.repositories.IMunicipioRepo;

@Service
public class MunicionServiceImpl implements IMunicipioService {
	
	@Autowired
	public IMunicipioRepo municipioRepo;

	@Override
	public List<Municipio> findAll() throws DataAccessException {
		return municipioRepo.findAll();
	}

	@Override
	public void save(Municipio municipio) throws DataAccessException {
		municipioRepo.save(municipio);

	}

}
