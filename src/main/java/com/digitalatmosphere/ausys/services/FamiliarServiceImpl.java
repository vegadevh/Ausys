package com.digitalatmosphere.ausys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Familiar;
import com.digitalatmosphere.ausys.repositories.IFamiliarRepo;

@Service
public class FamiliarServiceImpl implements IFamiliarService {
	
	@Autowired
	public IFamiliarRepo familiarRepo;

	@Override
	public void save(Familiar familiar) throws DataAccessException {
		familiarRepo.save(familiar);
	}

//	@Override
//	public Integer findLastId() throws DataAccessException {
//		return familiarRepo.returnId();
//	}

}
