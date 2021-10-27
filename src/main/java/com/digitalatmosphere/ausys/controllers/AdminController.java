package com.digitalatmosphere.ausys.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.Rol;
import com.digitalatmosphere.ausys.domains.Usuario;
import com.digitalatmosphere.ausys.services.IRolService;
import com.digitalatmosphere.ausys.services.IUsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IRolService rolS;
	
	@Autowired
	private IUsuarioService usuarioS;
	
	@RequestMapping("/usuario_registro")
	public ModelAndView  usuario_registro() {
		ModelAndView mav = new ModelAndView();
		Usuario usuario = new Usuario();
		List <Rol> roles = null;
		
		try {
			
			roles = rolS.findALL();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("roles", roles);
		mav.addObject("titulo", "Registro de usuario");
		mav.addObject("usuario", usuario);
		mav.setViewName("usuario_registro");
		
		
		return mav;
	}
	
	@PostMapping("/validar_usuario")
	public ModelAndView validar_usuario(@Valid @ModelAttribute Usuario usuario, BindingResult result ) {
		ModelAndView mav = new ModelAndView();
		
		
		
		if(result.hasErrors() ) {
			List <Rol> roles = null;
			
			try {
				
				roles = rolS.findALL();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			mav.addObject("roles", roles);
			mav.addObject("titulo", "Registro de usuario");
			mav.addObject("usuario", usuario);
			mav.setViewName("usuario_registro");
		}else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(usuario.getPassword());
			
			System.out.println(encodedPassword);
			
			usuario.setPassword(encodedPassword);
			
			try {
				usuarioS.save(usuario);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			mav.setViewName("index");
		}
		
		
		return mav;
	}
}
