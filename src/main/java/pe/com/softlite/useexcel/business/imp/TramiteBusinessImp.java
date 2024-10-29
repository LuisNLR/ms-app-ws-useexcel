package pe.com.softlite.useexcel.business.imp;

import pe.com.softlite.useexcel.business.TramiteBusiness;
import pe.com.softlite.useexcel.dto.SolicitanteDTO;
import pe.com.softlite.useexcel.dto.TipoTramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteRegisterDTO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
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
public class TramiteBusinessImp implements TramiteBusiness {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(TramiteBusinessImp.class);

	@Value("${api.ws.sistradoc.register}")
	private String apiUrlSistradocRegister;
	
	@Value("${excel.input.path.register}")
	private String inputPathRegister;
	
	@Value("${excel.input.file.register}")
	private String inputFileRegister;

	@SuppressWarnings("resource")
	@Override
	public List<HttpResponse<String>> readExcelAndRegisterTramites() throws Exception {
		String correlationId = UUID.randomUUID().toString();
		LOGGER.info(":::: Proceso Leer excel y registrar tramites. Inicio :::: '{}' ", TramiteBusinessImp.class.getName());
		
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
					LOGGER.info(":::: Proceso Leer excel y registrar tramites. Nro Registro :::: '{}' ", fila);
					Cell cellTramTipoTramite      = row.getCell(0);
					String tramTipoTramite        = cellTramTipoTramite!=null  ? (String) getValue(cellTramTipoTramite) : null;
					
					Cell cellTramAsunto           = row.getCell(1);
					String tramAsunto             = cellTramAsunto!=null ? (String) getValue(cellTramAsunto) : null;
					
					Cell cellTramTipoDocumento    = row.getCell(2);
					String tramTipoDocumento      = cellTramTipoDocumento!=null ? (String) getValue(cellTramTipoDocumento) : null;
					
					Cell cellTramNroFolio         = row.getCell(3);
					Double tramNroFolio           = cellTramNroFolio!=null ? (Double) getValue(cellTramNroFolio) : null; 
					
					Cell cellTramReferencia       = row.getCell(4);
					String tramReferencia         = cellTramReferencia!=null ? (String) getValue(cellTramReferencia) : null;
					
					Cell cellTramObservacion      = row.getCell(5);
					String tramObservacion        = cellTramObservacion!=null ? (String) getValue(cellTramObservacion) : null;
					
					Cell cellSolicTipoSolicitante = row.getCell(6);
					String solicTipoSolicitante   = cellSolicTipoSolicitante!=null ? (String) getValue(cellSolicTipoSolicitante) : null;
					
					Cell cellSolicTipoDocumento   = row.getCell(7);
					String solicTipoDocumento     = cellSolicTipoDocumento!=null ? (String) getValue(cellSolicTipoDocumento) : null;
					
					Cell cellSolicNroDocumento    = row.getCell(8);
					String solicNroDocumento      = cellSolicNroDocumento!=null ? cellSolicNroDocumento.getStringCellValue() : null;
					
					Cell cellSolicNombre          = row.getCell(9);
					String solicNombre            = cellSolicNombre!=null ? (String) getValue(cellSolicNombre) : null;
					
					Cell cellSolicApellidoPaterno = row.getCell(10);
					String solicApellidoPaterno   = cellSolicApellidoPaterno!=null ? (String) getValue(cellSolicApellidoPaterno) : null;
					
					Cell cellSolicApellidoMaterno = row.getCell(11);
					String SolicApellidoMaterno   = cellSolicApellidoMaterno!=null ? (String) getValue(cellSolicApellidoMaterno) : null;
					
					Cell cellSolicTelefono        = row.getCell(12);
					String solicTelefono          = cellSolicTelefono!=null ? (String) getValue(cellSolicTelefono) : null;
					
					Cell cellSolicCorreo          = row.getCell(13);
					String solicCorreo            = cellSolicCorreo!=null ? (String) getValue(cellSolicCorreo) : null;
					
					Cell cellSolicRepresentante   = row.getCell(14);
					String solicRepresentante     = cellSolicRepresentante!=null ? (String) getValue(cellSolicRepresentante) : null;
					
					String idTipoTramiteStr = tramTipoTramite.substring(0, tramTipoTramite.indexOf("."));
					String nombreTipoTramite = tramTipoTramite.substring(tramTipoTramite.indexOf(".")+1);
					Integer nroFolio = tramNroFolio.intValue();
					
					TipoTramiteDTO tipoTramiteDto = new TipoTramiteDTO(Long.valueOf(idTipoTramiteStr), nombreTipoTramite);
					SolicitanteDTO solicitanteDto = new SolicitanteDTO(solicNroDocumento, solicTipoDocumento, solicTipoSolicitante, solicNombre, solicApellidoPaterno, SolicApellidoMaterno, solicCorreo, solicTelefono, solicRepresentante);
					
					TramiteDTO tramiteDto = new TramiteDTO(tramAsunto, tramObservacion, nroFolio, tramReferencia, tramTipoDocumento, tipoTramiteDto, solicitanteDto);
					TramiteRegisterDTO tramiteRegisterDto = new TramiteRegisterDTO();
					tramiteRegisterDto.setTramiteDto(tramiteDto);
					
					Gson gson = new Gson();
					
					LOGGER.info(":::: Proceso Leer excel y registrar tramites. Trama input.  '{}' ", gson.toJson(tramiteRegisterDto));
					
					HttpClient httpClient = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
						    .uri(URI.create(apiUrlSistradocRegister))
//						    .uri(URI.create("http://localhost:8090/ms-app-ws-sistradoc/api/registerTramite"))
						    .header("Content-Type", "application/json")
						    .version(HttpClient.Version.HTTP_1_1)
						    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(tramiteRegisterDto)))
						    .build();
					
					HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
					LOGGER.info(":::: Proceso Leer excel y registrar tramites. status Respuesta .  '{}' ", httpResponse.statusCode());
					
					listResponse.add(httpResponse);
					
				}
				
				fila = fila +1;
			}
			workbook.close();
			fileInputStream.close();
			LOGGER.info(":::: Proceso Leer excel y registrar tramites. Total registros :::: '{}' ", fila);
			
		} catch (Exception e) {
			LOGGER.error(correlationId + ":::: Proceso Leer excel y registrar tramites. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		LOGGER.info(":::: Proceso Leer excel y registrar tramites. Final :::: '{}' ", TramiteBusinessImp.class.getName());
		return listResponse;
	}
	
	private Object getValue(Cell cell) {
		String  valueString;
		Double  valueDouble;
		Date    valueDate;
		Boolean valueBoolean;
		switch(cell.getCellType()) {
			case STRING:
				valueString = cell.getStringCellValue();
				return valueString;
			case NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)) {
					valueDate = cell.getDateCellValue();
					return valueDate;
				}else {
					valueDouble = cell.getNumericCellValue();
					return valueDouble;
				}
			case BOOLEAN:
				valueBoolean = cell.getBooleanCellValue();
				return valueBoolean;
			case _NONE:
				return "";
			case BLANK:
				return "";
			case ERROR:
				return "";
			default:
				return "";
		}
	}
	
	public String getValue() {
		LOGGER.info(":::: Proceso DEMO. Inicio :::: '{}' ", TramiteBusinessImp.class.getName());
		return apiUrlSistradocRegister;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String generateExcelResponseInsert(List<HttpResponse<String>> listResponse) {
		
        try {
			FileOutputStream fileOutputStream = new FileOutputStream("D:\\Tools\\Tramite.excel\\output\\OutputRegister.xlsx");
			Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Response");
	        	        
	        //Cabecera
	        String[] cabecera = {"RESULTADO", "CODIGO_TRAMITE", "TIPO TRAMITE", "ASUNTO", 
	        					 "TIPO DOC", "NRO DOC", "NOMBRE SOLICITANTE"};
	        Row rowCabecera = sheet.createRow(0);
	        for(int i=0;i<cabecera.length;i++) {
	        	rowCabecera.createCell(i).setCellValue(cabecera[i]);
	        }
	        
	        //Lenado de datos
	        int fila=1;
	        for(HttpResponse<String>response : listResponse) {
	        	Map<String, Object> mapResponse = new Gson().fromJson(response.body(), new TypeToken<HashMap<String, Object>>() {}.getType());
	        	String mensaje = (String) mapResponse.get("mensaje");
//	        	boolean flagResult = (boolean) mapResponse.get("flag");
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
	        
	        try {
				workbook.write(fileOutputStream);
				workbook.close();
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
		} catch (FileNotFoundException e) {
			LOGGER.error(":::: Proceso Generar excel de respuesta de registro de tramite. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return "Archivo generado";
	}

}
