package pe.com.softlite.useexcel.querys;

import java.io.IOException;
import java.util.List;

import pe.com.softlite.useexcel.dto.TramiteQuerysDTO;

public interface TramiteQueryBusiness {
	
	public List<TramiteQuerysDTO> getListTramite()  throws IOException, InterruptedException;
	
	public String generateExcelTramiteProcess(List<TramiteQuerysDTO> listTramiteDeriver) throws IOException;

	public void asignCorrelationId(String correlationId);
	
}
