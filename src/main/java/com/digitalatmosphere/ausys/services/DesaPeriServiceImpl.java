package com.digitalatmosphere.ausys.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;
import com.digitalatmosphere.ausys.repositories.IDesaPeriRepo;

@Service
public class DesaPeriServiceImpl implements IDesaPeriService {
	
	@Autowired
	public IDesaPeriRepo desaPeriRepo;

	@Override
	public List<DesaPeri> findALL() throws DataAccessException {
		return desaPeriRepo.findAll();
	}

	@Override
	public void save(DesaPeri desaPeri) throws DataAccessException {
		desaPeriRepo.save(desaPeri);

	}

	@Override
	public DesaPeri findOne(Integer id_desaperi) throws DataAccessException {
		return desaPeriRepo.getById(id_desaperi);
	}
	
	EntityManager entityManager;
	
	public DesaPeriServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

	@Override
	@Transactional
	public Integer saveR(DesaPeri desaPeri) throws DataAccessException {
		entityManager.persist(desaPeri);
        return desaPeri.getId_desaperi();
	}

	@Override
	public List<DesaPeri> findAll() throws DataAccessException {
		return desaPeriRepo.findAll();
	}

	@Override
	public List<PeritajeDTO> findAllPeritajes() throws DataAccessException {
		List<PeritajeDTO> peritajes = desaPeriRepo.findAllPeritajes().stream().map(obj -> {
			PeritajeDTO p = new PeritajeDTO();
			p.setId_peritaje(obj[0].toString());
			p.setTipo_de_caso(obj[1].toString());
			p.setNombre(obj[2].toString());
			p.setApellido(obj[3].toString());
			p.setFecha_registro(obj[4].toString());
			p.setId_desaperi(obj[5].toString());
			return p;
		}).collect(Collectors.toList());
		return peritajes;
	}

	@Override
	public List<DesaparecidoDTO> findAllDesaparecidos() throws DataAccessException {
		List<DesaparecidoDTO> desaparecidos = desaPeriRepo.findAllDesaparecidos().stream().map(obj -> {
			DesaparecidoDTO d = new DesaparecidoDTO();
			d.setId_desaparecido(obj[0].toString());
			d.setTipo_de_caso(obj[1].toString());
			d.setNombre(obj[2].toString());
			d.setApellido(obj[3].toString());
			d.setFecha_registro(obj[4].toString());
			d.setId_desaperi(obj[5].toString());
			return d;
		}).collect(Collectors.toList());
		return desaparecidos;
	}


}
