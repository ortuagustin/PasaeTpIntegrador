package ar.edu.unlp.pasae.tp_integrador.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// TODO: modelar asociaciones genotipo-fenotipo
@Entity
@SuppressWarnings("unused")
public class Analysis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	private AnalysisState state;

	public Analysis(Long id, Date date, AnalysisState state) {
		super();
		this.setId(id);
		this.setDate(date);
		this.setState(state);
	}

	public Analysis(Date date, AnalysisState state) {
		super();
		this.setDate(date);
		this.setState(state);
	}

	private Analysis() {
		super();
		this.setState(AnalysisState.DRAFT);
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the state
	 */
	public AnalysisState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(AnalysisState state) {
		this.state = state;
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
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}