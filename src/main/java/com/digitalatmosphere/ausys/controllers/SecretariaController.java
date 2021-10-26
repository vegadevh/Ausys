package com.digitalatmosphere.ausys.controllers;

import java.util.Arrays;
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
public class SecretariaController {

	@Autowired
	private IDesaPeriService desaPeriS;

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

	@RequestMapping("/listaRegistros")
	public ModelAndView listaRegistros(String keyword, String type, String sexo, String fechaI, String fechaF) {
		ModelAndView mav = new ModelAndView();

		List<DesaPeri> desaPeriL = null;

		String newType = "Selecciona una opción";
		if (sexo != null) {
			if (sexo.equals("Ambos")) {
				sexo = "";
			}
		} else {
			sexo = "";
		}

		if (fechaI == null || fechaF == null) {
			fechaI = fechaF = "";
		}

		// System.out.println("sexo:"+sexo);
		try {
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
		if (inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else {
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
		if (inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else {
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
		if (type.equals("col") || inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else {
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
		if (inicio == "" || fin == "") {
			mav.addObject("titulo", "Graficar");
			mav.addObject("alert", "Es necesario completar los campos presentes.");
			mav.setViewName("selectChart");
		} else {
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

}
