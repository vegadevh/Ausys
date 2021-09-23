package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;

public interface IDesaPeriService {

public List<DesaPeri> findALL() throws DataAccessException;
	
	public void save(DesaPeri desaPeri) throws DataAccessException;
	
	public void delete(Integer id_desaperi) throws DataAccessException;
	
	public Integer saveR(DesaPeri desaPeri) throws DataAccessException;
	
	public DesaPeri findOne(Integer id_desaperi) throws DataAccessException;
	
	public List<DesaPeri> findAll() throws DataAccessException;
	
	public List<PeritajeDTO> findAllPeritajes() throws DataAccessException;
	
	public List<DesaparecidoDTO> findAllDesaparecidos() throws DataAccessException;
	
	public List<PeritajeDTO> buscarNombrePeritaje(String nombre) throws DataAccessException;

	public List<PeritajeDTO> buscarIdPeritaje(String id) throws DataAccessException;
	
	public List<DesaparecidoDTO> buscarNombreDesaparecido(String nombre) throws DataAccessException;
	
	public List<DesaparecidoDTO> buscarIdDesaparecido(String id) throws DataAccessException;
}
