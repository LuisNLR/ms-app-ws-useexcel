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

import pe.com.softlite.useexcel.dto.TramiteQuerysDTO;
import pe.com.softlite.useexcel.querys.TramiteDevolverQueryBusiness;
import pe.com.softlite.useexcel.utils.Utils;

@Service
public class TramiteDevolverQueryBusinessImp implements TramiteDevolverQueryBusiness {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(TramiteDevolverQueryBusinessImp.class);
	
	private String correlationId;
	
	@Value("${api.ws.sistradoc.getListTramiteDevolver}")
	private String apiUrlSistradocGetListByDevolver;
	
	@Value("${excel.input.path.devolver}")
	private String pathPrevioDevolver;
	
	@Value("${excel.input.file.devolver}")
	private String filePrevioDevolver;

	@Override
	public List<TramiteQuerysDTO> getListTramite() throws IOException, InterruptedException {
		LOGGER.info(correlationId + ":::: Proceso obtener tramites a devolver. Inicio :::: '{}' ", TramiteDevolverQueryBusinessImp.class.getName());
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			    .uri(URI.create(apiUrlSistradocGetListByDevolver))
//			    .uri(URI.create("http://localhost:8090/ms-app-ws-sistradoc/querys/getListTramiteByDeriver"))
			    .header("Content-Type", "application/json")
			    .version(HttpClient.Version.HTTP_1_1)
			    .GET()
			    .build();
		
		HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		ObjectMapper objectMapper = new ObjectMapper();
		TramiteQuerysDTO[] listTramiteByDeriver = null;
		try {
			listTramiteByDeriver = objectMapper.readValue(httpResponse.body(), TramiteQuerysDTO[].class);
			LOGGER.info(correlationId + ":::: Proceso obtener tramites a devolver. Total registros :::: '{}' ", listTramiteByDeriver.length);
		} catch (Exception e) {
			LOGGER.error(correlationId + ":::: Proceso obtener tramites a devolver. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		
		return Arrays.asList (listTramiteByDeriver);
	}

	@Override
	public String generateExcelTramiteProcess(List<TramiteQuerysDTO> listTramiteDevolver) throws IOException {
		LOGGER.info(correlationId + ":::: Proceso generar excel tramites a devolver. Inicio :::: '{}' ", "generateExcelTramiteByDeriver");
		try {
//			FileOutputStream fileOutputStream = new FileOutputStream("D:\\Tools\\Tramite.excel\\Previo\\TramiteByDeriver.xlsx");
			FileOutputStream fileOutputStream = new FileOutputStream(pathPrevioDevolver + Utils.getNewNameFile(filePrevioDevolver));
			Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Response");
	        	        
	        //Cabecera
	        String[] cabecera = {"CODIGO TRAMITE", 
								 "MOTIVO DEVOLUCION", 
								 "TIPO TRAMITE", 
								 "ASUNTO", 
								 "SOLICITANTE", 
								 "DEPENDENCIA ACTUAL", 
								 "DEPENDENCIA DESTINO"};
	        Row rowCabecera = sheet.createRow(0);
	        for(int i=0;i<cabecera.length;i++) {
	        	rowCabecera.createCell(i).setCellValue(cabecera[i]);
	        }
	        
	        //Llenado de datos
	        int fila=1;
	        for(TramiteQuerysDTO tramiteQuerys : listTramiteDevolver) {
	        	String[] data = {tramiteQuerys.getCodigoTramite(), 
	        					 null, 
	        					 tramiteQuerys.getFechaIngreso(),
	        					 tramiteQuerys.getTipoTramite(), 
	        					 tramiteQuerys.getAsunto(),
	        					 tramiteQuerys.getSolicitante(),
	        					 tramiteQuerys.getDependenciaActual(),
	        					 tramiteQuerys.getDependenciaDestino()};
	        	
		        Row rowFila = sheet.createRow(fila);
		        
	        	for(int i=0;i<data.length;i++) {
	        		rowFila.createCell(i).setCellValue(data[i]);
	        		sheet.autoSizeColumn(i);
		        }
	        	fila = fila +1;
	        }
	        LOGGER.info(correlationId + ":::: Proceso generar excel tramites a devolver. Inicio :::: '{}' ", fila -1);
	        workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();

		} catch (Exception e) {
			LOGGER.error(correlationId + ":::: Proceso generar excel tramites a devolver. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		LOGGER.info(correlationId + ":::: Proceso generar excel tramites a devolver. Final :::: '{}' ", "generateExcelTramiteByDeriver");
		return "Archivo generado";
	}

	@Override
	public void asignCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
}
