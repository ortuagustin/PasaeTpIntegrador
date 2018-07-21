package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.dtos.*;
import ar.edu.unlp.pasae.tp_integrador.entities.*;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.services.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class TpIntegradorApplicationTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private PatientService patientService;

	@Autowired
	private CustomUserRepository userRepository;

	private CustomUser user;

	@Before
	public void createUser() {
		List<Role> roles = new ArrayList<Role>();
		this.user = userRepository.save(new CustomUser("tester", "tester", "tester@example.com", "test", "test", roles));
	}

	@Test
	public void it_saves_new_patient() {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);
		PatientDTO patient = this.patientService.create(request);

		Assert.assertNotNull(patient);
		Assert.assertEquals(name, patient.getName());
		Assert.assertEquals(surname, patient.getSurname());
		Assert.assertEquals(dni, patient.getDni());
		Assert.assertEquals(email, patient.getEmail());
	}

	@Test
	public void it_updates_existing_patient() {
		Long userId = this.user.getId();
		PatientRequestDTO createRequest = new PatientRequestDTO(userId, "Name", "Surname", "37058719", "test@example.com");
		PatientDTO patient = this.patientService.create(createRequest);

		String name = "Changed Name";
		String surname = "Changed Surname";
		String dni = "12345678";
		String email = "changed@example.com";

		PatientRequestDTO updateRequest = new PatientRequestDTO(patient.getId(), userId, name, surname, dni, email);
		PatientDTO updatedPatient = this.patientService.update(updateRequest);

		Assert.assertEquals(name, updatedPatient.getName());
		Assert.assertEquals(surname, updatedPatient.getSurname());
		Assert.assertEquals(dni, updatedPatient.getDni());
		Assert.assertEquals(email, updatedPatient.getEmail());
	}

	@Test
	public void it_does_not_allow_empty_name() {
		Long userId = this.user.getId();
		String name = "";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);

		thrown.expect(ValidationException.class);
		this.patientService.create(request);
	}

	@Test
	public void it_does_not_allow_empty_surname() {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);

		thrown.expect(ValidationException.class);
		this.patientService.create(request);
	}

	@Test
	public void it_does_not_allow_empty_dni() {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);

		thrown.expect(ValidationException.class);
		this.patientService.create(request);
	}

	@Test
	public void it_does_not_allow_invalid_dni() {
		PatientRequestDTO request;
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String email = "test@example.com";

		thrown.expect(ValidationException.class);

		request = new PatientRequestDTO(userId, name, surname, "dni", email);
		this.patientService.create(request);

		request.setDni("1234");
		this.patientService.create(request);
	}

	@Test
	public void it_does_not_allow_invalid_email() {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);

		thrown.expect(ValidationException.class);
		this.patientService.create(request);
	}

	@Test
	public void it_does_not_change_registrant_user() {
		List<Role> roles = new ArrayList<Role>();
		CustomUser anotherUser = this.userRepository.save(new CustomUser("other", "other", "other@example.com", "other", "other", roles));

		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO createRequest = new PatientRequestDTO(userId, name, surname, dni, email);
		PatientDTO patient = this.patientService.create(createRequest);

		PatientRequestDTO updateRequest = new PatientRequestDTO(patient.getId(), anotherUser.getId(), name, surname, dni, email);
		PatientDTO updatedPatient = this.patientService.update(updateRequest);

		Assert.assertEquals(userId, patient.getUser().getId());
		Assert.assertEquals(userId, updatedPatient.getUser().getId());
	}
}
