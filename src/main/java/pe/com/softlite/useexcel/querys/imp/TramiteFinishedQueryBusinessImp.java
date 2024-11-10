package pe.com.softlite.useexcel.querys.imp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.com.softlite.useexcel.dto.TramiteToProcess;
import pe.com.softlite.useexcel.querys.TramiteFinishedQueryBusiness;
import pe.com.softlite.useexcel.utils.Utils;

@Service
public class TramiteFinishedQueryBusinessImp implements TramiteFinishedQueryBusiness {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(TramiteFinishedQueryBusinessImp.class);
	
	private String correlationId;
	
	@Value("${api.ws.sistradoc.getListTramiteFinished}")
	private String apiUrlSistradocGetListByFinished;
	
	@Value("${excel.input.path.finished}")
	private String pathPrevioFinished;
	
	@Value("${excel.input.file.finished}")
	private String filePrevioFinished;

	@Override
	public List<TramiteToProcess> getListTramite() throws IOException, InterruptedException {
		LOGGER.info(correlationId + ":::: Proceso obtener tramites a finalizar. Inicio :::: '{}' ", TramiteFinishedQueryBusinessImp.class.getName());
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create(apiUrlSistradocGetListByFinished))
//			    .uri(URI.create("http://localhost:8090/ms-app-ws-sistradoc/querys/getListTramiteByDeriver"))
			    .header("Content-Type", "application/json")
			    .version(HttpClient.Version.HTTP_1_1)
			    .GET()
			    .build();
		
		HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		ObjectMapper objectMapper = new ObjectMapper();
		TramiteToProcess[] listTramiteByDeriver = null;
		try {
			listTramiteByDeriver = objectMapper.readValue(httpResponse.body(), TramiteToProcess[].class);
			LOGGER.info(correlationId + ":::: Proceso obtener tramites a finalizar. Total registros :::: '{}' ", listTramiteByDeriver.length);
		} catch (Exception e) {
			LOGGER.error(correlationId + ":::: Proceso obtener tramites a finalizar. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		
		return Arrays.asList (listTramiteByDeriver);
	}

	@Override
	public String generateExcelTramiteProcess(List<TramiteToProcess> listTramiteFinished) throws IOException {
		LOGGER.info(correlationId + ":::: Proceso generar excel tramites a finalizar. Inicio :::: '{}' ", "generateExcelTramiteByDeriver");
		try {
//			FileOutputStream fileOutputStream = new FileOutputStream("D:\\Tools\\Tramite.excel\\Previo\\TramiteByDeriver.xlsx");
			FileOutputStream fileOutputStream = new FileOutputStream(pathPrevioFinished + Utils.getNewNameFile(filePrevioFinished));
			Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Response");
	        	        
	        //Cabecera
	        String[] cabecera = {"CODIGO TRAMITE", "MOTIVO DERIVACION", "FECHA INGRESO", "TIPO TRAMITE", "ASUNTO", 
	        					 "SOLICITANTE", "DEPENDENCIA ACTUAL", "DEPENDENCIA DESTINO"};
	        Row rowCabecera = sheet.createRow(0);
	        for(int i=0;i<cabecera.length;i++) {
	        	rowCabecera.createCell(i).setCellValue(cabecera[i]);
	        }
	        
	        //Llenado de datos
	        int fila=1;
	        for(TramiteToProcess tramiteDeriver : listTramiteFinished) {
	        	String[] data = {tramiteDeriver.getCodigoTramite(), 
	        					 null, 
	        					 tramiteDeriver.getFechaIngreso(), 
	        					 tramiteDeriver.getTipoTramite(), 
	        					 tramiteDeriver.getAsunto(),
	        					 tramiteDeriver.getSolicitante(),
	        					 tramiteDeriver.getDependenciaActual(),
	        					 tramiteDeriver.getDependenciaDestino()};
	        	
		        Row rowFila = sheet.createRow(fila);
		        
	        	for(int i=0;i<data.length;i++) {
	        		rowFila.createCell(i).setCellValue(data[i]);
	        		sheet.autoSizeColumn(i);
		        }
	        	fila = fila +1;
	        }
	        LOGGER.info(correlationId + ":::: Proceso generar excel tramites a finalizar. Inicio :::: '{}' ", fila -1);
	        workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();

		} catch (Exception e) {
			LOGGER.error(correlationId + ":::: Proceso generar excel tramites a finalizar. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		LOGGER.info(correlationId + ":::: Proceso generar excel tramites a finalizar. Final :::: '{}' ", "generateExcelTramiteByDeriver");
		return "Archivo generado";
	}

	@Override
	public void asignCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
}
