package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.digitalatmosphere.ausys.domains.Usuario;
import com.digitalatmosphere.ausys.repositories.IUsuarioRepo;

public class UsuarioServiceImpl implements IUsuarioService{
	
	@Autowired
	public IUsuarioRepo usuarioRepo;

	@Override
	public List<Usuario> findALL() throws DataAccessException {
		return usuarioRepo.findAll();
	}

	@Override
	public void save(Usuario usuario) throws DataAccessException {
		usuarioRepo.save(usuario);
		
	}

	@Override
	public void delete(Integer id_usuario) throws DataAccessException {
		Usuario usuario = usuarioRepo.getById(id_usuario);
		usuarioRepo.delete(usuario);
	}

	@Override
	public Usuario findOne(Integer id_usuario) throws DataAccessException {
		return usuarioRepo.getById(id_usuario);
	}

}
