package pe.com.softlite.useexcel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TramiteRegisterDTO {
	
	private TramiteDTO tramiteDto;
	
	public TramiteDTO getTramiteDto() {
		return tramiteDto;
	}
	public void setTramiteDto(TramiteDTO tramiteDto) {
		this.tramiteDto = tramiteDto;
	}

}
