package com.digitalatmosphere.ausys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Usuario;
import com.digitalatmosphere.ausys.repositories.IUsuarioRepo;


@Service
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
	public void delete(String usuario) throws DataAccessException {
		Usuario user = usuarioRepo.getById(usuario);
		usuarioRepo.delete(user);
	}

	@Override
	public Usuario findOne(String usuario) throws DataAccessException {
		return usuarioRepo.findById(usuario).orElse(null);
	}

}
