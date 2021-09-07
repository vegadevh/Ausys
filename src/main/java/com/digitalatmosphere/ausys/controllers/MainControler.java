package com.digitalatmosphere.ausys.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.Departamento;
import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.domains.Division;
import com.digitalatmosphere.ausys.domains.Municipio;
import com.digitalatmosphere.ausys.domains.Peritaje;
import com.digitalatmosphere.ausys.services.IDepartamentoService;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDivisionService;
import com.digitalatmosphere.ausys.services.IMunicipioService;
import com.digitalatmosphere.ausys.services.IPeritajeService;

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
	
	@Autowired
	private IPeritajeService peritajeS;
	
	//Listas
	@ModelAttribute("listaSexo")
	public List<String> listaSexo(){
		return Arrays.asList("Hombre", "Mujer");
	}
	//
	
	@RequestMapping("/ingresarPeritaje")
	public ModelAndView ingresarPeritaje() {
		ModelAndView mav = new ModelAndView();
		Peritaje peritaje = new Peritaje();
		
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
		
		mav.addObject("peritaje", peritaje);
		mav.setViewName("IngresarPeritaje");
		return mav;
	}
	
	@RequestMapping("/validarPeritaje")
	public ModelAndView validarPeritaje(@Valid @ModelAttribute Peritaje peritaje, BindingResult result) {
		
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
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
			
			mav.addObject("peritaje", peritaje);
			mav.setViewName("IngresarPeritaje");
		}else {
			try {
				peritajeS.save(peritaje);
			}catch(Exception e){
				e.getStackTrace();
			}
			DesaPeri desaPeri = new DesaPeri();
//			Peritaje peritaje2 = new Peritaje();
			
//			desaPeri.setId_peritaje(id);
			desaPeri.setFecha_registro(new java.util.Date());
			
//			try {
//				peritaje2 = peritajeS.findOne(id);
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
			
			String id_peritaje = peritajeS.findOne(peritaje.getId_peritaje()).getId_peritaje();
			mav.addObject("id_peritaje",id_peritaje);
			mav.addObject("titulo", "Ingresar Peritajes p2");
			mav.addObject("desaPeri",desaPeri);
			mav.setViewName("ingresarDesaPeri");
		}
		return mav;
	}
	
	@PostMapping("/validarPeritaje2")
	public ModelAndView validarPeritaje2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result, @ModelAttribute Peritaje peritaje, BindingResult result2,@RequestParam(value="id_peritaje") String id) {
		
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			desaPeri.setFecha_registro(new java.util.Date());
			
//			try {
//				peritaje = peritajeS.findOne(desaPeri.getId_peritaje());
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//			
//			desaPeri.setPeritaje(peritaje);
			mav.addObject("titulo", "Ingresar Peritajes p2");
			mav.addObject("desaPeri",desaPeri);
			mav.setViewName("ingresarDesaPeri");
		}else {
			Peritaje peritaje2 = new Peritaje();
			
			desaPeri.setId_peritaje(id);
			try {
				peritaje2 = peritajeS.findOne(id);
//				desaPeriS.save(desaPeri);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			desaPeri.setPeritaje(peritaje2);
			
			try {
				desaPeriS.save(desaPeri);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			mav.addObject("mensaje", "Peritaje ingresado con exito");
			mav.setViewName("index");
		}
		return mav;
	}
}
