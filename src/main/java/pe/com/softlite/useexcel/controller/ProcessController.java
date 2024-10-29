package pe.com.softlite.useexcel.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.softlite.useexcel.business.TramiteBusiness;
import pe.com.softlite.useexcel.business.imp.TramiteBusinessImp;

@RestController
@RequestMapping("/api")
public class ProcessController {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	private TramiteBusiness tramiteBusiness;
	
	@GetMapping("/testUrlByExcel")
	public String testURL() {
		return "Property: " + tramiteBusiness.getValue();
	}
	
	@GetMapping("/insertTramitesByExcel")
	public String registerTramite() {
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessImp.class.getName());
		
		List<HttpResponse<String>> listTramitesRegistred;
		String archivoGenerado = null;
		try {
			listTramitesRegistred = tramiteBusiness.readExcelAndRegisterTramites();
			archivoGenerado = tramiteBusiness.generateExcelResponseInsert(listTramitesRegistred);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
}
