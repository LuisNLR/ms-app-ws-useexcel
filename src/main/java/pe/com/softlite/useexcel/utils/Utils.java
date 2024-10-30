package pe.com.softlite.useexcel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static final String TIPO_PERSONA_PERSONA = "PERSONA";
	public static final String TIPO_PERSONA_ENTIDAD = "ENTIDAD";
	
	public static String getNewNameFile(String oldNameFile) {
		Date fechaActual = new Date();
    	String fechaFormateada = new SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual);
    	return oldNameFile.replace(".xlsx", "_" + fechaFormateada + ".xlsx");
	}

}
