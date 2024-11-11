package pe.com.softlite.useexcel.dto;

public class TramiteResponseDTO {
	
	private String mensaje;
	private boolean flag;
	private Integer status;
	private TramiteQuerysDTO data;
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public TramiteQuerysDTO getData() {
		return data;
	}
	public void setData(TramiteQuerysDTO data) {
		this.data = data;
	}
	
}
