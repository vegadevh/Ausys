package com.digitalatmosphere.ausys.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Especial;
import com.digitalatmosphere.ausys.dto.EspecialDTO;
import com.digitalatmosphere.ausys.repositories.IEspecialRepo;

@Service
public class EspecialServiceImpl implements IEspecialService {
	
	@Autowired
	public IEspecialRepo especialRepo;

	@Override
	public void save(Especial especial) throws DataAccessException {
		especialRepo.save(especial);
	}

	@Override
	public List<EspecialDTO> especialDesaparecido(String id) throws DataAccessException {
		List<EspecialDTO> especial = especialRepo.especialDesaparecido(id).stream().map(obj -> {
			EspecialDTO e = new EspecialDTO();
			e.setEspecial(obj[0].toString());
			e.setDescripcion(obj[1].toString());
			return e;
		}).collect(Collectors.toList());
		return especial;
	}

	@Override
	public List<EspecialDTO> especialPeritaje(String id) throws DataAccessException {
		List<EspecialDTO> especial = especialRepo.especialPeritaje(id).stream().map(obj -> {
			EspecialDTO e = new EspecialDTO();
			e.setEspecial(obj[0].toString());
			e.setDescripcion(obj[1].toString());
			return e;
		}).collect(Collectors.toList());
		return especial;
	}

}
