package pe.com.softlite.useexcel.business.imp;

import pe.com.softlite.useexcel.business.TramiteBusiness;
import pe.com.softlite.useexcel.dto.SolicitanteDTO;
import pe.com.softlite.useexcel.dto.TipoTramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteDTO;
import pe.com.softlite.useexcel.dto.TramiteRegisterDTO;
import pe.com.softlite.useexcel.utils.ValidateService;
import java.io.FileInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
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

	@Override
	public ValidateService registerTramite() throws Exception {
		
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
					
					
					System.out.println("tramTipoTramite: " + tramTipoTramite + " \n" + 
									   "tramAsunto: " + tramAsunto + " \n" + 
									   "tramTipoDocumento: " + tramTipoDocumento + " \n" + 
									   "tramNroFolio : " + tramNroFolio + " \n" + 
									   "tramReferencia: " + tramReferencia + " \n" +
									   "tramObservacion: " + tramObservacion + " \n" +
									   "solicTipoSolicitante: " + solicTipoSolicitante + " \n" + 
									   "solicTipoDocumento: " + solicTipoDocumento + " \n" +
									   "solicNroDocumento: " + solicNroDocumento + " \n" +
									   "solicNombre: " + solicNombre + " \n" +
									   "solicApellidoPaterno: " + solicApellidoPaterno + " \n" +
									   "SolicApellidoMaterno: " + SolicApellidoMaterno + " \n" +
									   "solicTelefono: " + solicTelefono + " \n" +
									   "solicCorreo: " + solicCorreo + " \n" + 
									   "solicRepresentante: " + solicRepresentante
										);
										
					String idTipoTramiteStr = tramTipoTramite.substring(0, tramTipoTramite.indexOf("."));
					String nombreTipoTramite = tramTipoTramite.substring(tramTipoTramite.indexOf(".")+1);
					Integer nroFolio = tramNroFolio.intValue();
					
					TipoTramiteDTO tipoTramiteDto = new TipoTramiteDTO(Long.valueOf(idTipoTramiteStr), nombreTipoTramite);
					SolicitanteDTO solicitanteDto = new SolicitanteDTO(solicNroDocumento, solicTipoDocumento, solicTipoSolicitante, solicNombre, solicApellidoPaterno, SolicApellidoMaterno, solicCorreo, solicTelefono, solicRepresentante);
					
					TramiteDTO tramiteDto = new TramiteDTO(tramAsunto, tramObservacion, nroFolio, tramReferencia, solicTipoDocumento, tipoTramiteDto, solicitanteDto);
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
					
					System.out.println("\n");
//					System.out.println(httpResponse.statusCode());
//					System.out.println(httpResponse.body());
					System.out.println("url de properti: " + apiUrlSistradocRegister);
					System.out.println("\n");
					
//					Map responseMap = gson.fromJson(httpResponse.body(), HashMap.class);
//					String mensaje = (String) responseMap.get("mensaje");
//					System.out.println("DEMO: mensaje. " + mensaje);
					
				}
				
				fila = fila +1;
			}
			workbook.close();
			fileInputStream.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String args[]) {
		try {
			new TramiteBusinessImp().registerTramite();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
