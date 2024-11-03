package pe.com.softlite.useexcel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(Utils.class);
	
	public static final String TIPO_PERSONA_PERSONA = "PERSONA";
	public static final String TIPO_PERSONA_ENTIDAD = "ENTIDAD";
	
	public static String getNewNameFile(String oldNameFile) {
		Date fechaActual = new Date();
    	String fechaFormateada = new SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual);
    	return oldNameFile.replace(".xlsx", "_" + fechaFormateada + ".xlsx");
	}
	
	public static Object getValue(Cell cell) {
		String  valueString;
		Double  valueDouble;
		Date    valueDate;
		Boolean valueBoolean;
		try {
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
		} catch (Exception e) {
			LOGGER.info( ":::: Proceso obtener valor de celda. Intentar obtener valor celda:::: '{}' ", Utils.class.getName());
			LOGGER.error(":::: Proceso obtener valor de celda. Error Mensaje :::: '{}' ", e.getMessage());
			LOGGER.error(e.getLocalizedMessage(), e);
			return null;
		}
		
	}

}
