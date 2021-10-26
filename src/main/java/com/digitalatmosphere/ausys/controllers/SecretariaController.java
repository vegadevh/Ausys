package com.digitalatmosphere.ausys.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.domains.Desaparecido;
import com.digitalatmosphere.ausys.domains.Especial;
import com.digitalatmosphere.ausys.domains.Foto;
import com.digitalatmosphere.ausys.domains.Peritaje;
import com.digitalatmosphere.ausys.dto.CasosDTO;
import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.HombresMujeresDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDesaparecidoService;
import com.digitalatmosphere.ausys.services.IEspecialService;
import com.digitalatmosphere.ausys.services.IFotoService;
import com.digitalatmosphere.ausys.services.IPeritajeService;

@Controller
public class SecretariaController {

	@Autowired
	private IEspecialService especialS;

	@Autowired
	private IFotoService fotoS;

	@Autowired
	private IPeritajeService peritajeS;

	@Autowired
	private IDesaPeriService desaPeriS;

	@Autowired
	private IDesaparecidoService desaparecidoS;

	public static String DirectorioArchivos = System.getProperty("user.dir") + "/src/main/webapp/imagedata";

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

	// AGREGAR ESPECIAL
	@RequestMapping("/especial/{id}/{id_registro}")
	public ModelAndView agregarEspecial(@RequestParam(value = "id") String id) {
		ModelAndView mav = new ModelAndView();
		Especial especial = new Especial();

		mav.addObject("titulo", "Ingresar marca especial");
		mav.addObject("especial", especial);
		mav.addObject("id", id);
		mav.addObject("val", "Peritaje");
		mav.setViewName("ingresarEspecial");
		return mav;
	}

	@RequestMapping("/validarEspecial/{id}")
	public ModelAndView validarEspecial(@Valid @ModelAttribute Especial especial, BindingResult result,
			@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {

			mav.addObject("titulo", "Ingresar marca especial");
			mav.addObject("especial", especial);
			mav.addObject("id", id);
			mav.addObject("val", "Peritaje");
			mav.setViewName("ingresarEspecial");
		} else {
			Peritaje peritaje = new Peritaje();
			especial.setId_peritaje(id);
			peritaje = peritajeS.findOne(id);
			especial.setPeritaje(peritaje);

			try {
				especialS.save(especial);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("redirect:/listaPeritajes");
		}
		return mav;
	}

	@RequestMapping("/especialD/{id}/{id_registro}")
	public ModelAndView agregarEspecialD(@RequestParam(value = "id") String id) {
		ModelAndView mav = new ModelAndView();
		Especial especial = new Especial();

		mav.addObject("titulo", "Ingresar marca especial");
		mav.addObject("especial", especial);
		mav.addObject("id", id);
		mav.addObject("val", "Desaparecido");
		mav.setViewName("ingresarEspecial");
		return mav;
	}

	@RequestMapping("/validarEspecialD/{id}")
	public ModelAndView validarEspecialD(@Valid @ModelAttribute Especial especial, BindingResult result,
			@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {

			// CAMBIAR LOGICA
			mav.addObject("titulo", "Ingresar marca especial");
			mav.addObject("especial", especial);
			mav.addObject("id", id);
			mav.addObject("val", "Desaparecido");
			mav.setViewName("ingresarEspecial");
		} else {
			Desaparecido desaparecido = new Desaparecido();
			especial.setId_desaparecido(id);
			desaparecido = desaparecidoS.findOne(id);
			especial.setDesaparecido(desaparecido);

			try {
				especialS.save(especial);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("redirect:/listaDesaparecidos");
		}
		return mav;
	}

	// AGREGAR FOTOS
	@RequestMapping("/fotografia/D/{id_registro}/{id_desaperi}")
	public ModelAndView addFotoDesaparecido(@RequestParam(value = "id") String id) {
		ModelAndView mav = new ModelAndView();
		Foto foto = new Foto();

		mav.addObject("titulo", "Ingresar Foto");
		mav.addObject("foto", foto);
		mav.addObject("id", id);
		mav.addObject("val", "Desaparecido");
		mav.setViewName("ingresarFoto");
		return mav;
	}

	@RequestMapping("/fotografia/P/{id_registro}/{id_desaperi}")
	public ModelAndView addFotoPeritaje(@RequestParam(value = "id") String id) {
		ModelAndView mav = new ModelAndView();
		Foto foto = new Foto();

		mav.addObject("titulo", "Ingresar Foto");
		mav.addObject("foto", foto);
		mav.addObject("id", id);
		mav.addObject("val", "Peritaje");
		mav.setViewName("ingresarFoto");
		return mav;
	}

	@RequestMapping("/subir/D/{id_registro}")
	@ResponseBody
	public ModelAndView subirFoto(@Valid @ModelAttribute Foto foto, BindingResult result,
			@PathVariable(value = "id_registro") String id, @RequestParam(value = "img") MultipartFile file) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {

			mav.addObject("titulo", "Ingresar Foto");
			mav.addObject("foto", foto);
			String alert = "El campo no puede ser vacio. Ingrese una foto.";
			mav.addObject("id", id);
			mav.addObject("alert", alert);
			mav.addObject("val", "Desaparecido");
			mav.setViewName("ingresarFoto");
		} else {
			StringBuilder fileName = new StringBuilder();

			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String strDate = dateFormat.format(date);
			String myDate = strDate.replaceAll(":", "");

			try {
				String filename = myDate.concat(
						id.concat(file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4)));
				Path fileNameAndPath = Paths.get(DirectorioArchivos, filename);

				try {
					Files.write(fileNameAndPath, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				foto.setFoto(filename);

				Desaparecido desaparecido = new Desaparecido();
				foto.setId_desaparecido(id);
				desaparecido = desaparecidoS.findOne(id);
				foto.setDesaparecido(desaparecido);

				try {
					fotoS.save(foto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mav.setViewName("redirect:/listaDesaparecidos");
			} catch (StringIndexOutOfBoundsException e) {
				String alert = "El campo no puede ser vacio. Ingrese una foto.";
				mav.addObject("titulo", "Ingresar Foto");
				mav.addObject("foto", foto);
				mav.addObject("id", id);
				mav.addObject("alert", alert);
				mav.addObject("val", "Desaparecido");
				mav.setViewName("ingresarFoto");
			}
		}
		return mav;
	}

	@RequestMapping("/subir/P/{id_registro}")
	@ResponseBody
	public ModelAndView subirFotoP(@Valid @ModelAttribute Foto foto, BindingResult result,
			@PathVariable(value = "id_registro") String id, @RequestParam(value = "img") MultipartFile file) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {

			mav.addObject("titulo", "Ingresar Foto");
			mav.addObject("foto", foto);
			String alert = "El campo no puede ser vacio. Ingrese una foto.";
			mav.addObject("id", id);
			mav.addObject("alert", alert);
			mav.addObject("val", "Peritaje");
			mav.setViewName("ingresarFoto");
		} else {
			StringBuilder fileName = new StringBuilder();

			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String strDate = dateFormat.format(date);
			String myDate = strDate.replaceAll(":", "");

			try {
				String filename = myDate.concat(
						id.concat(file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4)));
				Path fileNameAndPath = Paths.get(DirectorioArchivos, filename);

				try {
					Files.write(fileNameAndPath, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				foto.setFoto(filename);

				Peritaje peritaje = new Peritaje();
				foto.setId_peritaje(id);
				peritaje = peritajeS.findOne(id);
				foto.setPeritaje(peritaje);

				try {
					fotoS.save(foto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mav.setViewName("redirect:/listaPeritajes");
			} catch (StringIndexOutOfBoundsException e) {
				String alert = "El campo no puede ser vacio. Ingrese una foto.";
				mav.addObject("titulo", "Ingresar Foto");
				mav.addObject("foto", foto);
				mav.addObject("id", id);
				mav.addObject("alert", alert);
				mav.addObject("val", "Peritaje");
				mav.setViewName("ingresarFoto");
			}
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
