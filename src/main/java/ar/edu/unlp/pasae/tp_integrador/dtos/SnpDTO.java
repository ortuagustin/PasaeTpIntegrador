package ar.edu.unlp.pasae.tp_integrador.dtos;

public class SnpDTO {
	private Long id;
	private String snp;
	private Double estadistico;
	private Double pvalue;

	public SnpDTO() {
		super();
	}

	public SnpDTO(Long id, String snp, Double pvalue, Double estadistico) {
		super();
		this.setId(id);
		this.setSnp(snp);
		this.setPvalue(pvalue);
		this.setEstadistico(estadistico);
	}

	/**
	 * @return the estadistico
	 */
	public Double getEstadistico() {
		return estadistico;
	}

	/**
	 * @param estadistico the estadistico to set
	 */
	public void setEstadistico(Double estadistico) {
		this.estadistico = estadistico;
	}

	/**
	 * @return the pvalue
	 */
	public Double getPvalue() {
		return pvalue;
	}

	/**
	 * @param pvalue the pvalue to set
	 */
	public void setPvalue(Double pvalue) {
		this.pvalue = pvalue;
	}

	/**
	 * @return the snp
	 */
	public String getSnp() {
		return snp;
	}

	/**
	 * @param snp the snp to set
	 */
	public void setSnp(String snp) {
		this.snp = snp;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}