package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Desaparecido;
import com.digitalatmosphere.ausys.repositories.IDesaparecidoRepo;

@Service
public class DesaparecidoServiceImpl implements IDesaparecidoService {
	
	@Autowired
	public IDesaparecidoRepo desaparecidoRepo;

	@Override
	public List<Desaparecido> findALL() throws DataAccessException {
		return desaparecidoRepo.findAll();
	}

	@Override
	public void save(Desaparecido desaparecido) throws DataAccessException {
		desaparecidoRepo.save(desaparecido);
		
	}

	@Override
	public Desaparecido findOne(String id_desaparecido) throws DataAccessException {
		return desaparecidoRepo.getById(id_desaparecido);
	}

	@Override
	public void delete(String id_desaparecido) throws DataAccessException {
		Desaparecido desaparecido = desaparecidoRepo.getById(id_desaparecido);
		desaparecidoRepo.delete(desaparecido);
		
	}

}
