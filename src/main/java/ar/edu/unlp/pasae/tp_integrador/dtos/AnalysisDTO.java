package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.Date;

import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;

public class AnalysisDTO {
  private Long id;
  private Date date;
  private AnalysisState state;

  public AnalysisDTO(Long id, Date date, AnalysisState state) {
    super();
    this.setId(id);
    this.setDate(date);
    this.setState(state);
  }

  public AnalysisDTO(Date date, AnalysisState state) {
    super();
    this.setDate(date);
    this.setState(state);
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
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
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }
}