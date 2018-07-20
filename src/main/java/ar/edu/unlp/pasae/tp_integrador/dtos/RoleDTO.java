package ar.edu.unlp.pasae.tp_integrador.dtos;

public class RoleDTO  {
	private Long id;
	private String name;

	public RoleDTO() {}

	public RoleDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
