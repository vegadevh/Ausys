package com.digitalatmosphere.ausys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.Usuario;
import com.digitalatmosphere.ausys.services.IUsuarioService;
import com.digitalatmosphere.ausys.services.UsuarioServiceImpl;


@Controller
public class AdminController {
	
	
	@Autowired
	private IUsuarioService usuarioS;
	
	@RequestMapping("/usuario_registro")
	public ModelAndView  usuario_registro() {
		ModelAndView mav = new ModelAndView();
		Usuario usuario = new Usuario();
		
		mav.addObject("titulo", "Registro de usuario");
		mav.addObject("usuario", usuario);
		mav.setViewName("usuario_registro");
		
		
		return mav;
	}
}
