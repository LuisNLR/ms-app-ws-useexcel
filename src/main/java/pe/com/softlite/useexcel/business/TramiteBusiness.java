package pe.com.softlite.useexcel.business;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public interface TramiteBusiness {
	
	public List<HttpResponse<String>> readExcelAndProcesingTramites() throws Exception;
	
	public String generateExcelResponse(List<HttpResponse<String>> listResponse) throws IOException;
	
	public String getValue();
	
	public void asignCorrelationId(String correlationId);

}
