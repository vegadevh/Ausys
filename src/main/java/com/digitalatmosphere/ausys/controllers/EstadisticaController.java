package com.digitalatmosphere.ausys.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalatmosphere.ausys.domains.Departamento;
import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.domains.Desaparecido;
import com.digitalatmosphere.ausys.domains.Especial;
import com.digitalatmosphere.ausys.domains.Foto;
import com.digitalatmosphere.ausys.domains.Municipio;
import com.digitalatmosphere.ausys.domains.Peritaje;
import com.digitalatmosphere.ausys.services.IDepartamentoService;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDesaparecidoService;
import com.digitalatmosphere.ausys.services.IEspecialService;
import com.digitalatmosphere.ausys.services.IFotoService;
import com.digitalatmosphere.ausys.services.IMunicipioService;
import com.digitalatmosphere.ausys.services.IPeritajeService;

@Controller
@RequestMapping("/write")
public class EstadisticaController {

	@Autowired
	private IDesaPeriService desaPeriS;

	@Autowired
	private IPeritajeService peritajeS;

	@Autowired
	private IDesaparecidoService desaparecidoS;

	@Autowired
	private IMunicipioService municipioS;

	@Autowired
	private IDepartamentoService departamentoS;

	@Autowired
	private IEspecialService especialS;

	@Autowired
	private IFotoService fotoS;

	public static String DirectorioArchivos = System.getProperty("user.dir") + "/src/main/webapp/imagedata";

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

	// Ingresar peritaje
	@RequestMapping("/ingresarPeritaje")
	public ModelAndView ingresarPeritaje() {
		ModelAndView mav = new ModelAndView();
		Peritaje peritaje = new Peritaje();

		List<Departamento> departamentos = null;
		List<Municipio> municipios = null;

		try {
			departamentos = departamentoS.findAll();
			municipios = municipioS.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Ingresar Peritajes");

		mav.addObject("departamentos", departamentos);
		mav.addObject("municipios", municipios);

		mav.addObject("peritaje", peritaje);
		mav.setViewName("IngresarPeritaje");
		return mav;
	}

	@RequestMapping("/validarPeritaje")
	public ModelAndView validarPeritaje(@Valid @ModelAttribute Peritaje peritaje, BindingResult result,
			@RequestParam(value = "edad_estimada") String edad_estimada,
			@RequestParam(value = "id_peritaje") String identificador,
			@RequestParam(value = "deptoSelect") String deptoSelect,
			@RequestParam(value = "municipio.id_municipio") String municipioSelect) {

		ModelAndView mav = new ModelAndView();
		Peritaje idperi = null;
		idperi = peritajeS.findOne(identificador);

		if (deptoSelect.equals("0") || municipioSelect.equals("0")) {
			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;

			System.out.println(deptoSelect);
			System.out.println(municipioSelect);

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Ingresar Peritajes");
			mav.addObject("deptoalert", "Por favor, seleccione alguna de las opciones.");
			mav.addObject("municipioalert",
					"Por favor, seleccione alguna de las opciones de departamento y posteriormente un municipio.");
			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);

			mav.addObject("peritaje", peritaje);
			mav.setViewName("IngresarPeritaje");
		}

		else if (idperi != null) {

			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Ingresar Peritajes");
			mav.addObject("iderror", "El identificador ya esta en uso.");
			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);

			mav.addObject("peritaje", peritaje);
			mav.setViewName("IngresarPeritaje");
		} else if (result.hasErrors()) {
			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Ingresar Peritajes ERROR");

			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);

			mav.addObject("peritaje", peritaje);
			mav.setViewName("IngresarPeritaje");
		} else {
			System.out.println("Fredy");
			try {
				peritajeS.save(peritaje);
			} catch (Exception e) {
				e.getStackTrace();
			}
			DesaPeri desaPeri = new DesaPeri();
			desaPeri.setFecha_registro(new java.util.Date());

			String id_peritaje = peritajeS.findOne(peritaje.getId_peritaje()).getId_peritaje();
			Integer age = Integer.parseInt(edad_estimada);
			mav.addObject("age", age);
			mav.addObject("id_peritaje", id_peritaje);
			mav.addObject("titulo", "Ingresar Peritajes");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarPeritaje2");
		}
		return mav;
	}

	@PostMapping("/validarPeritaje2")
	public ModelAndView validarPeritaje2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result,
			@ModelAttribute Peritaje peritaje, BindingResult result2, @RequestParam(value = "id_peritaje") String id,
			@RequestParam(value = "tipo_de_caso") String tipo_de_caso,
			@RequestParam(value = "edad_estimada") String edad_estimada) {

		ModelAndView mav = new ModelAndView();

		if (tipo_de_caso.equals("col2")) {
			System.out.println(tipo_de_caso);
			desaPeri.setFecha_registro(new java.util.Date());
			Integer age = Integer.parseInt(edad_estimada);
			mav.addObject("age", age);
			mav.addObject("alert", "Debe seleccionar una de las opciones de Tipo de Caso.");
			mav.addObject("titulo", "Ingresar Peritajes");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarPeritaje2");
		}
		if (result.hasErrors()) {
			System.out.println(tipo_de_caso);
			if (tipo_de_caso.equals("col2")) {

				mav.addObject("alert", "Debe seleccionar una de las opciones de Tipo de Caso.");
			}
			desaPeri.setFecha_registro(new java.util.Date());
			Integer age = Integer.parseInt(edad_estimada);
			mav.addObject("age", age);
			mav.addObject("titulo", "Ingresar Peritajes");
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

		try {
			departamentos = departamentoS.findAll();
			municipios = municipioS.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("titulo", "Ingresar Desaparecido");

		mav.addObject("departamentos", departamentos);
		mav.addObject("municipios", municipios);

		mav.addObject("desaparecido", desaparecido);

		mav.setViewName("ingresarDesaparecido");

		return mav;
	}

	@RequestMapping("/validarDesaparecido")
	public ModelAndView validarDesaparecido(@Valid @ModelAttribute Desaparecido desaparecido, BindingResult result,
			@RequestParam(value = "deptoSelect") String deptoSelect,
			@RequestParam(value = "municipio.id_municipio") String municipioSelect,
			@RequestParam(value = "fecha_nacimiento") String fecha_nacimiento) throws ParseException {

		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Ingresar Desaparecido");

			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);

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

			// Edad
			String s = fecha_nacimiento;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse(s);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);

			LocalDate l1 = LocalDate.of(year, month, date);
			LocalDate now1 = LocalDate.now();
			Period diff1 = Period.between(l1, now1);
			Integer age = diff1.getYears();

			mav.addObject("age", age);
			String id_desaparecido = desaparecidoS.findOne(desaparecido.getId_desaparecido()).getId_desaparecido();
			mav.addObject("id_desaparecido", id_desaparecido);
			mav.addObject("titulo", "Ingresar Desaparecido");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarDesaparecido2");
		}
		return mav;
	}

	@PostMapping("/validarDesaparecido2")
	public ModelAndView validarDesaparecido2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result,
			@ModelAttribute Desaparecido desaparecido, BindingResult result2,
			@RequestParam(value = "id_desaparecido") String id, @RequestParam(value = "age") String age) {

		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			String caso = "Desaparecido";
			desaPeri.setFecha_registro(new java.util.Date());
			desaPeri.setTipo_de_caso(caso);

			mav.addObject("age", age);
			mav.addObject("titulo", "Ingresar Desaparecido");
			mav.addObject("desaPeri", desaPeri);
			mav.setViewName("ingresarDesaparecido2");
		} else {
			Desaparecido desaparecido2 = new Desaparecido();

			// desaPeri.setId_peritaje(id);
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

	// EDITAR DESAPARECIDO

	@RequestMapping("/editar/desaparecido/{id_desaparecido}/{id_desaperi}")
	public ModelAndView editarDesaparecido(@RequestParam(value = "id_desaparecido") String id_desaparecido,
			@RequestParam(value = "id_desaperi") String id_desaperi) {
		ModelAndView mav = new ModelAndView();

		if (id_desaparecido != null) {
			Desaparecido desaparecido = desaparecidoS.findOne(id_desaparecido);

			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Editar Desaparecido");

			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);

			mav.addObject("desaparecido", desaparecido);
			mav.addObject("id_desaparecidoParam", id_desaparecido);
			mav.addObject("id_desaperiParam", id_desaperi);
			mav.setViewName("editarDesaparecido");
		} else {
			mav.setViewName("listaDesaparecidos");
		}

		return mav;
	}

	@RequestMapping("/editar/desaparecido2/{id_desaparecido}/{id_desaperi}")
	public ModelAndView editarDesaparecido2(@Valid @ModelAttribute Desaparecido desaparecido, BindingResult result,
			@RequestParam(value = "id_desaperi") String id_desaperi,
			@RequestParam(value = "id_desaparecido") String id_desaparecido,
			@RequestParam(value = "fecha_nacimiento") String fecha_nacimiento) throws ParseException {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			List<Departamento> departamentos = null;
			List<Municipio> municipios = null;

			try {
				departamentos = departamentoS.findAll();
				municipios = municipioS.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			mav.addObject("titulo", "Editar Desaparecido");

			mav.addObject("departamentos", departamentos);
			mav.addObject("municipios", municipios);

			mav.addObject("desaparecido", desaparecido);
			mav.addObject("id_desaparecidoParam", id_desaparecido);
			mav.addObject("id_desaperiParam", id_desaperi);
			mav.setViewName("editarDesaparecido");
		} else {
			try {
				desaparecidoS.save(desaparecido);
			} catch (Exception e) {
				e.getStackTrace();
			}

			if (id_desaperi != null) {
				DesaPeri desaPeri = desaPeriS.findOne(Integer.parseInt(id_desaperi));

				// Edad
				String s = fecha_nacimiento;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date d = sdf.parse(s);
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;
				int date = c.get(Calendar.DATE);

				LocalDate l1 = LocalDate.of(year, month, date);
				LocalDate now1 = LocalDate.now();
				Period diff1 = Period.between(l1, now1);
				Integer age = diff1.getYears();

				if (age < 18) {
					desaPeri.setDui(null);
				}

				mav.addObject("titulo", "Editar Desaparecido");
				mav.addObject("desaPeri", desaPeri);
				mav.addObject("age", age);

				mav.addObject("id_desaparecidoParam", id_desaparecido);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarDesaparecido2");

			}

		}

		return mav;
	}

	@RequestMapping("/validarEdicion/desaparecido2/{id_desaparecido}/{id_desaperi}")
	public ModelAndView validarEdicionDesaparecido2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result,
			@Valid @ModelAttribute Desaparecido desaparecido, BindingResult result2,
			@RequestParam(value = "id_desaperi") String id_desaperi,
			@RequestParam(value = "id_desaparecido") String id_desaparecido, @RequestParam(value = "age") String age) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {

			mav.addObject("titulo", "Editar Desaparecido");
			mav.addObject("age", age);
			if (Integer.parseInt(age) < 18) {
				desaPeri.setDui(null);
			}
			mav.addObject("desaPeri", desaPeri);
			mav.addObject("id_desaparecidoParam", id_desaparecido);
			mav.addObject("id_desaperiParam", id_desaperi);
			mav.setViewName("editarDesaparecido2");
		} else {
			Desaparecido desaparecido2 = new Desaparecido();
			desaPeri.setId_desaparecido(id_desaparecido);
			try {
				desaparecido2 = desaparecidoS.findOne(id_desaparecido);
			} catch (Exception e) {
				e.printStackTrace();
			}
			desaPeri.setDesaparecido(desaparecido2);
			try {
				desaPeriS.save(desaPeri);
			} catch (Exception e) {
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

	// EDITAR PERITAJE

	@RequestMapping("/editar/peritaje/{id_peritaje}/{id_desaperi}")
	public ModelAndView editarPeritaje(@RequestParam(value = "id_peritaje") String id_peritaje,
			@RequestParam(value = "id_desaperi") String id_desaperi) {
		ModelAndView mav = new ModelAndView();

		if (id_peritaje != null) {
			Peritaje peritaje = peritajeS.findOne(id_peritaje);

			mav.addObject("titulo", "Editar Peritaje");

			mav.addObject("peritaje", peritaje);
			mav.addObject("id_peritajeParam", id_peritaje);
			mav.addObject("id_desaperiParam", id_desaperi);
			mav.setViewName("editarPeritaje");
		} else {
			mav.setViewName("listaPeritajes");
		}

		return mav;
	}

	@RequestMapping("/editar/peritaje2/{id_peritaje}/{id_desaperi}")
	public ModelAndView editarDesaparecido2(@Valid @ModelAttribute Peritaje peritaje, BindingResult result,
			@RequestParam(value = "id_desaperi") String id_desaperi,
			@RequestParam(value = "id_peritaje") String id_peritaje,
			@RequestParam(value = "edad_estimada") String age) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {

			mav.addObject("titulo", "Editar Peritaje");
			
			mav.addObject("peritaje", peritaje);
			mav.addObject("id_peritajeParam", id_peritaje);
			mav.addObject("id_desaperiParam", id_desaperi);
			mav.setViewName("editarPeritaje");
		} else {
			try {
				peritajeS.save(peritaje);
			} catch (Exception e) {
				e.getStackTrace();
			}

			if (id_desaperi != null) {
				DesaPeri desaPeri = desaPeriS.findOne(Integer.parseInt(id_desaperi));
				if(Integer.parseInt(age) < 18) {
					desaPeri.setDui(null);
				}
				mav.addObject("titulo", "Editar Peritaje");
				mav.addObject("desaPeri", desaPeri);
				mav.addObject("age", age);
				mav.addObject("id_peritajeParam", id_peritaje);
				mav.addObject("id_desaperiParam", id_desaperi);
				mav.setViewName("editarPeritaje2");

			}

		}

		return mav;
	}

	@RequestMapping("/validarEdicion/peritaje2/{id_peritaje}/{id_desaperi}")
	public ModelAndView validarEdicionPeritaje2(@Valid @ModelAttribute DesaPeri desaPeri, BindingResult result,
			@Valid @ModelAttribute Peritaje peritaje, BindingResult result2,
			@RequestParam(value = "id_desaperi") String id_desaperi,
			@RequestParam(value = "id_peritaje") String id_peritaje,
			@RequestParam(value = "age") String age) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			if(Integer.parseInt(age) < 18) {
				desaPeri.setDui(null);
			}
			mav.addObject("titulo", "Editar Peritaje");
			mav.addObject("desaPeri", desaPeri);
			mav.addObject("age", age);
			mav.addObject("id_peritajeParam", id_peritaje);
			mav.addObject("id_desaperiParam", id_desaperi);
			mav.setViewName("editarPeritaje2");
		} else {
			Peritaje peritaje2 = new Peritaje();
			desaPeri.setId_peritaje(id_peritaje);
			try {
				peritaje2 = peritajeS.findOne(id_peritaje);
			} catch (Exception e) {
				e.printStackTrace();
			}
			desaPeri.setPeritaje(peritaje2);
			try {
				desaPeriS.save(desaPeri);
			} catch (Exception e) {
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
			mav.setViewName("redirect:/read/listaPeritajes");
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
			mav.setViewName("redirect:/read/listaDesaparecidos");
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
				mav.setViewName("redirect:/read/listaDesaparecidos");
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
				mav.setViewName("redirect:/read/listaPeritajes");
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
}
