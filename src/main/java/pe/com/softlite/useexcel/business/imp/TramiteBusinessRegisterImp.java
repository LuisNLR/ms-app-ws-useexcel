package pe.com.softlite.useexcel.business.imp;

import pe.com.softlite.useexcel.business.TramiteBusiness;
import pe.com.softlite.useexcel.dto.SolicitanteDTO;
import pe.com.softlite.useexcel.dto.TipoTramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteRegisterDTO;
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
public class TramiteBusinessRegisterImp implements TramiteBusiness {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(TramiteBusinessRegisterImp.class);
	
	private String correlationId;

	@Value("${api.ws.sistradoc.register}")
	private String apiUrlSistradocRegister;
	
	@Value("${excel.input.path.register}")
	private String inputPathRegister;
	
	@Value("${excel.input.file.register}")
	private String inputFileRegister;

	@Value("${excel.output.path.register}")
	private String outputPathRegister;
	
	@Value("${excel.output.file.register}")
	private String outputFileRegister;
	
	@SuppressWarnings("resource")
	@Override
	public List<HttpResponse<String>> readExcelAndProcesingTramites() throws Exception {
		LOGGER.info(correlationId + ":::: Proceso Leer excel y registrar tramites. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		
		List<HttpResponse<String>> listResponse = new ArrayList<>();
		
		String excelFilePath = inputPathRegister + inputFileRegister;
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
					LOGGER.info(correlationId + ":::: Proceso Leer excel y registrar tramites. Nro Registro :::: '{}' ", fila);
					String tramTipoTramite        = (String) Utils.getValue(row.getCell(0), fila, 0);
					
					String idTipoTramiteStr = tramTipoTramite.substring(0, tramTipoTramite.indexOf("."));
					String nombreTipoTramite = tramTipoTramite.substring(tramTipoTramite.indexOf(".")+1);
					
					Double tramNroFolio = (Double) Utils.getValue(row.getCell(3), fila, 3);
					Integer nroFolio = tramNroFolio.intValue();
					
					TipoTramiteDTO tipoTramiteDto = new TipoTramiteDTO(Long.valueOf(idTipoTramiteStr), 
																	   nombreTipoTramite);
					
					SolicitanteDTO solicitanteDto = new SolicitanteDTO((String) Utils.getValue(row.getCell(8), fila, 8), 
																	   (String) Utils.getValue(row.getCell(7), fila, 7), 
																	   (String) Utils.getValue(row.getCell(6), fila, 6), 
																	   (String) Utils.getValue(row.getCell(9), fila, 9), 
																	   (String) Utils.getValue(row.getCell(10), fila, 10), 
																	   (String) Utils.getValue(row.getCell(11), fila, 11), 
																	   (String) Utils.getValue(row.getCell(13), fila, 13), 
																	   (String) Utils.getValue(row.getCell(12), fila, 12), 
																	   (String) Utils.getValue(row.getCell(14), fila, 14));
					
					TramiteDTO tramiteDto = new TramiteDTO((String) Utils.getValue(row.getCell(1), fila, 1), 
														   (String) Utils.getValue(row.getCell(5), fila, 5), 
														   nroFolio, 
														   (String) Utils.getValue(row.getCell(4), fila, 4), 
														   (String) Utils.getValue(row.getCell(2), fila, 2), 
														   tipoTramiteDto, 
														   solicitanteDto);
					
					TramiteRegisterDTO tramiteRegisterDto = new TramiteRegisterDTO();
					tramiteRegisterDto.setTramiteDto(tramiteDto);
					
					Gson gson = new Gson();
					
					LOGGER.info(correlationId + ":::: Proceso Leer excel y registrar tramites. Trama input.  '{}' ", gson.toJson(tramiteRegisterDto));
					
					HttpClient httpClient = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
						    .uri(URI.create(apiUrlSistradocRegister))
//						    .uri(URI.create("http://localhost:8090/ms-app-ws-sistradoc/api/registerTramite"))
						    .header("Content-Type", "application/json")
						    .version(HttpClient.Version.HTTP_1_1)
						    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(tramiteRegisterDto)))
						    .build();
					
					HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
					LOGGER.info(correlationId + ":::: Proceso Leer excel y registrar tramites. status Respuesta .  '{}' ", httpResponse.statusCode());
					
					listResponse.add(httpResponse);
				}
				fila = fila +1;
			}
			workbook.close();
			fileInputStream.close();
			LOGGER.info(correlationId + ":::: Proceso Leer excel y registrar tramites. Total registros :::: '{}' ", fila - 1);
			
		} catch (Exception e) {
			LOGGER.error(correlationId + ":::: Proceso Leer excel y registrar tramites. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		LOGGER.info(correlationId + ":::: Proceso Leer excel y registrar tramites. Final :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		return listResponse;
	}
	
	public String getValue() {
		LOGGER.info(":::: Proceso DEMO. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		return apiUrlSistradocRegister;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String generateExcelResponse(List<HttpResponse<String>> listResponse) throws IOException {
		LOGGER.info(correlationId + ":::: Proceso Generar excel de respuesta de registro de tramite. Inicio :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
        try {
//			FileOutputStream fileOutputStream = new FileOutputStream("D:\\Tools\\Tramite.excel\\output\\OutputRegister.xlsx");
			FileOutputStream fileOutputStream = new FileOutputStream(outputPathRegister + Utils.getNewNameFile(outputFileRegister));
			Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Response");
	        	        
	        //Cabecera
	        String[] cabecera = {"RESULTADO", "CODIGO_TRAMITE", "TIPO TRAMITE", "ASUNTO", 
	        					 "TIPO DOC", "NRO DOC", "NOMBRE SOLICITANTE"};
	        Row rowCabecera = sheet.createRow(0);
	        for(int i=0;i<cabecera.length;i++) {
	        	rowCabecera.createCell(i).setCellValue(cabecera[i]);
	        }
	        
	        //Llenado de datos
	        int fila=1;
	        for(HttpResponse<String>response : listResponse) {
	        	LOGGER.info(correlationId + ":::: Proceso Generar excel de respuesta de registro de tramite. Trama response :::: '{}' ", response.body());
	        	Map<String, Object> mapResponse = new Gson().fromJson(response.body(), new TypeToken<HashMap<String, Object>>() {}.getType());
	        	String mensaje = (String) mapResponse.get("mensaje");
				Map<String, Object> mapData = (Map<String, Object>) mapResponse.get("data");
	        	String codigoTramite = (String) mapData.get("codigoTramite");
	        	String asunto = (String) mapData.get("asunto");
	        	Map<String, Object> mapTipoTramite = (Map<String, Object>) mapData.get("tipoTramiteDto");
	        	Double idTipoTramite = (Double) mapTipoTramite.get("idTipoTramite");
	        	String nombreTipoTramite = (String) mapTipoTramite.get("nombreTipoTramite");
	        	
	        	Map<String, Object> mapSolicitante = (Map<String, Object>) mapData.get("solicitanteDto");
	        	String tipoSolicitante = (String) mapSolicitante.get("tipoSolicitante");
	        	String tipoDocumentoSolicitante = (String) mapSolicitante.get("tipoDocumento");
	        	String numeroSolicitante = (String) mapSolicitante.get("numeroDocumento");
	        	String nombreSolicitante = (String) mapSolicitante.get("nombreSolicitante");
	        	String apellidoPaternoSolicitante = (String) mapSolicitante.get("apellidoPaterno");
	        	String apellidoMaternoSolicitante = (String) mapSolicitante.get("apellidoMaterno");
	        	
	        	String[] data = {mensaje, codigoTramite, idTipoTramite + ". " + nombreTipoTramite, asunto, 
   					 tipoDocumentoSolicitante, numeroSolicitante, tipoSolicitante.equals("PERSONA")? nombreSolicitante + " " + apellidoPaternoSolicitante + " " + apellidoMaternoSolicitante : nombreSolicitante };
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
			LOGGER.error(":::: Proceso Generar excel de respuesta de registro de tramite. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
        LOGGER.info(correlationId + ":::: Proceso Generar excel de respuesta de registro de tramite. Final :::: '{}' ", TramiteBusinessRegisterImp.class.getName());
		return "Archivo generado";
	}

	@Override
	public void asignCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
