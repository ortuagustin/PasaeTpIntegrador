package ar.edu.unlp.pasae.tp_integrador.dtos;

public class SnpDTO {
	private Long id;
	private String snp;
	private Double statistical;
	private Double pvalue;

	public SnpDTO() {
		super();
	}

	public SnpDTO(String snp, Double pvalue, Double statistical) {
		super();
		this.setSnp(snp);
		this.setPvalue(pvalue);
		this.setStatistical(statistical);
	}

	public SnpDTO(Long id, String snp, Double pvalue, Double statistical) {
		this(snp, pvalue, statistical);
		this.setId(id);
	}

	/**
	 * @return the statistical
	 */
	public Double getStatistical() {
		return statistical;
	}

	/**
	 * @param statistical the statistical to set
	 */
	public void setStatistical(Double statistical) {
		this.statistical = statistical;
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