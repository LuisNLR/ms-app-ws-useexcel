package pe.com.softlite.useexcel.business.imp;

import pe.com.softlite.useexcel.business.TramiteBusinessDevolver;
import pe.com.softlite.useexcel.dto.TramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteMovimientoDTO;
import pe.com.softlite.useexcel.utils.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class TramiteBusinessDevolverImp implements TramiteBusinessDevolver {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(TramiteBusinessDevolverImp.class);
	
	private String correlationId;

	@Value("${api.ws.sistradoc.devolver}")
	private String apiUrlSistradocDevolver;
	
	@Value("${excel.input.path.devolver}")
	private String inputPathDevolver;
	
	@Value("${excel.input.file.devolver}")
	private String inputFileDevolver;

	@Value("${excel.output.path.devolver}")
	private String outputPathDevolver;
	
	@Value("${excel.output.file.devolver}")
	private String outputFileDevolver;
	
	@SuppressWarnings("resource")
	@Override
	public List<HttpResponse<String>> readExcelAndProcesingTramites() throws Exception {
		LOGGER.info(correlationId + ":::: Proceso Leer excel y devolver tramites. Inicio :::: '{}' ", TramiteBusinessDevolverImp.class.getName());
		
		List<HttpResponse<String>> listResponse = new ArrayList<>();
		
		String excelFilePath = inputPathDevolver + inputFileDevolver;
//		String excelFilePath = "D:\\Tools\\Tramite.excel\\Input\\Register\\Input_registrar_tramite.xlsx";
		try {
			FileInputStream fileInputStream = new FileInputStream(excelFilePath);
			Workbook workbook = null;
			
			if(excelFilePath.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(fileInputStream);
			}else if(excelFilePath.endsWith(".xls")) {
				workbook = new HSSFWorkbook(fileInputStream);
			}else {
				throw new IllegalArgumentException("El archivo especificado no es un archivo excel");
			}
			
			Sheet sheet = workbook.getSheetAt(0);// Obtiene la primera hoja del archivo excel
			int fila = 0;
			for(Row row : sheet) {
				
				if(fila>0) {
					LOGGER.info(correlationId + ":::: Proceso Leer excel y devolver tramites. Nro Registro :::: '{}' ", fila);
					String codigoTramite = (String) Utils.getValue(row.getCell(0), fila, 0);
					String motivoEnvio = (String) Utils.getValue(row.getCell(1), fila, 1);

					TramiteDTO tramiteDto = new TramiteDTO(codigoTramite);
					TramiteMovimientoDTO tramiteMovimientoDto = new TramiteMovimientoDTO(motivoEnvio, tramiteDto);
					
					Gson gson = new Gson();
					
					LOGGER.info(correlationId + ":::: Proceso Leer excel y devolver tramites. Trama input.  '{}' ", gson.toJson(tramiteMovimientoDto));
					
					HttpClient httpClient = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
						    .uri(URI.create(apiUrlSistradocDevolver))
//						    .uri(URI.create("http://localhost:8090/ms-app-ws-sistradoc/api/registerTramite"))
						    .header("Content-Type", "application/json")
						    .version(HttpClient.Version.HTTP_1_1)
						    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(tramiteMovimientoDto)))
						    .build();
					
					HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
					LOGGER.info(correlationId + ":::: Proceso Leer excel y devolver tramites. status Respuesta .  '{}' ", httpResponse.statusCode());
					
					listResponse.add(httpResponse);
				}
				fila = fila +1;
			}
			workbook.close();
			fileInputStream.close();
			LOGGER.info(correlationId + ":::: Proceso Leer excel y devolver tramites. Total registros :::: '{}' ", fila - 1);
			
		} catch (Exception e) {
			LOGGER.error(correlationId + ":::: Proceso Leer excel y devolver tramites. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		LOGGER.info(correlationId + ":::: Proceso Leer excel y devolver tramites. Final :::: '{}' ", TramiteBusinessDevolverImp.class.getName());
		return listResponse;
	}
	
	public String getValue() {
		LOGGER.info(":::: Proceso DEMO. Inicio :::: '{}' ", TramiteBusinessDevolverImp.class.getName());
		return apiUrlSistradocDevolver;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String generateExcelResponse(List<HttpResponse<String>> listResponse) throws IOException {
		LOGGER.info(correlationId + ":::: Proceso Generar excel de respuesta de devolución de tramite. Inicio :::: '{}' ", TramiteBusinessDevolverImp.class.getName());
        try {
//			FileOutputStream fileOutputStream = new FileOutputStream("D:\\Tools\\Tramite.excel\\output\\OutputRegister.xlsx");
			FileOutputStream fileOutputStream = new FileOutputStream(outputPathDevolver + Utils.getNewNameFile(outputFileDevolver));
			Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Response");
	        	        
	        //Cabecera
	        String[] cabecera = {"RESULTADO", "CODIGO_TRAMITE", "TIPO TRAMITE", "ASUNTO", 
	        					 "SOLICITANTE", "DEPENDENCIA DESTINO"};
	        Row rowCabecera = sheet.createRow(0);
	        for(int i=0;i<cabecera.length;i++) {
	        	rowCabecera.createCell(i).setCellValue(cabecera[i]);
	        }
	        
	        //Llenado de datos
	        int fila=1;
	        for(HttpResponse<String>response : listResponse) {
	        	LOGGER.info(correlationId + ":::: Proceso Generar excel de respuesta de devolución de tramite. Trama response :::: '{}' ", response.body());
	        	Map<String, Object> mapResponse = new Gson().fromJson(response.body(), new TypeToken<HashMap<String, Object>>() {}.getType());
	        	String mensaje = (String) mapResponse.get("mensaje");
				Map<String, Object> mapData = (Map<String, Object>) mapResponse.get("data");
	        	String codigoTramite = (String) mapData.get("codigoTramite");
	        	String asunto = (String) mapData.get("asunto");
	        	String solicitante = (String) mapData.get("solicitante");
	        	String tipoTramite = (String) mapData.get("tipoTramite");
	        	String dependenciaDestino = (String) mapData.get("dependenciaDestino");
	        	
	        	
	        	String[] data = {mensaje, codigoTramite, tipoTramite, asunto, 
	        			solicitante, dependenciaDestino };
	        	Row rowFila = sheet.createRow(fila);
	        	
	        	for(int i=0;i<data.length;i++) {
	        		rowFila.createCell(i).setCellValue(data[i]);
	        		sheet.autoSizeColumn(i);
		        }
	        	fila = fila +1;
	        }
			workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();
	        
		} catch (FileNotFoundException e) {
			LOGGER.error(":::: Proceso Generar excel de respuesta de devolución de tramite. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
        LOGGER.info(correlationId + ":::: Proceso Generar excel de respuesta de devolución de tramite. Final :::: '{}' ", TramiteBusinessDevolverImp.class.getName());
		return "Archivo generado";
	}

	@Override
	public void asignCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
