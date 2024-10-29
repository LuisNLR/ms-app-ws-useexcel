package pe.com.softlite.useexcel.business;

import java.net.http.HttpResponse;
import java.util.List;

public interface TramiteBusiness {
	
	public List<HttpResponse<String>> readExcelAndRegisterTramites() throws Exception;
	
	public String generateExcelResponseInsert(List<HttpResponse<String>> listResponse);
	
	public String getValue();

}
