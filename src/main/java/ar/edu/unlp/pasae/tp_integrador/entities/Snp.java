package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Snp {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String snp;
	private Double estadistico;
	private Double pvalue;

	public Snp(String snp, Double estadistico, Double pvalue) {
		super();
		this.setSnp(snp);
		this.setEstadistico(estadistico);
		this.setPvalue(pvalue);
	}

	public Snp(Long id, String snp, Double estadistico, Double pvalue) {
		this(snp, estadistico, pvalue);
		this.setId(id);
	}

	public Snp() {
		super();
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
}