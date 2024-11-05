package pe.com.softlite.useexcel.dto;

public class TramiteByDeriver {
	
	private String codigoTramite;
	private String fechaIngreso;
	private String tipoTramite;
	private String asunto;
	private String solicitante;
	private String dependenciaActual;
	private String dependenciaDestino;
	
	public String getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(String codigoTramite) {
		this.codigoTramite = codigoTramite;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getDependenciaActual() {
		return dependenciaActual;
	}
	public void setDependenciaActual(String dependenciaActual) {
		this.dependenciaActual = dependenciaActual;
	}
	public String getDependenciaDestino() {
		return dependenciaDestino;
	}
	public void setDependenciaDestino(String dependenciaDestino) {
		this.dependenciaDestino = dependenciaDestino;
	}

}
