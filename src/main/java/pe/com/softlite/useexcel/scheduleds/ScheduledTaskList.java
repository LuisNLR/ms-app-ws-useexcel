package pe.com.softlite.useexcel.scheduleds;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.com.softlite.useexcel.controller.ListController;

@Component
@EnableScheduling
public class ScheduledTaskList {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(ScheduledTaskList.class);
//	private static final String descripLoggerDeriver = ":::: Task de trámites a derivar. ";
//	private static final String descripLoggerDevolver = ":::: Task de trámites a devolver. ";
//	private static final String descripLoggerFinished = ":::: Task de trámites a finalizar. ";
	
	@Autowired
	private ListController listController;
	
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long MINUTO_x2 = MINUTO * 2;
	private final long MINUTO_Y_MEDIO = MINUTO + MINUTO/2;
	
	@Scheduled(initialDelay = MINUTO*2, fixedDelay = MINUTO*2)//initialDelay = MINUTO*2, fixedDelay = MINUTO_x2
	public void taskGetListTramiteDeriver() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Task de trámites a derivar. Inicio :::: '{}' ", ScheduledTaskList.class.getName());
		LOGGER.info(correlationId + ":::: Task de trámites a derivar. Hora: :::: '{}' ", new Date());
		String result = listController.getListTramiteByDeriver();
		LOGGER.info(correlationId + ":::: Task de trámites a derivar. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Task de trámites a derivar. Fin :::: '{}' ", "Hecho");
	}
	
	@Scheduled(initialDelay = MINUTO*19, fixedDelay = MINUTO*19)
	public void taskGetListTramiteDevolver() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Task de trámites a devolver. Inicio :::: '{}' ", ScheduledTaskList.class.getName());
		LOGGER.info(correlationId + ":::: Task de trámites a devolver. Hora: :::: '{}' ", new Date());
		String result = listController.getListTramiteByDevolver();
		LOGGER.info(correlationId + ":::: Task de trámites a devolver. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Task de trámites a devolver. Fin :::: '{}' ", "Hecho");
	}
	
	@Scheduled(initialDelay = MINUTO*17, fixedDelay = MINUTO*17)
	public void taskGetListTramiteFinished() {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(correlationId + ":::: Task de trámites a finalizar. Inicio :::: '{}' ", ScheduledTaskList.class.getName());
		LOGGER.info(correlationId + ":::: Task de trámites a finalizar. Hora: :::: '{}' ", new Date());
		String result = listController.getListTramiteByFinished();
		LOGGER.info(correlationId + ":::: Task de trámites a finalizar. Resultado: :::: '{}' ", result);
		LOGGER.info(correlationId + ":::: Task de trámites a finalizar. Fin :::: '{}' ", "Hecho");
	}
	

}
