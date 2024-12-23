package pe.com.softlite.useexcel.controller;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.softlite.useexcel.business.TramiteBusinessDeriver;
import pe.com.softlite.useexcel.business.TramiteBusinessDevolver;
import pe.com.softlite.useexcel.business.TramiteBusinessFinished;
import pe.com.softlite.useexcel.business.TramiteBusinessRegister;
import pe.com.softlite.useexcel.business.imp.TramiteBusinessRegisterImp;

@RestController
@RequestMapping("/api")
public class ProcessController {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	private TramiteBusinessRegister tramiteBusinessRegister;
	
	@Autowired
	private TramiteBusinessDeriver tramiteBusinessDeriver;
	
	@Autowired
	private TramiteBusinessDevolver tramiteBusinessDevolver;
	
	@Autowired
	private TramiteBusinessFinished tramiteBusinessFinished;
	
	@GetMapping("/testUrlByExcel")
	public String testURL() {
		return "Property: " + tramiteBusinessRegister.getValue();
	}
	
	@GetMapping("/processRegisterTramitesByExcel")
	public String processRegisterTramite() {
		String correlationId = UUID.randomUUID().toString();
		tramiteBusinessRegister.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<HttpResponse<String>> listTramitesRegistred;
		String archivoGenerado = null;
		try {
			listTramitesRegistred = tramiteBusinessRegister.readExcelAndProcesingTramites();
			archivoGenerado = tramiteBusinessRegister.generateExcelResponse(listTramitesRegistred);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
	@GetMapping("/processDeriverTramitesByExcel")
	public String processDeriverTramite() {
		String correlationId = UUID.randomUUID().toString();
		tramiteBusinessDeriver.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. deriverTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<HttpResponse<String>> listTramitesRegistred;
		String archivoGenerado = null;
		try {
			listTramitesRegistred = tramiteBusinessDeriver.readExcelAndProcesingTramites();
			if(!listTramitesRegistred.isEmpty()) {
				archivoGenerado = tramiteBusinessDeriver.generateExcelResponse(listTramitesRegistred);
			}else {
				archivoGenerado = "No se generó el archivo";
			}
			
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. deriverTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
			archivoGenerado = e.getMessage();
		}
		return "Excel procesado: " + archivoGenerado;
		
	}
	
	@GetMapping("/processDevolverTramitesByExcel")
	public String processDevolverTramite() {
		String correlationId = UUID.randomUUID().toString();
		tramiteBusinessDevolver.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. devolverTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<HttpResponse<String>> listTramitesRegistred;
		String archivoGenerado = null;
		try {
			listTramitesRegistred = tramiteBusinessDevolver.readExcelAndProcesingTramites();
			archivoGenerado = tramiteBusinessDevolver.generateExcelResponse(listTramitesRegistred);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. devolverTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
	@GetMapping("/processFinishedTramitesByExcel")
	public String processFinishedTramite() {
		String correlationId = UUID.randomUUID().toString();
		tramiteBusinessFinished.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. finishedTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<HttpResponse<String>> listTramitesRegistred;
		String archivoGenerado = null;
		try {
			listTramitesRegistred = tramiteBusinessFinished.readExcelAndProcesingTramites();
			archivoGenerado = tramiteBusinessFinished.generateExcelResponse(listTramitesRegistred);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. finishedTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
}
