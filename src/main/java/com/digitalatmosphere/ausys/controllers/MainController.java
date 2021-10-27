package com.digitalatmosphere.ausys.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDesaparecidoService;
import com.digitalatmosphere.ausys.services.IPeritajeService;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	private IDesaPeriService desaPeriS;
	
	@Autowired
	private IPeritajeService peritajeS;
	
	@Autowired
	private IDesaparecidoService desaparecidoS;
	
	//Listas
	@ModelAttribute("listaSexo")
	public List<String> listaSexo(){
		return Arrays.asList("Hombre", "Mujer");
	}
	
		@ModelAttribute("listaSexo2")
		public List<String> listaSexo2(){
			return Arrays.asList("Ambos","Hombre", "Mujer");
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
	
	//ELIMINAR
	
	@RequestMapping("/eliminar/peritaje/{id_peritaje}/{id_desaperi}")
	public ModelAndView eliminarPeritaje(@RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_peritaje") String id_peritaje) {
		ModelAndView mav = new ModelAndView();
		if(id_desaperi !=null) {
			desaPeriS.delete(Integer.parseInt(id_desaperi));
			peritajeS.delete(id_peritaje);
			
		}
		mav.setViewName("redirect:/listaPeritajes");
		
		return mav;
	}
	
	@RequestMapping("/eliminar/desaparecido/{id_desaparecido}/{id_desaperi}")
	public ModelAndView eliminarDesaparecido(@RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_desaparecido") String id_desaparecido) {
		ModelAndView mav = new ModelAndView();
		if(id_desaperi !=null) {
			desaPeriS.delete(Integer.parseInt(id_desaperi));
			desaparecidoS.delete(id_desaparecido);
			
		}
		mav.setViewName("redirect:/listaDesaparecidos");
		
		return mav;
	}
	
}
