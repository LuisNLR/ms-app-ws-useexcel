package pe.com.softlite.useexcel.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.softlite.useexcel.business.imp.TramiteBusinessRegisterImp;
import pe.com.softlite.useexcel.dto.TramiteToProcess;
import pe.com.softlite.useexcel.querys.TramiteDeriverQueryBusiness;
import pe.com.softlite.useexcel.querys.TramiteDevolverQueryBusiness;
import pe.com.softlite.useexcel.querys.TramiteFinishedQueryBusiness;

@RestController
@RequestMapping("/list")
public class ListController {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ListController.class);
	
	@Autowired
	private TramiteDeriverQueryBusiness tramiteDeriverQueryBusiness;
	
	@Autowired
	private TramiteDevolverQueryBusiness tramiteDevolverQueryBusiness;
	
	@Autowired
	private TramiteFinishedQueryBusiness tramiteFinishedQueryBusiness;
	
	@GetMapping("/getExcelTramiteByDeriver")
	public String getListTramiteByDeriver() {
		String correlationId = UUID.randomUUID().toString();
		tramiteDeriverQueryBusiness.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<TramiteToProcess> listTramiteDeriver;
		String archivoGenerado = null;
		try {
			listTramiteDeriver = tramiteDeriverQueryBusiness.getListTramite();
			archivoGenerado = tramiteDeriverQueryBusiness.generateExcelTramiteProcess(listTramiteDeriver);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
	@GetMapping("/getExcelTramiteByDevolver")
	public String getListTramiteByDevolver() {
		String correlationId = UUID.randomUUID().toString();
		tramiteDevolverQueryBusiness.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<TramiteToProcess> listTramiteDeriver;
		String archivoGenerado = null;
		try {
			listTramiteDeriver = tramiteDevolverQueryBusiness.getListTramite();
			archivoGenerado = tramiteDevolverQueryBusiness.generateExcelTramiteProcess(listTramiteDeriver);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
	@GetMapping("/getExcelTramiteByFinished")
	public String getListTramiteByFinished() {
		String correlationId = UUID.randomUUID().toString();
		tramiteFinishedQueryBusiness.asignCorrelationId(correlationId);
		
		LOGGER.info(":::: Proceso controller. insertTramitesByExcel. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<TramiteToProcess> listTramiteDeriver;
		String archivoGenerado = null;
		try {
			listTramiteDeriver = tramiteFinishedQueryBusiness.getListTramite();
			archivoGenerado = tramiteFinishedQueryBusiness.generateExcelTramiteProcess(listTramiteDeriver);
		} catch (Exception e) {
			LOGGER.error(":::: Proceso controller. insertTramitesByExcel. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		return "Excel procesado: " + archivoGenerado;
	}
	
}
