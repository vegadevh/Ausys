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

import com.digitalatmosphere.ausys.domains.Departamento;
import com.digitalatmosphere.ausys.domains.DesaPeri;
import com.digitalatmosphere.ausys.domains.Desaparecido;
import com.digitalatmosphere.ausys.domains.Division;
import com.digitalatmosphere.ausys.domains.Especial;
import com.digitalatmosphere.ausys.domains.Foto;
import com.digitalatmosphere.ausys.domains.Municipio;
import com.digitalatmosphere.ausys.domains.Peritaje;
import com.digitalatmosphere.ausys.dto.CasosDTO;
import com.digitalatmosphere.ausys.dto.DesaparecidoDTO;
import com.digitalatmosphere.ausys.dto.EspecialDTO;
import com.digitalatmosphere.ausys.dto.HombresMujeresDTO;
import com.digitalatmosphere.ausys.dto.PeritajeDTO;
import com.digitalatmosphere.ausys.dto.RegistroDTO;
import com.digitalatmosphere.ausys.dto.fotografiaDTO;
import com.digitalatmosphere.ausys.services.IDepartamentoService;
import com.digitalatmosphere.ausys.services.IDesaPeriService;
import com.digitalatmosphere.ausys.services.IDesaparecidoService;
import com.digitalatmosphere.ausys.services.IDivisionService;
import com.digitalatmosphere.ausys.services.IEspecialService;
import com.digitalatmosphere.ausys.services.IFotoService;
import com.digitalatmosphere.ausys.services.IMunicipioService;
import com.digitalatmosphere.ausys.services.IPeritajeService;

@Controller
public class MainController {
	
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
	
	@Autowired
	private IEspecialService especialS;
	
	@Autowired
	private IFotoService fotoS;
	
	public static String DirectorioArchivos = System.getProperty("user.dir")+ "/src/main/webapp/imagedata";
	
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

	//BUSQUEDA DE REPORTES
	
	@RequestMapping("/buscarPeritajes")
	public ModelAndView buscarPeritajes(){
		ModelAndView mav = new ModelAndView();

		mav.addObject("titulo", "Busqueda de Registros de Peritajes");
		mav.setViewName("busquedaP");
		return mav;
	}
	
	@RequestMapping(value="/busquedaPeritajes", params="action=buscarnombre")
	public ModelAndView BuscarNombre(@RequestParam(value="nombre_registro") String nombre, @RequestParam(value="sexo_registro") String sexo,@RequestParam(value="type_registro") String type) {
		ModelAndView mav = new ModelAndView();
		List<PeritajeDTO> desaPeriL = null;
		String newType = "Selecciona una opción";
		if(type.equals(newType)) {
			type = "";
		}
		if(sexo != null) {
			if(sexo.equals("Ambos")) {
				sexo = "";
			}
		}else {
			sexo = "";
		}
		try {
			desaPeriL = desaPeriS.buscarNombrePeritaje(nombre.toLowerCase(), sexo.toLowerCase(), type);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(desaPeriL != null && desaPeriL.size() !=0 ) {
			mav.addObject("titulo", "Lista de Peritajes");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaPeritajes");
			
		}else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con el nombre: ".concat(nombre));
			mav.setViewName("busquedaP");
		}
		return mav;
	}
	//Buscar por Identificador
	@RequestMapping(value="/busquedaPeritajes", params="action=buscaridentificador")
	public ModelAndView BuscarIdentificador(@RequestParam(value="id_registro") String id) {
		ModelAndView mav = new ModelAndView();
		List<PeritajeDTO> desaPeriL = null;
		
		try {
			desaPeriL = desaPeriS.buscarIdPeritaje(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(desaPeriL != null && desaPeriL.size() !=0 ) {
			mav.addObject("titulo", "Lista de Peritajes");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaPeritajes");
			
		}else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con el identificador: ".concat(id));
			mav.setViewName("busquedaP");
		}
		return mav;
	}
	//DESAPARECIDOS
	@RequestMapping("/buscarDesaparecidos")
	public ModelAndView buscarDesaparecidos(){
		ModelAndView mav = new ModelAndView();

		mav.addObject("titulo", "Busqueda de Desaparecidos");
		mav.setViewName("busquedaD");
		return mav;
	}
	
	@RequestMapping(value="/busquedaDesaparecidos", params="action=buscarnombre")
	public ModelAndView BuscarNombreD(@RequestParam(value="nombre_registro") String nombre, @RequestParam(value="sexo_registro") String sexo) {
		ModelAndView mav = new ModelAndView();
		List<DesaparecidoDTO> desaPeriL = null;
		if(sexo != null) {
			if(sexo.equals("Ambos")) {
				sexo = "";
			}
		}else {
			sexo = "";
		}
		try {
			desaPeriL = desaPeriS.buscarNombreDesaparecido(nombre.toLowerCase(), sexo.toLowerCase());
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(desaPeriL != null && desaPeriL.size() !=0 ) {
			mav.addObject("titulo", "Lista de Desaparecidos");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaDesaparecidos");
			
		}else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con los datos proporcionados");
			mav.setViewName("busquedaD");
		}
		return mav;
	}
	//Buscar por Identificador
	@RequestMapping(value="/busquedaDesaparecidos", params="action=buscaridentificador")
	public ModelAndView BuscarIdentificadorD(@RequestParam(value="id_registro") String id) {
		ModelAndView mav = new ModelAndView();
		List<DesaparecidoDTO> desaPeriL = null;
		try {
			desaPeriL = desaPeriS.buscarIdDesaparecido(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(desaPeriL != null && desaPeriL.size() !=0 ) {
			mav.addObject("titulo", "Lista de Desaparecidos");
			mav.addObject("desaPeriL", desaPeriL);
			mav.setViewName("listaDesaparecidos");
			
		}else {
			mav.addObject("titulo", "Busqueda de Registros");
			mav.addObject("mensaje", "No se ha encontrado un registro con el identificador: ".concat(id));
			mav.setViewName("busquedaD");
		}
		return mav;
	}
	
	//VER REGISTTRO INDIVIDUAL
	
	@RequestMapping("/verRegistro/{id_peritaje}/{id_desaperi}")
	public ModelAndView verRegistroPeritaje(@PathVariable(value="id_peritaje") String id_peritaje, @PathVariable(value="id_desaperi") String id_desaperi, @RequestParam(value="peritajeName") String peritajeName, @RequestParam(value="peritajeLastname") String peritajeLastname) {
		ModelAndView mav = new ModelAndView();
		
		List<RegistroDTO> registro = null;
		List<fotografiaDTO> fotos = null;
		List<EspecialDTO> especiales = null;
		List<DesaparecidoDTO> relacion = null;
		try {
			especiales = especialS.especialPeritaje(id_peritaje);
			fotos = fotoS.fotosPeritaje(id_peritaje);
			registro = desaPeriS.verRegistroPeritaje(id_peritaje, id_desaperi);
			relacion = desaPeriS.PeritajesXDesaparecidos(peritajeName, peritajeLastname);
			System.out.println(registro);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(registro != null && registro.size() !=0 ) {
			mav.addObject("titulo", "Registro: ".concat(id_peritaje));
			mav.addObject("relacion", relacion);
			mav.addObject("registro", registro);
			mav.addObject("especiales", especiales);
			mav.addObject("fotos", fotos);
			mav.addObject("val", "Peritaje");
			mav.setViewName("verRegistro");
		}else {
			mav.addObject("titulo", "Lista de Registros");
			mav.setViewName("listaPeritajes");
		}
		
		return mav;
	}
	
	@RequestMapping("/verRegistroD/{id_desaparecido}/{id_desaperi}")
	public ModelAndView verRegistroDesaparecido(@PathVariable(value="id_desaparecido") String id_desaparecido, @PathVariable(value="id_desaperi") String id_desaperi) {
		ModelAndView mav = new ModelAndView();
		
		List<RegistroDTO> registro = null;
		List<fotografiaDTO> fotos = null;
		List<EspecialDTO> especiales = null;
		try {
			especiales = especialS.especialDesaparecido(id_desaparecido);
			fotos = fotoS.fotosDesaparecido(id_desaparecido);
			registro = desaPeriS.verRegistroDesaparecido(id_desaparecido, id_desaperi);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(registro != null && registro.size() !=0 ) {
			mav.addObject("titulo", "Registro: ".concat(id_desaparecido));
			mav.addObject("registro", registro);
			mav.addObject("fotos", fotos);
			mav.addObject("especiales", especiales);
			mav.addObject("val", "Desaparecido");
			mav.setViewName("verRegistro");
		}else {
			mav.addObject("titulo", "Lista de Desaparecidos");
			mav.setViewName("listaDesaparecidos");
		}
		
		return mav;
	}
	
	//AGREGAR ESPECIAL
	@RequestMapping("/especial/{id}/{id_registro}")
	public ModelAndView agregarEspecial(@RequestParam(value="id") String id) {
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
	public ModelAndView validarEspecial(@Valid @ModelAttribute Especial especial, BindingResult result,@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			
			
			mav.addObject("titulo", "Ingresar marca especial");
			mav.addObject("especial", especial);
			mav.addObject("id", id);
			mav.addObject("val", "Peritaje");
			mav.setViewName("ingresarEspecial");
		}else {
			Peritaje peritaje = new Peritaje();
			especial.setId_peritaje(id);
			peritaje = peritajeS.findOne(id);
			especial.setPeritaje(peritaje);
			
			try {
				especialS.save(especial);
			}catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("redirect:/listaPeritajes");
		}
		return mav;
	}
	
	@RequestMapping("/especialD/{id}/{id_registro}")
	public ModelAndView agregarEspecialD(@RequestParam(value="id") String id) {
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
	public ModelAndView validarEspecialD(@Valid @ModelAttribute Especial especial, BindingResult result,@PathVariable("id") String id) {
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			
			//CAMBIAR LOGICA
			mav.addObject("titulo", "Ingresar marca especial");
			mav.addObject("especial", especial);
			mav.addObject("id", id);
			mav.addObject("val", "Desaparecido");
			mav.setViewName("ingresarEspecial");
		}else {
			Desaparecido desaparecido = new Desaparecido();
			especial.setId_desaparecido(id);
			desaparecido = desaparecidoS.findOne(id);
			especial.setDesaparecido(desaparecido);
			
			try {
				especialS.save(especial);
			}catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("redirect:/listaDesaparecidos");
		}
		return mav;
	}
	
	//AGREGAR FOTOS
	@RequestMapping("/fotografia/D/{id_registro}/{id_desaperi}")
	public ModelAndView addFotoDesaparecido(@RequestParam(value="id") String id) {
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
	public ModelAndView addFotoPeritaje(@RequestParam(value="id") String id) {
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
	public ModelAndView subirFoto(@Valid @ModelAttribute Foto foto, BindingResult result, @PathVariable(value="id_registro") String id, @RequestParam(value="img") MultipartFile file) {
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			
			mav.addObject("titulo", "Ingresar Foto");
			mav.addObject("foto", foto);
			String alert = "El campo no puede ser vacio. Ingrese una foto.";
			mav.addObject("id", id);
			mav.addObject("alert", alert);
			mav.addObject("val", "Desaparecido");
			mav.setViewName("ingresarFoto");
		}else {
			StringBuilder fileName = new StringBuilder();
			
			Date date = Calendar.getInstance().getTime();  
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
			String strDate = dateFormat.format(date);
			String myDate = strDate.replaceAll(":","");
			
			try {
				String filename = myDate.concat(id.concat(file.getOriginalFilename().substring(file.getOriginalFilename().length()-4)));
				Path fileNameAndPath = Paths.get(DirectorioArchivos, filename);
				
				try {
					Files.write(fileNameAndPath, file.getBytes());
				}catch (IOException e) {
					e.printStackTrace();
				}
				foto.setFoto(filename);
				
				Desaparecido desaparecido = new Desaparecido();
				foto.setId_desaparecido(id);
				desaparecido = desaparecidoS.findOne(id);
				foto.setDesaparecido(desaparecido);
				
				try {
					fotoS.save(foto);
				}catch (Exception e) {
					e.printStackTrace();
				}
				mav.setViewName("redirect:/listaDesaparecidos");
			}catch(StringIndexOutOfBoundsException e) {
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
	public ModelAndView subirFotoP(@Valid @ModelAttribute Foto foto, BindingResult result, @PathVariable(value="id_registro") String id, @RequestParam(value="img") MultipartFile file) {
		ModelAndView mav = new ModelAndView();
		
		if(result.hasErrors()) {
			
			mav.addObject("titulo", "Ingresar Foto");
			mav.addObject("foto", foto);
			String alert = "El campo no puede ser vacio. Ingrese una foto.";
			mav.addObject("id", id);
			mav.addObject("alert", alert);
			mav.addObject("val", "Peritaje");
			mav.setViewName("ingresarFoto");
		}else {
			StringBuilder fileName = new StringBuilder();
			
			Date date = Calendar.getInstance().getTime();  
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
			String strDate = dateFormat.format(date);
			String myDate = strDate.replaceAll(":","");
			
			try {
				String filename = myDate.concat(id.concat(file.getOriginalFilename().substring(file.getOriginalFilename().length()-4)));
				Path fileNameAndPath = Paths.get(DirectorioArchivos, filename);
				
				try {
					Files.write(fileNameAndPath, file.getBytes());
				}catch (IOException e) {
					e.printStackTrace();
				}
				foto.setFoto(filename);
				
				Peritaje peritaje= new Peritaje();
				foto.setId_peritaje(id);
				peritaje = peritajeS.findOne(id);
				foto.setPeritaje(peritaje);
				
				try {
					fotoS.save(foto);
				}catch (Exception e) {
					e.printStackTrace();
				}
				mav.setViewName("redirect:/listaPeritajes");
			}catch(StringIndexOutOfBoundsException e) {
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
