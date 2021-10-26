package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Rol;

import com.digitalatmosphere.ausys.repositories.IRolRepo;

@Service
public class RolServiceImpl implements IRolService{

	@Autowired
	public IRolRepo rolRepo;
	
	
	@Override
	public List<Rol> findALL() throws DataAccessException {
		
		return rolRepo.findAll();
	}

}
