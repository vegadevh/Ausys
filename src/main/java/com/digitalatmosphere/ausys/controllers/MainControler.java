package com.digitalatmosphere.ausys.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.Departamento;
import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.domains.Division;
import com.digitalatmosphere.ausys.domains.Municipio;
import com.digitalatmosphere.ausys.services.IDepartamentoService;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDivisionService;
import com.digitalatmosphere.ausys.services.IMunicipioService;
//import com.digitalatmosphere.ausys.domains.Peritaje;

@Controller
public class MainControler {
	
	@Autowired
	private IDivisionService divisionS;
	
	@Autowired
	private IMunicipioService municipioS;
	
	@Autowired
	private IDepartamentoService departamentoS;
	
	@Autowired
	private IDesaPeriService desaPeriS;
	
	//Listas
	@ModelAttribute("listaSexo")
	public List<String> listaSexo(){
		return Arrays.asList("Hombre", "Mujer");
	}
	//

	@GetMapping({"/ingresarPeritaje"})
	public ModelAndView ingresarPeritaje() {
		ModelAndView mav = new ModelAndView();
		DesaPeri desaPeri = new DesaPeri();
		desaPeri.setFecha_registro(new java.util.Date());
		
		
		List<Departamento> departamentos = null;
		List<Municipio> municipios = null;
		List<Division> divisiones = null;
		
		try {
			departamentos = departamentoS.findAll();
			municipios = municipioS.findAll();
			divisiones = divisionS.findAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		mav.addObject("titulo", "Ingresar Peritajes");
		
		mav.addObject("departamentos", departamentos);
		mav.addObject("municipios", municipios);
		mav.addObject("divisiones", divisiones);
		
		mav.addObject("desaPeri", desaPeri);
		mav.setViewName("IngresarPeritaje");
		return mav;
	}
	
//	@PostMapping({"/validarPeritaje"})
//	public ModelAndView validarPeritaje(@ModelAttribute DesaPeri desaPeri, BindingResult result) {
//		
//		ModelAndView mav = new ModelAndView();
//		
//		if(result.hasErrors()) {
//			mav.setViewName("IngresarPeritaje");
//		}else {
//			try {
//				desaPeriS.save(desaPeri);
//			}catch(Exception e){
//				e.getStackTrace();
//			}
//			String mensaje ="Peritaje creado con éxito";
//			mav.addObject("mensaje", mensaje);
//			mav.setViewName("index");
//		}
//		return mav;
//	}
}
