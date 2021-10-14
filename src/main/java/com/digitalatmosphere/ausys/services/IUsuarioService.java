package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> findALL() throws DataAccessException;
	
	public void save(Usuario usuario) throws DataAccessException;
	
	public void delete(Integer id_usuario) throws DataAccessException;
	
	public Usuario findOne(Integer id_usuario) throws DataAccessException;

}
