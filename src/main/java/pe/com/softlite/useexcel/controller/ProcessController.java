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
	public String processDeriverrTramite() {
		String correlationId = UUID.randomUUID().toString();
		tramiteBusinessDeriver.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<HttpResponse<String>> listTramitesRegistred;
		String archivoGenerado = null;
		try {
			listTramitesRegistred = tramiteBusinessDeriver.readExcelAndProcesingTramites();
			archivoGenerado = tramiteBusinessDeriver.generateExcelResponse(listTramitesRegistred);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
}
