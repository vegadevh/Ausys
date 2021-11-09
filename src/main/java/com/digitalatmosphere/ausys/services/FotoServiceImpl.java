package com.digitalatmosphere.ausys.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Foto;
import com.digitalatmosphere.ausys.dto.fotografiaDTO;
import com.digitalatmosphere.ausys.repositories.IFotoRepo;
import com.digitalatmosphere.ausys.repositories.IPeritajeRepo;

@Service
public class FotoServiceImpl implements IFotoService {
	
	@Autowired
	public IFotoRepo fotoRepo;
	
	@Autowired
	public IPeritajeRepo peritajeRepo;
	
	@Override
	public void save(Foto foto) throws DataAccessException {
		fotoRepo.save(foto);
		
	}

	@Override
	public List<fotografiaDTO> fotosDesaparecido(String id) throws DataAccessException {
		List<fotografiaDTO> fotos = fotoRepo.fotosDesaparecido(id).stream().map(obj -> {
			fotografiaDTO f = new fotografiaDTO();
			f.setFilename(obj[0].toString());
			return f;
		}).collect(Collectors.toList());
		return fotos;
	}

	@Override
	public List<fotografiaDTO> fotosPeritaje(String id) throws DataAccessException {
		List<fotografiaDTO> fotos = fotoRepo.fotosPeritaje(id).stream().map(obj -> {
			fotografiaDTO f = new fotografiaDTO();
			f.setFilename(obj[0].toString());
			return f;
		}).collect(Collectors.toList());
		return fotos;
	}

	@Override
	public void eliminarFotosPeritaje(String id_peritaje) throws DataAccessException {
		fotoRepo.eliminarFotosPeritaje(id_peritaje);
		return;
	}

	@Override
	public void eliminarFotosDesaparecido(String id_desaparecido) throws DataAccessException {
		fotoRepo.eliminarFotosDesaparecido(id_desaparecido);
		return;
		
	}

}
