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

import pe.com.softlite.useexcel.business.TramiteBusiness;
import pe.com.softlite.useexcel.business.TramiteQueryBusiness;
import pe.com.softlite.useexcel.business.imp.TramiteBusinessRegisterImp;
import pe.com.softlite.useexcel.dto.TramiteByDeriver;

@RestController
@RequestMapping("/api")
public class ProcessController {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	private TramiteBusiness tramiteBusiness;
	
	@Autowired
	private TramiteQueryBusiness tramiteQueryBusiness;
	
	@GetMapping("/testUrlByExcel")
	public String testURL() {
		return "Property: " + tramiteBusiness.getValue();
	}
	
	@GetMapping("/insertTramitesByExcel")
	public String registerTramite() {
		String correlationId = UUID.randomUUID().toString();
		tramiteBusiness.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<HttpResponse<String>> listTramitesRegistred;
		String archivoGenerado = null;
		try {
			listTramitesRegistred = tramiteBusiness.readExcelAndProcesingTramites();
			archivoGenerado = tramiteBusiness.generateExcelResponse(listTramitesRegistred);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
	@GetMapping("/getExcelTramiteByDeriver")
	public String getListTramiteByDeriver() {
		String correlationId = UUID.randomUUID().toString();
		tramiteBusiness.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<TramiteByDeriver> listTramiteDeriver;
		String archivoGenerado = null;
		try {
			listTramiteDeriver = tramiteQueryBusiness.getListTramiteByDeriver();
			archivoGenerado = tramiteQueryBusiness.generateExcelTramiteByDeriver(listTramiteDeriver);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
}
