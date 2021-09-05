package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Departamento;
import com.digitalatmosphere.ausys.repositories.IDepartamentoRepo;

@Service
public class DepartamentoServiceImpl implements IDepartamentoService {
	
	@Autowired
	public IDepartamentoRepo departamentoRepo;

	@Override
	public List<Departamento> findAll() throws DataAccessException {
		return departamentoRepo.findAll();
	}

	@Override
	public void save(Departamento departamento) throws DataAccessException {
		departamentoRepo.save(departamento);

	}

}
