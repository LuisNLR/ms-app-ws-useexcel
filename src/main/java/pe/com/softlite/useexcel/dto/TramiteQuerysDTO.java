package pe.com.softlite.useexcel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TramiteQuerysDTO {

	// Datos Tramite
	private String codigoTramite;
	private String fechaIngreso;
	private String asunto;
	private String observacion;
	private String tipoTramite;
	private String solicitante;
	private String estadoTramite;
	// Datos Movimientos
	private String motivoEnvio;
	private String estadoMovimiento;
	private String dependenciaActual;
	private String dependenciaDestino;

	public TramiteQuerysDTO() {
		super();
	}

	public TramiteQuerysDTO(String codigoTramite, String asunto, String tipoTramite, String solicitante,
				String motivoEnvio, String dependenciaActual, String dependenciaDestino) {
		super();
		this.codigoTramite = codigoTramite;
		this.asunto = asunto;
		this.tipoTramite = tipoTramite;
		this.solicitante = solicitante;
		this.motivoEnvio = motivoEnvio;
		this.dependenciaActual = dependenciaActual;
		this.dependenciaDestino = dependenciaDestino;
	}

	public TramiteQuerysDTO(String codigoTramite, String asunto, String tipoTramite, String solicitante,
				String estadoTramite) {
		super();
		this.codigoTramite = codigoTramite;
		this.asunto = asunto;
		this.tipoTramite = tipoTramite;
		this.solicitante = solicitante;
		this.estadoTramite = estadoTramite;
	}

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

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getEstadoTramite() {
		return estadoTramite;
	}

	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}

	public String getMotivoEnvio() {
		return motivoEnvio;
	}

	public void setMotivoEnvio(String motivoEnvio) {
		this.motivoEnvio = motivoEnvio;
	}

	public String getEstadoMovimiento() {
		return estadoMovimiento;
	}

	public void setEstadoMovimiento(String estadoMovimiento) {
		this.estadoMovimiento = estadoMovimiento;
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
