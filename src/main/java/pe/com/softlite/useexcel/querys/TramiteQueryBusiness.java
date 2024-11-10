package pe.com.softlite.useexcel.querys;

import java.io.IOException;
import java.util.List;

import pe.com.softlite.useexcel.dto.TramiteToProcess;

public interface TramiteQueryBusiness {
	
	public List<TramiteToProcess> getListTramite()  throws IOException, InterruptedException;
	
	public String generateExcelTramiteProcess(List<TramiteToProcess> listTramiteDeriver) throws IOException;

	public void asignCorrelationId(String correlationId);
	
}
