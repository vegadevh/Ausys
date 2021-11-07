package com.digitalatmosphere.ausys.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.EspecialDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;
import com.digitalatmosphere.ausys.dto.RegistroDTO;
import com.digitalatmosphere.ausys.dto.fotografiaDTO;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IEspecialService;
import com.digitalatmosphere.ausys.services.IFotoService;

@Controller
@RequestMapping("/read")
public class EstadisticaSecretariaController {
	
	@Autowired
	private IEspecialService especialS;
	
	@Autowired
	private IFotoService fotoS;

	@Autowired
	private IDesaPeriService desaPeriS;

	// Listas
	@ModelAttribute("listaSexo")
	public List<String> listaSexo() {
		return Arrays.asList("Masculino", "Femenino");
	}

	@ModelAttribute("listaSexo2")
	public List<String> listaSexo2() {
		return Arrays.asList("Ambos", "Masculino", "Femenino");
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

	@RequestMapping("/listaPeritajes")
	public ModelAndView listaPeritajes() {
		ModelAndView mav = new ModelAndView();

		List<PeritajeDTO> desaPeriL = null;
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

		List<DesaparecidoDTO> desaPeriL = null;
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

	// VER REGISTTRO INDIVIDUAL

	@RequestMapping("/verRegistro/{id_peritaje}/{id_desaperi}")
	public ModelAndView verRegistroPeritaje(@PathVariable(value = "id_peritaje") String id_peritaje,
			@PathVariable(value = "id_desaperi") String id_desaperi,
			@RequestParam(value = "peritajeName") String peritajeName,
			@RequestParam(value = "peritajeLastname") String peritajeLastname,
			@RequestParam(value = "edad_estimada") String edad_estimada) {
		ModelAndView mav = new ModelAndView();

		List<RegistroDTO> registro = null;
		List<fotografiaDTO> fotos = null;
		List<EspecialDTO> especiales = null;
		List<DesaparecidoDTO> relacion = null;
		try {
			especiales = especialS.especialPeritaje(id_peritaje);
			fotos = fotoS.fotosPeritaje(id_peritaje);
			registro = desaPeriS.verRegistroPeritaje(id_peritaje, id_desaperi);
			Integer edad = Integer.parseInt(edad_estimada);
			relacion = desaPeriS.PeritajesXDesaparecidos(peritajeName, peritajeLastname, edad);
			System.out.println(registro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (registro != null && registro.size() != 0) {
			mav.addObject("titulo", "Registro: ".concat(id_peritaje));
			mav.addObject("relacion", relacion);
			mav.addObject("registro", registro);
			mav.addObject("especiales", especiales);
			mav.addObject("fotos", fotos);
			mav.addObject("val", "Peritaje");
			mav.setViewName("verRegistro");
		} else {
			mav.addObject("titulo", "Lista de Peritajes");
			mav.setViewName("listaPeritajes");
		}

		return mav;
	}

	@RequestMapping("/verRegistroD/{id_desaparecido}/{id_desaperi}")
	public ModelAndView verRegistroDesaparecido(@PathVariable(value = "id_desaparecido") String id_desaparecido,
			@PathVariable(value = "id_desaperi") String id_desaperi) {
		ModelAndView mav = new ModelAndView();

		List<RegistroDTO> registro = null;
		List<fotografiaDTO> fotos = null;
		List<EspecialDTO> especiales = null;
		try {
			especiales = especialS.especialDesaparecido(id_desaparecido);
			fotos = fotoS.fotosDesaparecido(id_desaparecido);
			registro = desaPeriS.verRegistroDesaparecido(id_desaparecido, id_desaperi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (registro != null && registro.size() != 0) {
			mav.addObject("titulo", "Registro: ".concat(id_desaparecido));
			mav.addObject("registro", registro);
			mav.addObject("fotos", fotos);
			mav.addObject("especiales", especiales);
			mav.addObject("val", "Desaparecido");
			mav.setViewName("verRegistro");
		} else {
			mav.addObject("titulo", "Lista de Desaparecidos");
			mav.setViewName("listaDesaparecidos");
		}

		return mav;
	}
}
