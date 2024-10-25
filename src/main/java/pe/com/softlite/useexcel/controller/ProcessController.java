package pe.com.softlite.useexcel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.com.softlite.useexcel.business.TramiteBusiness;

@RestController
@RequestMapping("/api")
public class ProcessController {
	
	@Autowired
	private TramiteBusiness tramiteBusiness;
	
	@GetMapping("/testUrlByExcel")
	public String testURL() {
		return "Property: " + tramiteBusiness.getValue();
	}
	
	@GetMapping("/insertTramitesByExcel")
	public String registerTramite() {
		return "Property: " + tramiteBusiness.getValue();
	}

}
