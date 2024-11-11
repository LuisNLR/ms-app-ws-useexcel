package pe.com.softlite.useexcel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TramiteDTO {
	
	private String codigoTramite;
//	private String archivado;
	private String asunto;
	private String observacion;
//	private String motivoAnulacion;
//	private Date fechaRegistro;
//	private Date fechaTermino;
	private Integer numeroFolios;
	private String referencia;
	private String estadoTramite;
	private String tipoDocumento;
	private TipoTramiteDTO tipoTramiteDto;
	private SolicitanteDTO solicitanteDto;
	
	public TramiteDTO() {
		super();
	}

	public TramiteDTO(String codigoTramite) {
		super();
		this.codigoTramite = codigoTramite;
	}

	public TramiteDTO(String codigoTramite, String estadoTramite) {
		super();
		this.codigoTramite = codigoTramite;
		this.estadoTramite = estadoTramite;
	}

	public TramiteDTO(String asunto, String observacion, Integer numeroFolios, String referencia, String tipoDocumento,
			TipoTramiteDTO tipoTramiteDto, SolicitanteDTO solicitanteDto) {
		super();
		this.asunto = asunto;
		this.observacion = observacion;
		this.numeroFolios = numeroFolios;
		this.referencia = referencia;
		this.tipoDocumento = tipoDocumento;
		this.tipoTramiteDto = tipoTramiteDto;
		this.solicitanteDto = solicitanteDto;
	}


	public String getCodigoTramite() {
		return codigoTramite;
	}
	public void setCodigoTramite(String codigoTramite) {
		this.codigoTramite = codigoTramite;
	}
//	public String getArchivado() {
//		return archivado;
//	}
//	public void setArchivado(String archivado) {
//		this.archivado = archivado;
//	}
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
//	public String getMotivoAnulacion() {
//		return motivoAnulacion;
//	}
//	public void setMotivoAnulacion(String motivoAnulacion) {
//		this.motivoAnulacion = motivoAnulacion;
//	}
//	public Date getFechaRegistro() {
//		return fechaRegistro;
//	}
//	public void setFechaRegistro(Date fechaRegistro) {
//		this.fechaRegistro = fechaRegistro;
//	}
//	public Date getFechaTermino() {
//		return fechaTermino;
//	}
//	public void setFechaTermino(Date fechaTermino) {
//		this.fechaTermino = fechaTermino;
//	}
	public Integer getNumeroFolios() {
		return numeroFolios;
	}
	public void setNumeroFolios(Integer numeroFolios) {
		this.numeroFolios = numeroFolios;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getEstadoTramite() {
		return estadoTramite;
	}
	public void setEstadoTramite(String estadoTramite) {
		this.estadoTramite = estadoTramite;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public TipoTramiteDTO getTipoTramiteDto() {
		return tipoTramiteDto;
	}
	public void setTipoTramiteDto(TipoTramiteDTO tipoTramiteDto) {
		this.tipoTramiteDto = tipoTramiteDto;
	}
	public SolicitanteDTO getSolicitanteDto() {
		return solicitanteDto;
	}
	public void setSolicitanteDto(SolicitanteDTO solicitanteDto) {
		this.solicitanteDto = solicitanteDto;
	}

}
