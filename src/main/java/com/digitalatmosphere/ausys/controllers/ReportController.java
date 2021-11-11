package com.digitalatmosphere.ausys.controllers;

import java.io.FileInputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digitalatmosphere.ausys.services.IDesaPeriService;

//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/pdf")
public class ReportController {

//	@Autowired
//	private IDesaPeriService desaPeriS;
//	
//	@GetMapping("/peritajes")
//	public ResponseEntity<byte[]> peritajesPDF() throws Exception, JRException{
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findAllPeritajes());
//		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllPeritajes.jrxml"));
//		
//		HashMap<String, Object> map = new HashMap<>();
//		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//		
//		byte[] data = JasperExportManager.exportReportToPdf(report);
//		
//		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//		headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=peritajes.pdf");
//		
//		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//		
//	}
//	
//	@GetMapping("/desaparecidos")
//	public ResponseEntity<byte[]> desaparecidosPDF() throws Exception, JRException{
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findAllDesaparecidos());
//		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllDesaparecidos.jrxml"));
//		
//		HashMap<String, Object> map = new HashMap<>();
//		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//		
//		byte[] data = JasperExportManager.exportReportToPdf(report);
//		
//		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//		headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=desaparecidos.pdf");
//		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//		
//	}
//	
//	@GetMapping("/filtro")
//	public ResponseEntity<byte[]> listaRegistrosPDF(String keyword, String type, String sexo, String fechaI, String fechaF) throws Exception, JRException{
//		byte[] data = null;
//		org.springframework.http.HttpHeaders headers = null;
//		ResponseEntity<byte[]> response = null;
//		
//		String newType = "Selecciona una opción";
//		if(sexo != null) {
//			if(sexo.equals("Ambos")) {
//				sexo = "";
//			}
//		}else {
//			sexo = "";
//		}
//		if (fechaI == null || fechaF == null){
//			fechaI = fechaF = "";
//		}
//		System.out.println("sexo:"+sexo);
//		try {
//			if (!fechaI.equals("") && !fechaF.equals("") ){
//				keyword = keyword.toLowerCase();
//				if(type != null && !type.equals(newType)) {
//					JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findByDateBetweenAndAbove(keyword, type, sexo, fechaI, fechaF));
//					JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllFilter.jrxml"));
//					
//					HashMap<String, Object> map = new HashMap<>();
//					JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//					
//					data = JasperExportManager.exportReportToPdf(report);
//					
//					headers = new org.springframework.http.HttpHeaders();
//					headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=filtrarPorfechas.pdf");
//					response = ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//				}
//				else{
//					JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findByDateBetweenAndAbove(keyword, "", sexo, fechaI, fechaF));
//					JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllFilter.jrxml"));
//					
//					HashMap<String, Object> map = new HashMap<>();
//					JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//					
//					data = JasperExportManager.exportReportToPdf(report);
//					
//					headers = new org.springframework.http.HttpHeaders();
//					headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=filtrarPorfechas.pdf");
//					response = ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//				}
//			}else if(type != null && !type.equals(newType)) {
//				keyword = keyword.toLowerCase();
//				
//				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findByKeywordAndtipe(keyword,type, sexo));
//				JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllFilter.jrxml"));
//				
//				HashMap<String, Object> map = new HashMap<>();
//				JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//				
//				data = JasperExportManager.exportReportToPdf(report);
//				
//				headers = new org.springframework.http.HttpHeaders();
//				headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=elfiltro.pdf");
//				response = ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//				
//			}else if(keyword != null) {
//				keyword = keyword.toLowerCase();
//				
//				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findByKeyword(keyword, sexo));
//				JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllFilter.jrxml"));
//				
//				HashMap<String, Object> map = new HashMap<>();
//				JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//				
//				data = JasperExportManager.exportReportToPdf(report);
//				
//				headers = new org.springframework.http.HttpHeaders();
//				headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=find.pdf");
//				response = ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//				
//			}else {
//				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findAll());
//				JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllFilter.jrxml"));
//				
//				HashMap<String, Object> map = new HashMap<>();
//				JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
//				
//				data = JasperExportManager.exportReportToPdf(report);
//				
//				headers = new org.springframework.http.HttpHeaders();
//				headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=listaRegistros.pdf");
//				
//				response = ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return response;
//	}
}
