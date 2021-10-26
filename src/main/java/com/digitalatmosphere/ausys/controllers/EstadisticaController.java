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
public class EstadisticaController {

	@Autowired
	private IDesaPeriService desaPeriS;

	@Autowired
	private IPeritajeService peritajeS;

	@Autowired
	private IDesaparecidoService desaparecidoS;

	@Autowired
	private IDivisionService divisionS;

	@Autowired
	private IMunicipioService municipioS;

	@Autowired
	private IDepartamentoService departamentoS;

	// Listas
	@ModelAttribute("listaSexo")
	public List<String> listaSexo() {
		return Arrays.asList("Hombre", "Mujer");
	}

	@ModelAttribute("listaSexo2")
	public List<String> listaSexo2() {
		return Arrays.asList("Ambos", "Hombre", "Mujer");
	}

	@ModelAttribute("listaCasos")
	public List<String> listaCasos() {
		return Arrays.asList("Análisis toxicólogico", "Análisis toxicólogico Medio ambientales",
				"Asistencia a vistas públicas", "Examenes odontológicos");
	}

	@ModelAttribute("listaMarcas")
	public List<String> listaMarcas() {
		return Arrays.asList("Cicatriz", "Desperfectos", "Tatuaje");
	}

	@ModelAttribute("listaCasos3")
	public List<String> listaCasos_3() {
		return Arrays.asList("Análisis toxicólogico", "Análisis toxicólogico Medio ambientales",
				"Asistencia a vistas públicas", "Examenes odontológicos", "Desaparecido");
	}

	@ModelAttribute("listaCasos2")
	public List<String> listaCasos_2() {
		return Arrays.asList("Selecciona una opción", "Análisis toxicólogico",
				"Análisis toxicólogico Medio ambientales", "Asistencia a vistas públicas", "Examenes odontológicos",
				"Desaparecido");
	}
	//

	// Ingresar peritaje
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
		} catch (Exception e) {
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

		if (result.hasErrors()) {
			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;
			List<Division> divisiones = null;

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
				divisiones = divisionS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Ingresar Peritajes");

			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);
			mav.addObject("divisiones", divisiones);

			mav.addObject("peritaje", peritaje);
			mav.setViewName("IngresarPeritaje");
		} else {
			try {
				peritajeS.save(peritaje);
			} catch (Exception e) {
				e.getStackTrace();
			}
			DesaPeri desaPeri = new DesaPeri();
			desaPeri.setFecha_registro(new java.util.Date());

			String id_peritaje = peritajeS.findOne(peritaje.getId_peritaje()).getId_peritaje();
			mav.addObject("id_peritaje", id_peritaje);
			mav.addObject("titulo", "Ingresar Peritajes p2");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarPeritaje2");
		}
		return mav;
	}

	@PostMapping("/validarPeritaje2")
	public ModelAndView validarPeritaje2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result,
			@ModelAttribute Peritaje peritaje, BindingResult result2, @RequestParam(value = "id_peritaje") String id) {

		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			desaPeri.setFecha_registro(new java.util.Date());

			mav.addObject("titulo", "Ingresar Peritajes p2");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarPeritaje2");
		} else {
			Peritaje peritaje2 = new Peritaje();

			desaPeri.setId_peritaje(id);
			try {
				peritaje2 = peritajeS.findOne(id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			desaPeri.setPeritaje(peritaje2);
			Integer idDesaPeri = null;
			try {
				idDesaPeri = desaPeriS.saveR(desaPeri);
			} catch (Exception e) {
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
	// ingresar desaparecido

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
		} catch (Exception e) {
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
	public ModelAndView validarDesaparecido(@Valid @ModelAttribute Desaparecido desaparecido, BindingResult result) {

		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;
			List<Division> divisiones = null;

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
				divisiones = divisionS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Ingresar Desaparecido");

			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);
			mav.addObject("divisiones", divisiones);

			mav.addObject("desaparecido", desaparecido);
			mav.setViewName("IngresarDesaparecido");
		} else {
			try {
				desaparecidoS.save(desaparecido);
			} catch (Exception e) {
				e.getStackTrace();
			}
			DesaPeri desaPeri = new DesaPeri();
			String caso = "Desaparecido";
			desaPeri.setFecha_registro(new java.util.Date());
			desaPeri.setTipo_de_caso(caso);

			String id_desaparecido = desaparecidoS.findOne(desaparecido.getId_desaparecido()).getId_desaparecido();
			mav.addObject("id_desaparecido", id_desaparecido);
			mav.addObject("titulo", "Ingresar Desaparecido p2");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarDesaparecido2");
		}
		return mav;
	}

	@PostMapping("/validarDesaparecido2")
	public ModelAndView validarDesaparecido2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result,
			@ModelAttribute Desaparecido desaparecido, BindingResult result2,
			@RequestParam(value = "id_desaparecido") String id) {

		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			String caso = "Desaparecido";
			desaPeri.setFecha_registro(new java.util.Date());
			desaPeri.setTipo_de_caso(caso);

			mav.addObject("titulo", "Ingresar Desaparecido p2");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarDesaparecido2");
		} else {
			Desaparecido desaparecido2 = new Desaparecido();

//				desaPeri.setId_peritaje(id);
			desaPeri.setId_desaparecido(id);
			try {
				desaparecido2 = desaparecidoS.findOne(id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			desaPeri.setDesaparecido(desaparecido2);
			Integer idDesaPeri = null;
			try {
				idDesaPeri = desaPeriS.saveR(desaPeri);
			} catch (Exception e) {
				e.printStackTrace();
			}
			desaPeri.setId_desaperi(idDesaPeri);

			mav.addObject("desaPeri", desaPeri);
			mav.addObject("desaparecido", desaparecido);
			mav.addObject("mensaje", "Desaparecido ingresado con exito");
			mav.setViewName("verDesaparecido");
		}
		return mav;
	}
	
	//EDITAR DESAPARECIDO
	
		@RequestMapping("/editar/desaparecido/{id_desaparecido}/{id_desaperi}")
		public ModelAndView editarDesaparecido(@RequestParam(value="id_desaparecido") String id_desaparecido, @RequestParam(value="id_desaperi") String id_desaperi) {
			ModelAndView mav = new ModelAndView();
			
			if(id_desaparecido != null) {
				Desaparecido desaparecido = desaparecidoS.findOne(id_desaparecido);
				
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
				
				mav.addObject("titulo", "Editar Desaparecido");
				
				mav.addObject("departamentos", departamentos);
				mav.addObject("municipios", municipios);
				mav.addObject("divisiones", divisiones);
				
				mav.addObject("desaparecido", desaparecido);
				mav.addObject("id_desaparecidoParam", id_desaparecido);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarDesaparecido");
			}else {
				mav.setViewName("listaDesaparecidos");
			}
			
			return mav;
		}
		
		@RequestMapping("/editar/desaparecido2/{id_desaparecido}/{id_desaperi}")
		public ModelAndView editarDesaparecido2(@Valid @ModelAttribute Desaparecido desaparecido, BindingResult result, @RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_desaparecido") String id_desaparecido) {
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
				
				mav.addObject("titulo", "Editar Desaparecido");
				
				mav.addObject("departamentos", departamentos);
				mav.addObject("municipios", municipios);
				mav.addObject("divisiones", divisiones);
				
				mav.addObject("desaparecido", desaparecido);
				mav.addObject("id_desaparecidoParam", id_desaparecido);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarDesaparecido");
			}else {
				try {
					desaparecidoS.save(desaparecido);
				}catch(Exception e){
					e.getStackTrace();
				}
				
				if(id_desaperi != null) {
					DesaPeri desaPeri = desaPeriS.findOne(Integer.parseInt(id_desaperi));
					
					mav.addObject("titulo", "Editar Desaparecido p2");
					mav.addObject("desaPeri", desaPeri);
					
					mav.addObject("id_desaparecidoParam", id_desaparecido);
					mav.addObject("id_desaperiParam", id_desaperi);
					mav.setViewName("editarDesaparecido2");
					
				}
				
			}
			
			return mav;
		}
		
		@RequestMapping("/validarEdicion/desaparecido2/{id_desaparecido}/{id_desaperi}")
		public ModelAndView validarEdicionDesaparecido2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result, @Valid @ModelAttribute Desaparecido desaparecido, BindingResult result2, @RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_desaparecido") String id_desaparecido) {
			ModelAndView mav = new ModelAndView();
			
			if(result.hasErrors()) {
				
				mav.addObject("titulo", "Editar Desaparecido p2");
				mav.addObject("desaPeri",desaPeri);
				mav.addObject("id_desaparecidoParam", id_desaparecido);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarDesaparecido2");
			}else {
				Desaparecido desaparecido2 = new Desaparecido();
				desaPeri.setId_desaparecido(id_desaparecido);
				try {
					desaparecido2 = desaparecidoS.findOne(id_desaparecido);
				}catch(Exception e){
					e.printStackTrace();
				}
				desaPeri.setDesaparecido(desaparecido2);
				try {
					desaPeriS.save(desaPeri);
				}catch(Exception e){
					e.printStackTrace();
				}
				desaPeri.setId_desaperi(Integer.parseInt(id_desaperi));
				
				mav.addObject("desaPeri", desaPeri);
				mav.addObject("desaparecido", desaparecido);
				mav.addObject("id_desaparecidoParam", id_desaparecido);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.addObject("mensaje", "Desaparecido editado con exito");
				mav.setViewName("verDesaparecido");
			}
			return mav;
		}
		
		//EDITAR PERITAJE
		
		@RequestMapping("/editar/peritaje/{id_peritaje}/{id_desaperi}")
		public ModelAndView editarPeritaje(@RequestParam(value="id_peritaje") String id_peritaje, @RequestParam(value="id_desaperi") String id_desaperi) {
			ModelAndView mav = new ModelAndView();
			
			if(id_peritaje != null) {
				Peritaje peritaje = peritajeS.findOne(id_peritaje);
				
				mav.addObject("titulo", "Editar Peritaje");
				
				mav.addObject("peritaje", peritaje);
				mav.addObject("id_peritajeParam", id_peritaje);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarPeritaje");
			}else {
				mav.setViewName("listaPeritajes");
			}
			
			return mav;
		}
		
		@RequestMapping("/editar/peritaje2/{id_peritaje}/{id_desaperi}")
		public ModelAndView editarDesaparecido2(@Valid @ModelAttribute Peritaje peritaje, BindingResult result, @RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_peritaje") String id_peritaje) {
			ModelAndView mav = new ModelAndView();
			
			if(result.hasErrors()) {
				
				mav.addObject("titulo", "Editar Peritaje");
				
				mav.addObject("peritaje", peritaje);
				mav.addObject("id_peritajeParam", id_peritaje);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarPeritaje");
			}else {
				try {
					peritajeS.save(peritaje);
				}catch(Exception e){
					e.getStackTrace();
				}
				
				if(id_desaperi != null) {
					DesaPeri desaPeri = desaPeriS.findOne(Integer.parseInt(id_desaperi));
					
					mav.addObject("titulo", "Editar Peritaje p2");
					mav.addObject("desaPeri", desaPeri);
					
					mav.addObject("id_peritajeParam", id_peritaje);
					mav.addObject("id_desaperiParam", id_desaperi);
					mav.setViewName("editarPeritaje2");
					
				}
				
			}
			
			return mav;
		}
		
		@RequestMapping("/validarEdicion/peritaje2/{id_peritaje}/{id_desaperi}")
		public ModelAndView validarEdicionPeritaje2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result, @Valid @ModelAttribute Peritaje peritaje, BindingResult result2, @RequestParam(value="id_desaperi") String id_desaperi, @RequestParam(value="id_peritaje") String id_peritaje) {
			ModelAndView mav = new ModelAndView();
			
			if(result.hasErrors()) {
				
				mav.addObject("titulo", "Editar Peritaje p2");
				mav.addObject("desaPeri",desaPeri);
				mav.addObject("id_peritajeParam", id_peritaje);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarPeritaje2");
			}else {
				Peritaje peritaje2 = new Peritaje();
				desaPeri.setId_peritaje(id_peritaje);
				try {
					peritaje2 = peritajeS.findOne(id_peritaje);
				}catch(Exception e){
					e.printStackTrace();
				}
				desaPeri.setPeritaje(peritaje2);
				try {
					desaPeriS.save(desaPeri);
				}catch(Exception e){
					e.printStackTrace();
				}
				desaPeri.setId_desaperi(Integer.parseInt(id_desaperi));
				
				mav.addObject("desaPeri", desaPeri);
				mav.addObject("peritaje", peritaje);
				mav.addObject("id_peritajeParam", id_peritaje);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.addObject("mensaje", "Peritaje editado con exito");
				mav.setViewName("verPeritaje");
			}
			return mav;
		}
}
