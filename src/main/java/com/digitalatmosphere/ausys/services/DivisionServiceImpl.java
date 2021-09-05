package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Division;
import com.digitalatmosphere.ausys.repositories.IDivisionRepo;

@Service
public class DivisionServiceImpl implements IDivisionService {
	
	@Autowired
	public IDivisionRepo divisionRepo;

	@Override
	public List<Division> findAll() throws DataAccessException {
		return divisionRepo.findAll();
	}

	@Override
	public void save(Division division) throws DataAccessException {
		divisionRepo.save(division);

	}

}
