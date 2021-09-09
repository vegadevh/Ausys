package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Peritaje;
import com.digitalatmosphere.ausys.repositories.IPeritajeRepo;

@Service
public class PeritajeServiceImpl implements IPeritajeService{

	@Autowired
	public IPeritajeRepo peritajeRepo;
	
	@Override
	public List<Peritaje> findALL() throws DataAccessException {
		return peritajeRepo.findAll();
	}

	@Override
	public void save(Peritaje peritaje) throws DataAccessException {
		peritajeRepo.save(peritaje);
		
	}

	@Override
	public Peritaje findOne(String id_peritaje) throws DataAccessException {
		return peritajeRepo.getById(id_peritaje);
	}

}
