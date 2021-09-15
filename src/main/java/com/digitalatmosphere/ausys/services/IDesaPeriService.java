package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.DesaPeri;

public interface IDesaPeriService {

public List<DesaPeri> findALL() throws DataAccessException;
	
	public void save(DesaPeri desaPeri) throws DataAccessException;
	
	public Integer saveR(DesaPeri desaPeri) throws DataAccessException;
	
	public DesaPeri findOne(Integer id_desaperi) throws DataAccessException;
	
	public List<DesaPeri> findAll() throws DataAccessException;
	
	public List<DesaPeri> findAllPeritajes() throws DataAccessException;
	
	public List<DesaPeri> findAllDesaparecidos() throws DataAccessException;
}
