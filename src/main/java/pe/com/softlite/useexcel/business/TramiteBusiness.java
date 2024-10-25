package pe.com.softlite.useexcel.business;

import java.net.http.HttpResponse;
import java.util.List;

public interface TramiteBusiness {
	
	public List<HttpResponse<String>> registerTramite() throws Exception;
	
	public String getValue();

}
