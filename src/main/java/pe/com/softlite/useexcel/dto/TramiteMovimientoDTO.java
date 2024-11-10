package pe.com.softlite.useexcel.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TramiteMovimientoDTO {
	
	private Long idMovimiento;
	private Date fechaDerivacionPosterior;
	private Date fechaDerivacion;
	private Date fechaRecepcion;
	private String motivoEnvio;
	private Integer numeroMovimiento;
	private Integer pasoActual;
	private String repecpcionDocumento;
	private String ubicacionActual;
	private String estadoMovimiento;
	private DependenciaDTO dependenciaDto;
	private TramiteDTO tramiteDto;
	
	public TramiteMovimientoDTO() {
		super();
	}
	public TramiteMovimientoDTO(String motivoEnvio, TramiteDTO tramiteDto) {
		super();
		this.motivoEnvio = motivoEnvio;
		this.tramiteDto = tramiteDto;
	}
	public TramiteMovimientoDTO(String motivoEnvio, DependenciaDTO dependenciaDto, TramiteDTO tramiteDto) {
		super();
		this.motivoEnvio = motivoEnvio;
		this.dependenciaDto = dependenciaDto;
		this.tramiteDto = tramiteDto;
	}

	public Long getIdMovimiento() {
		return idMovimiento;
	}
	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}
	public Date getFechaDerivacionPosterior() {
		return fechaDerivacionPosterior;
	}
	public void setFechaDerivacionPosterior(Date fechaDerivacionPosterior) {
		this.fechaDerivacionPosterior = fechaDerivacionPosterior;
	}
	public Date getFechaDerivacion() {
		return fechaDerivacion;
	}
	public void setFechaDerivacion(Date fechaDerivacion) {
		this.fechaDerivacion = fechaDerivacion;
	}
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public String getMotivoEnvio() {
		return motivoEnvio;
	}
	public void setMotivoEnvio(String motivoEnvio) {
		this.motivoEnvio = motivoEnvio;
	}
	public Integer getNumeroMovimiento() {
		return numeroMovimiento;
	}
	public void setNumeroMovimiento(Integer numeroMovimiento) {
		this.numeroMovimiento = numeroMovimiento;
	}
	public Integer getPasoActual() {
		return pasoActual;
	}
	public void setPasoActual(Integer pasoActual) {
		this.pasoActual = pasoActual;
	}
	public String getRepecpcionDocumento() {
		return repecpcionDocumento;
	}
	public void setRepecpcionDocumento(String repecpcionDocumento) {
		this.repecpcionDocumento = repecpcionDocumento;
	}
	public String getUbicacionActual() {
		return ubicacionActual;
	}
	public void setUbicacionActual(String ubicacionActual) {
		this.ubicacionActual = ubicacionActual;
	}
	public String getEstadoMovimiento() {
		return estadoMovimiento;
	}
	public void setEstadoMovimiento(String estadoMovimiento) {
		this.estadoMovimiento = estadoMovimiento;
	}
	public DependenciaDTO getDependenciaDto() {
		return dependenciaDto;
	}
	public void setDependenciaDto(DependenciaDTO dependenciaDto) {
		this.dependenciaDto = dependenciaDto;
	}
	public TramiteDTO getTramiteDto() {
		return tramiteDto;
	}
	public void setTramiteDto(TramiteDTO tramiteDto) {
		this.tramiteDto = tramiteDto;
	}

}
