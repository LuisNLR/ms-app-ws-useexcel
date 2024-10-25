package pe.com.softlite.useexcel.business.imp;

import pe.com.softlite.useexcel.business.TramiteBusiness;
import pe.com.softlite.useexcel.dto.SolicitanteDTO;
import pe.com.softlite.useexcel.dto.TipoTramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteRegisterDTO;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class TramiteBusinessImp implements TramiteBusiness {

	@Value("${api.ws.sistradoc.register}")
	private String apiUrlSistradocRegister;
	
	@Value("${excel.input.path.register}")
	private String inputPathRegister;
	
	@Value("${excel.input.file.register}")
	private String inputFileRegister;

	@SuppressWarnings("resource")
	@Override
	public List<HttpResponse<String>> registerTramite() throws Exception {
		
		List<HttpResponse<String>> listResponse = new ArrayList<>();
		
		String excelFilePath = inputPathRegister + inputFileRegister;
//		String excelFilePath = "D:\\Tools\\Tramite.excel.input\\Register\\Input_registrar_tramite.xlsx";
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
					
					HttpClient httpClient = HttpClient.newHttpClient();
					HttpRequest request = HttpRequest.newBuilder()
						    .uri(URI.create(apiUrlSistradocRegister))
//						    .uri(URI.create("http://localhost:8090/ms-app-ws-sistradoc/api/registerTramite"))
						    .header("Content-Type", "application/json")
						    .version(HttpClient.Version.HTTP_1_1)
						    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(tramiteRegisterDto)))
						    .build();
					
					HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
					listResponse.add(httpResponse);
					
				}
				
				fila = fila +1;
			}
			workbook.close();
			fileInputStream.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
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
		return apiUrlSistradocRegister;
	}

}
