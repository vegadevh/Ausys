package com.digitalatmosphere.ausys.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalatmosphere.ausys.domains.Usuario;
import com.digitalatmosphere.ausys.repositories.IUsuarioRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	IUsuarioRepo userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <Usuario> user = userRepository.findyByUserName(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("No se encontro: " + username));
		
		
		return user.map(MyUserDetails::new).get();
		
		
		
	}

}
