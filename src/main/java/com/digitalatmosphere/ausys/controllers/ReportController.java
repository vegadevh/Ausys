package com.digitalatmosphere.ausys.controllers;

import java.io.FileInputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.digitalatmosphere.ausys.services.IDesaPeriService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReportController {

	@Autowired
	private IDesaPeriService desaPeriS;
	
	@GetMapping("/pdf/peritajes")
	public ResponseEntity<byte[]> generarPDF() throws Exception, JRException{
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(desaPeriS.findAllPeritajes());
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/findAllPeritajes.jrxml"));
		
		HashMap<String, Object> map = new HashMap<>();
		JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
		
		byte[] data = JasperExportManager.exportReportToPdf(report);
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline.filename=peritajes.pdf");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
		
	}
	
}
