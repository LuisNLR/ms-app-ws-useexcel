package pe.com.softlite.useexcel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DependenciaDTO {
	
	private Long idDependencia;
	private String nombreDependencia;
	
	public DependenciaDTO() {
		super();
	}

	public DependenciaDTO(Long idDependencia, String nombreDependencia) {
		super();
		this.idDependencia = idDependencia;
		this.nombreDependencia = nombreDependencia;
	}

	public Long getIdDependencia() {
		return idDependencia;
	}

	public void setIdDependencia(Long idDependencia) {
		this.idDependencia = idDependencia;
	}

	public String getNombreDependencia() {
		return nombreDependencia;
	}

	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}

}
