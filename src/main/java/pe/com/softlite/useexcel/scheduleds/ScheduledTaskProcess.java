package pe.com.softlite.useexcel.scheduleds;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import pe.com.softlite.useexcel.controller.ProcessController;

public class ScheduledTaskProcess {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ScheduledTaskProcess.class);
	
	@Autowired
	private ProcessController processController;
	
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long MINUTO_Y_MEDIO = MINUTO + MINUTO/2;
	private final long HORA = MINUTO * 60;
	
	@Scheduled(fixedDelay = MINUTO*3)
	public void taskRegisterTramite() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Proceso Registro programado. Inicio :::: '{}' ", ScheduledTaskProcess.class.getName());
		LOGGER.info(correlationId + ":::: Proceso Registro programado. Hora: :::: '{}' ", new Date());
		String result = processController.processRegisterTramite();
		LOGGER.info(correlationId + ":::: Proceso Registro programado. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Proceso Registro programado. Fin :::: '{}' ", "Hecho");
	}
	
	@Scheduled(initialDelay = MINUTO_Y_MEDIO, fixedDelay = MINUTO*3)
	public void taskDeriverTramite() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Proceso Derivar programado. Inicio :::: '{}' ", ScheduledTaskProcess.class.getName());
		LOGGER.info(correlationId + ":::: Proceso Derivar programado. Hora: :::: '{}' ", new Date());
		String result = processController.processDeriverTramite();
		LOGGER.info(correlationId + ":::: Proceso Derivar programado. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Proceso Derivar programado. Fin :::: '{}' ", "Hecho");
	}
	
	@Scheduled(initialDelay = MINUTO*2, fixedDelay = MINUTO*3)
	public void taskDevolverTramite() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Proceso Devolver programado. Inicio :::: '{}' ", ScheduledTaskProcess.class.getName());
		LOGGER.info(correlationId + ":::: Proceso Devolver programado. Hora: :::: '{}' ", new Date());
		String result = processController.processDevolverTramite();
		LOGGER.info(correlationId + ":::: Proceso Devolver programado. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Proceso Devolver programado. Fin :::: '{}' ", "Hecho");
	}

	@Scheduled(initialDelay = MINUTO*2, fixedDelay = MINUTO*3)
	public void taskFinalizarTramite() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Proceso Finalizar programado. Inicio :::: '{}' ", ScheduledTaskProcess.class.getName());
		LOGGER.info(correlationId + ":::: Proceso Finalizar programado. Hora: :::: '{}' ", new Date());
		String result = processController.processFinishedTramite();
		LOGGER.info(correlationId + ":::: Proceso Finalizar programado. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Proceso Finalizar programado. Fin :::: '{}' ", "Hecho");
	}
	
}
