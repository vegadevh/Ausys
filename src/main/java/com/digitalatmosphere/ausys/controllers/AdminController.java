package com.digitalatmosphere.ausys.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.Rol;
import com.digitalatmosphere.ausys.domains.Usuario;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDesaparecidoService;
import com.digitalatmosphere.ausys.services.IPeritajeService;
import com.digitalatmosphere.ausys.services.IRolService;
import com.digitalatmosphere.ausys.services.IUsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private IRolService rolS;
	
	@Autowired
	private IUsuarioService usuarioS;
	
	@Autowired
	private IDesaPeriService desaPeriS;
	
	@Autowired
	private IPeritajeService peritajeS;
	
	@Autowired
	private IDesaparecidoService desaparecidoS;
	
	//Listas
	@ModelAttribute("listaSexo")
	public List<String> listaSexo(){
		return Arrays.asList("Masculino", "Femenino");
	}
	
		@ModelAttribute("listaSexo2")
		public List<String> listaSexo2(){
			return Arrays.asList("Ambos","Masculino", "Femenino");
		}
	
	@ModelAttribute("listaCasos")
	public List<String> listaCasos(){
		return Arrays.asList("Análisis toxicólogico", "Análisis toxicólogico Medio ambientales", 
				"Asistencia a vistas públicas", "Examenes odontológicos");
	}
	
	@ModelAttribute("listaMarcas")
	public List<String> listaMarcas(){
		return Arrays.asList("Cicatriz", "Desperfectos", "Tatuaje");
	}
	@ModelAttribute("listaCasos3")
	public List<String> listaCasos_3(){
		return Arrays.asList("Análisis toxicólogico", "Análisis toxicólogico Medio ambientales", 
				"Asistencia a vistas públicas", "Examenes odontológicos", "Desaparecido");
	}
	
	@ModelAttribute("listaCasos2")
	public List<String> listaCasos_2(){
		return Arrays.asList("Selecciona una opción","Análisis toxicólogico", "Análisis toxicólogico Medio ambientales", 
				"Asistencia a vistas públicas", "Examenes odontológicos", "Desaparecido");
	}
	//
	
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
	
	//LISTA DE USUARIOS
	
	@RequestMapping("/listaUsuarios")
	public ModelAndView listaPeritajes() {
		ModelAndView mav = new ModelAndView();

		List<Usuario> users = null;
		try {
			users = usuarioS.findALL();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Lista de usuarios");
		mav.addObject("users", users);
		mav.setViewName("listaUsuario");

		return mav;
	}
	
	@RequestMapping("/eliminar/usuario/{id_usuario}")
	public ModelAndView eliminarUsuario(@RequestParam(value="id_usuario") String id_usuario) {
		ModelAndView mav = new ModelAndView();
		if(id_usuario != null) {
			usuarioS.delete(Integer.parseInt(id_usuario));
		}
		
		mav.setViewName("redirect:/admin/listaUsuarios");
		return mav;
	}
	
	@RequestMapping("/editar/usuario/{id_usuario}")
	public ModelAndView editarUsuario(@RequestParam(value="id_usuario") String id_usuario) {
		ModelAndView mav = new ModelAndView();
		Usuario usuario = new Usuario();
		
		
		if(id_usuario != null) {
			usuario = usuarioS.findOne(Integer.parseInt(id_usuario));
			if(usuario.getEnabled_u() == true) {
				usuario.setEnabled_u(false);
				
			}else {
				usuario.setEnabled_u(true);
			}
			
			usuarioS.save(usuario);
			
		}
		
		
		mav.setViewName("redirect:/admin/listaUsuarios");
		return mav;
	}
	

	//ELIMINAR
	
	@RequestMapping("/eliminar/peritaje/{id_peritaje}/{id_desaperi}")
	public ModelAndView eliminarPeritaje(@RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_peritaje") String id_peritaje) {
		ModelAndView mav = new ModelAndView();
		if(id_desaperi !=null) {
			desaPeriS.delete(Integer.parseInt(id_desaperi));
			peritajeS.delete(id_peritaje);
			
		}
		mav.setViewName("redirect:/read/listaPeritajes");
		
		return mav;
	}
	
	@RequestMapping("/eliminar/desaparecido/{id_desaparecido}/{id_desaperi}")
	public ModelAndView eliminarDesaparecido(@RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_desaparecido") String id_desaparecido) {
		ModelAndView mav = new ModelAndView();
		if(id_desaperi !=null) {
			desaPeriS.delete(Integer.parseInt(id_desaperi));
			desaparecidoS.delete(id_desaparecido);
			
		}
		mav.setViewName("redirect:/read/listaDesaparecidos");
		
		return mav;
	}
}
