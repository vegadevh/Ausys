package com.digitalatmosphere.ausys.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.Departamento;
import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.domains.Desaparecido;
import com.digitalatmosphere.ausys.domains.Division;
import com.digitalatmosphere.ausys.domains.Municipio;
import com.digitalatmosphere.ausys.domains.Peritaje;
import com.digitalatmosphere.ausys.services.IDepartamentoService;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDesaparecidoService;
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
	
	@Autowired
	private IDesaparecidoService desaparecidoS;
	
	//Listas
	@ModelAttribute("listaSexo")
	public List<String> listaSexo(){
		return Arrays.asList("Hombre", "Mujer");
	}
	//
	
	//Ingresar peritaje
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
			desaPeri.setFecha_registro(new java.util.Date());
			
			String id_peritaje = peritajeS.findOne(peritaje.getId_peritaje()).getId_peritaje();
			mav.addObject("id_peritaje",id_peritaje);
			mav.addObject("titulo", "Ingresar Peritajes p2");
			mav.addObject("desaPeri",desaPeri);
			mav.setViewName("ingresarPeritaje2");
		}
		return mav;
	}
	
	@PostMapping("/validarPeritaje2")
	public ModelAndView validarPeritaje2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result, @ModelAttribute Peritaje peritaje, BindingResult result2,@RequestParam(value="id_peritaje") String id) {
		
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			desaPeri.setFecha_registro(new java.util.Date());

			mav.addObject("titulo", "Ingresar Peritajes p2");
			mav.addObject("desaPeri",desaPeri);
			mav.setViewName("ingresarPeritaje2");
		}else {
			Peritaje peritaje2 = new Peritaje();
			
			desaPeri.setId_peritaje(id);
			try {
				peritaje2 = peritajeS.findOne(id);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			desaPeri.setPeritaje(peritaje2);
			Integer idDesaPeri = null;
			try {
				idDesaPeri = desaPeriS.saveR(desaPeri);
			}catch(Exception e){
				e.printStackTrace();
			}
			desaPeri.setId_desaperi(idDesaPeri);
			
			mav.addObject("desaPeri", desaPeri);
			mav.addObject("peritaje", peritaje);
			mav.addObject("mensaje", "Peritaje ingresado con exito");
			mav.setViewName("verPeritaje");
		}
		return mav;
	}
	//ingresar desaparecido
	
	@RequestMapping("/ingresarDesaparecido")
	public ModelAndView ingresarDesaparecido() {
		ModelAndView mav = new ModelAndView();
		
		Desaparecido desaparecido = new Desaparecido();
		
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
		
		mav.addObject("titulo", "Ingresar Desaparecido");
		
		mav.addObject("departamentos", departamentos);
		mav.addObject("municipios", municipios);
		mav.addObject("divisiones", divisiones);
		
		mav.addObject("desaparecido", desaparecido);
		
		mav.setViewName("ingresarDesaparecido");
		
		return mav;
	}
	
	@RequestMapping("/validarDesaparecido")
	public ModelAndView validarPeritaje(@Valid @ModelAttribute Desaparecido desaparecido, BindingResult result) {
		
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
			
			mav.addObject("desaparecido", desaparecido);
			mav.setViewName("IngresarDesaparecido");
		}else {
			try {
				desaparecidoS.save(desaparecido);
			}catch(Exception e){
				e.getStackTrace();
			}
			DesaPeri desaPeri = new DesaPeri();
			desaPeri.setFecha_registro(new java.util.Date());
			
			String id_desaparecido = desaparecidoS.findOne(desaparecido.getId_desaparecido()).getId_desaparecido();
			mav.addObject("id_desaparecido",id_desaparecido);
			mav.addObject("titulo", "Ingresar Peritajes p2");
			mav.addObject("desaPeri",desaPeri);
			mav.setViewName("ingresarDesaparecido2");
		}
		return mav;
	}
	
	@PostMapping("/validarDesaparecido2")
	public ModelAndView validarDesaparecido2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result, @ModelAttribute Desaparecido desaparecido, BindingResult result2,@RequestParam(value="id_desaparecido") String id) {
		
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			desaPeri.setFecha_registro(new java.util.Date());

			mav.addObject("titulo", "Ingresar Desaparecido p2");
			mav.addObject("desaPeri",desaPeri);
			mav.setViewName("ingresarDesaparecido2");
		}else {
			Desaparecido desaparecido2 = new Desaparecido();
			
			desaPeri.setId_peritaje(id);
			try {
				desaparecido2 = desaparecidoS.findOne(id);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			desaPeri.setDesaparecido(desaparecido2);
			Integer idDesaPeri = null;
			try {
				idDesaPeri = desaPeriS.saveR(desaPeri);
			}catch(Exception e){
				e.printStackTrace();
			}
			desaPeri.setId_desaperi(idDesaPeri);
			
			mav.addObject("desaPeri", desaPeri);
			mav.addObject("desaparecido",desaparecido);
			mav.addObject("mensaje", "Desaparecido ingresado con exito");
			mav.setViewName("verDesaparecido");
		}
		return mav;
	}
	
	@RequestMapping("/listaPeritajes")
	public ModelAndView listaPeritajes() {
		ModelAndView mav = new ModelAndView();

		List<DesaPeri> desaPeriL = null;
		try {
			desaPeriL = desaPeriS.findAllPeritajes();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Lista de peritajes");
		mav.addObject("desaPeriL", desaPeriL);
		mav.setViewName("listaPeritajes");

		return mav;
	}
	
	@RequestMapping("/listaDesaparecidos")
	public ModelAndView listaDesaparecidos() {
		ModelAndView mav = new ModelAndView();

		List<DesaPeri> desaPeriL = null;
		try {
			desaPeriL = desaPeriS.findAllDesaparecidos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Lista de desaparecidos");
		mav.addObject("desaPeriL", desaPeriL);
		mav.setViewName("listaDesaparecidos");

		return mav;
	}
	
	@RequestMapping("/listaRegistros")
	public ModelAndView listaRegistros() {
		ModelAndView mav = new ModelAndView();

		List<DesaPeri> desaPeriL = null;
		try {
			desaPeriL = desaPeriS.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Lista de registros");
		mav.addObject("desaPeriL", desaPeriL);
		mav.setViewName("listaRegistros");

		return mav;
	}
}
