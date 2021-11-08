package com.digitalatmosphere.ausys.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.dto.CasosDTO;
import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.HombresMujeresDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;
import com.digitalatmosphere.ausys.services.IDesaPeriService;

@Controller
@RequestMapping("/show")
public class SecretariaController {

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

	@RequestMapping("/listaRegistros")
	public ModelAndView listaRegistros(String keyword, String type, String sexo, String fechaI, String fechaF) {
		ModelAndView mav = new ModelAndView();
		Boolean alertActivated = false;
		List<DesaPeri> desaPeriL = null;

		String newType = "Selecciona una opción";
		if (sexo != null) {
			if (sexo.equals("Ambos")) {
				sexo = "";
			}
		} else {
			sexo = "";
		}
		
		System.out.println("fecha I: " + fechaI);
		System.out.println("fecha F: " + fechaF);
		if((fechaI == null && fechaF != null) || (fechaI != null && fechaF == null) ) {
			mav.addObject("alert", "Al buscar por fecha, ingresar los campos Fecha Inicial y Fecha Final.");
			alertActivated = true;
			mav.addObject("titulo", "Lista de registros");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaRegistros");
			return mav;
		}else if(fechaI == null && fechaF == null) {
			fechaI = fechaF = "";
		}else if((fechaI.equals("") && !fechaF.equals("")) || (!fechaI.equals("") && fechaF.equals("")) ) {
			mav.addObject("alert", "Al buscar por fecha, ingresar los campos Fecha Inicial y Fecha Final.");
			alertActivated = true;
			mav.addObject("titulo", "Lista de registros");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaRegistros");
			return mav;
		}

		// System.out.println("sexo:"+sexo);
		try {
			if(alertActivated == false) {
				if (!fechaI.equals("") && !fechaF.equals("")) {
					keyword = keyword.toLowerCase();
					if (type != null && !type.equals(newType)) {
						desaPeriL = desaPeriS.findByDateBetweenAndAbove(keyword, type, sexo, fechaI, fechaF);
					} else {
						desaPeriL = desaPeriS.findByDateBetweenAndAbove(keyword, "", sexo, fechaI, fechaF);
					}
				} else if (type != null && !type.equals(newType)) {
					keyword = keyword.toLowerCase();
					desaPeriL = desaPeriS.findByKeywordAndtipe(keyword, type, sexo);
				} else if (keyword != null) {
					keyword = keyword.toLowerCase();
					desaPeriL = desaPeriS.findByKeyword(keyword, sexo);
				} else {
					desaPeriL = desaPeriS.findAll();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("Fecha inicial: "+ fechaI);

		mav.addObject("titulo", "Lista de registros");
		mav.addObject("desaPeriL", desaPeriL);
		mav.setViewName("listaRegistros");

		return mav;
	}

	// Charts
	@RequestMapping("/graficar")
	public ModelAndView graficar() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("titulo", "Graficar");
		mav.setViewName("selectChart");
		return mav;
	}

	@RequestMapping("/graficar/HombresMujeresRangoFechas")
	public ModelAndView graficarHombresMujeresRangoFechas(@RequestParam(value = "inicio") String inicio,
			@RequestParam(value = "fin") String fin) {
		ModelAndView mav = new ModelAndView();
		
		Date fechaactual = new Date(System.currentTimeMillis());
		String fechaInicio1 = inicio;
		String fechaFin1 = fin;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		try {
			fechaInicioDate = date.parse(fechaInicio1);
			fechaFinDate = date.parse(fechaFin1);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else if (fechaInicioDate.after(fechaactual)) {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha inicio no puede ser mayor a la actual");
			mav.setViewName("selectChart");
		}else if (fechaFinDate.after(fechaactual)) {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha Final no puede ser mayor a la actual");
			mav.setViewName("selectChart");
			
		}else {
			List<HombresMujeresDTO> HombresMujeres = null;

			try {
				HombresMujeres = desaPeriS.HombresMujeresPorFecha(inicio, fin);
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Hombres y mujeres por rango de fechas");
			mav.addObject("HombresMujeres", HombresMujeres);
			mav.setViewName("charts");
		}

		return mav;
	}

	@RequestMapping("/graficar/CantidadPorCasos")
	public ModelAndView graficarCantidadPorCasos() {
		ModelAndView mav = new ModelAndView();

		List<CasosDTO> cantidadPorCasos = null;

		try {
			cantidadPorCasos = desaPeriS.cantidadPorCasos();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Casos por tipo de caso");
		mav.addObject("cantidadPorCasos", cantidadPorCasos);
		mav.setViewName("charts");

		return mav;
	}

	@RequestMapping("/graficar/CantidadPorCasosRango")
	public ModelAndView graficarCantidadPorCasosRango(@RequestParam(value = "inicio") String inicio,
			@RequestParam(value = "fin") String fin) {
		ModelAndView mav = new ModelAndView();
		
		Date fechaactual = new Date(System.currentTimeMillis());
		String fechaInicio2 = inicio;
		String fechaFin2 = fin;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		try {
			fechaInicioDate = date.parse(fechaInicio2);
			fechaFinDate = date.parse(fechaFin2);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		if (inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		}else if (fechaInicioDate.after(fechaactual)){
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha inicio no puede ser mayor a la actual");
			mav.setViewName("selectChart");
		}else if (fechaFinDate.after(fechaactual)){
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha Final no puede ser mayor a la actual");
			mav.setViewName("selectChart");
		}else {
			List<CasosDTO> cantidadPorCasos = null;

			try {
				cantidadPorCasos = desaPeriS.cantidadPorCasosRango(inicio, fin);
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Cantidad de casos por rango de tiempo");
			mav.addObject("cantidadPorCasos", cantidadPorCasos);
			mav.setViewName("charts");
		}
		return mav;
	}

	@RequestMapping("/graficar/HombresMujeresPorCaso")
	public ModelAndView graficarHombresMujeresPorCaso(@RequestParam(value = "type") String type) {
		ModelAndView mav = new ModelAndView();
		if (type.equals("col")) {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else {
			List<HombresMujeresDTO> HombresMujeres = null;

			try {
				HombresMujeres = desaPeriS.HombresMujeresPorCaso(type);
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Hombres y mujeres por tipo de caso");
			mav.addObject("HombresMujeres", HombresMujeres);
			mav.setViewName("charts");
		}

		return mav;
	}

	@RequestMapping("/graficar/HombresMujeresPorCasoYRango")
	public ModelAndView graficarHombresMujeresPorCasoYRango(@RequestParam(value = "type") String type,
			@RequestParam(value = "inicio") String inicio, @RequestParam(value = "fin") String fin) {
		ModelAndView mav = new ModelAndView();
		
		Date fechaactual = new Date(System.currentTimeMillis());
		String fechaInicio3 = inicio;
		String fechaFin3 = fin;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		try {
			fechaInicioDate = date.parse(fechaInicio3);
			fechaFinDate = date.parse(fechaFin3);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if (type.equals("col") || inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else if (fechaInicioDate.after(fechaactual)){
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha inicio no puede ser mayor a la actual");
			mav.setViewName("selectChart");
		}else if (fechaFinDate.after(fechaactual)){
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha Final no puede ser mayor a la actual");
			mav.setViewName("selectChart");
		}else {
			List<HombresMujeresDTO> HombresMujeres = null;

			try {
				HombresMujeres = desaPeriS.HombresMujeresPorCasoYRango(type, inicio, fin);
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Hombres y mujeres por tipo de caso (Rango de tiempo)");
			mav.addObject("HombresMujeres", HombresMujeres);
			mav.setViewName("charts");
		}

		return mav;
	}

	@RequestMapping("/graficar/CantidadPorCasosSexo")
	public ModelAndView graficarCantidadPorCasosSexo(@RequestParam(value = "sexo") String sexo) {
		ModelAndView mav = new ModelAndView();

		List<CasosDTO> cantidadPorCasos = null;

		try {
			cantidadPorCasos = desaPeriS.cantidadPorCasosSexo(sexo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Casos por tipo de caso - ".concat(sexo));
		mav.addObject("cantidadPorCasos", cantidadPorCasos);
		mav.setViewName("charts");

		return mav;
	}

	// Cantidad de casos por sexo (Rango Tiempo)
	@RequestMapping("/graficar/CantidadPorCasosSexoRango")
	public ModelAndView graficarCantidadPorCasosSexoRango(@RequestParam(value = "sexo") String sexo,
			@RequestParam(value = "inicio") String inicio, @RequestParam(value = "fin") String fin) {
		ModelAndView mav = new ModelAndView();
		
		Date fechaactual = new Date(System.currentTimeMillis());
		String fechaInicio4 = inicio;
		String fechaFin4 = fin;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicioDate = null;
		Date fechaFinDate = null;
		
		try {
			fechaInicioDate = date.parse(fechaInicio4);
			fechaFinDate = date.parse(fechaFin4);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		
		if (inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else if (fechaInicioDate.after(fechaactual)) {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha inicio no puede ser mayor a la actual");
			mav.setViewName("selectChart");
		}else if (fechaFinDate.after(fechaactual)){
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Fecha Final no puede ser mayor a la actual");
			mav.setViewName("selectChart");
		}else{
			List<CasosDTO> cantidadPorCasos = null;

			try {
				cantidadPorCasos = desaPeriS.cantidadPorCasosSexoRango(sexo, inicio, fin);
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Cantidad de casos por tipo (Rango de tiempo)".concat(sexo));
			mav.addObject("cantidadPorCasos", cantidadPorCasos);
			mav.setViewName("charts");
		}
		return mav;
	}

	// BUSQUEDA DE REPORTES

	@RequestMapping("/buscarPeritajes")
	public ModelAndView buscarPeritajes() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("titulo", "Busqueda de Registros de Peritajes");
		mav.setViewName("busquedaP");
		return mav;
	}

	@RequestMapping(value = "/busquedaPeritajes", params = "action=buscarnombre")
	public ModelAndView BuscarNombre(@RequestParam(value = "nombre_registro") String nombre,
			@RequestParam(value = "sexo_registro") String sexo, @RequestParam(value = "type_registro") String type) {
		ModelAndView mav = new ModelAndView();
		List<PeritajeDTO> desaPeriL = null;
		String newType = "Selecciona una opción";
		if (type.equals(newType)) {
			type = "";
		}
		if (sexo != null) {
			if (sexo.equals("Ambos")) {
				sexo = "";
			}
		} else {
			sexo = "";
		}
		try {
			desaPeriL = desaPeriS.buscarNombrePeritaje(nombre.toLowerCase(), sexo.toLowerCase(), type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (desaPeriL != null && desaPeriL.size() != 0) {
			mav.addObject("titulo", "Lista de Peritajes");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaPeritajes");

		} else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con el nombre: ".concat(nombre));
			mav.setViewName("busquedaP");
		}
		return mav;
	}

	// Buscar por Identificador
	@RequestMapping(value = "/busquedaPeritajes", params = "action=buscaridentificador")
	public ModelAndView BuscarIdentificador(@RequestParam(value = "id_registro") String id) {
		ModelAndView mav = new ModelAndView();
		List<PeritajeDTO> desaPeriL = null;

		try {
			desaPeriL = desaPeriS.buscarIdPeritaje(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (desaPeriL != null && desaPeriL.size() != 0) {
			mav.addObject("titulo", "Lista de Peritajes");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaPeritajes");

		} else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con el identificador: ".concat(id));
			mav.setViewName("busquedaP");
		}
		return mav;
	}

	// DESAPARECIDOS
	@RequestMapping("/buscarDesaparecidos")
	public ModelAndView buscarDesaparecidos() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("titulo", "Busqueda de Desaparecidos");
		mav.setViewName("busquedaD");
		return mav;
	}

	@RequestMapping(value = "/busquedaDesaparecidos", params = "action=buscarnombre")
	public ModelAndView BuscarNombreD(@RequestParam(value = "nombre_registro") String nombre,
			@RequestParam(value = "sexo_registro") String sexo) {
		ModelAndView mav = new ModelAndView();
		List<DesaparecidoDTO> desaPeriL = null;
		if (sexo != null) {
			if (sexo.equals("Ambos")) {
				sexo = "";
			}
		} else {
			sexo = "";
		}
		try {
			desaPeriL = desaPeriS.buscarNombreDesaparecido(nombre.toLowerCase(), sexo.toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (desaPeriL != null && desaPeriL.size() != 0) {
			mav.addObject("titulo", "Lista de Desaparecidos");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaDesaparecidos");

		} else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con los datos proporcionados");
			mav.setViewName("busquedaD");
		}
		return mav;
	}

	// Buscar por Identificador
	@RequestMapping(value = "/busquedaDesaparecidos", params = "action=buscaridentificador")
	public ModelAndView BuscarIdentificadorD(@RequestParam(value = "id_registro") String id) {
		ModelAndView mav = new ModelAndView();
		List<DesaparecidoDTO> desaPeriL = null;
		try {
			desaPeriL = desaPeriS.buscarIdDesaparecido(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (desaPeriL != null && desaPeriL.size() != 0) {
			mav.addObject("titulo", "Lista de Desaparecidos");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaDesaparecidos");

		} else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con el identificador: ".concat(id));
			mav.setViewName("busquedaD");
		}
		return mav;
	}

}
