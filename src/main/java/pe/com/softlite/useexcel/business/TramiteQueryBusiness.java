package pe.com.softlite.useexcel.business;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import pe.com.softlite.useexcel.dto.TramiteByDeriver;

public interface TramiteQueryBusiness {
	
	public List<TramiteByDeriver> getListTramiteByDeriver()  throws IOException, InterruptedException;
	
	public String generateExcelTramiteByDeriver(List<TramiteByDeriver> listTramiteDeriver) throws IOException;

	public void asignCorrelationId(String correlationId);
	
}
