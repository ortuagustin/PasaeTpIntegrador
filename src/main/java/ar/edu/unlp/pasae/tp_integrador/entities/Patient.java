package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

// TODO Falta la relacion con el usuario registrante
// TODO Falta la relacion con los fenotipos
// TODO Falta el campo genotipo (como lo modelamos)
@Entity
public class Patient {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@NotEmpty
	private String dni;
	@Email
  private String email;

	public Patient() {
		super();
	}

	public Patient(Long id, String name, String surname, String dni, String email) {
		super();
		this.setId(id);
		this.setName(name);
		this.setSurname(surname);
		this.setDni(dni);
		this.setEmail(email);
  }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
  }

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
  }

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
  }

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
  }

  /**
   * @param dni the dni to set
   */
  public void setDni(String dni) {
    this.dni = dni;
  }

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}