package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Rollback(true)
public class PatientTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private PatientService patientService;

	@Autowired
	private CustomUserRepository userRepository;

	@Autowired
	private NumericPhenotypeService numericPhenotypeService;

	@Autowired
	private CategoricPhenotypeService categoricPhenotypeService;

	private CustomUser user;

	@Before
	public void createUser() {
		List<Role> roles = new ArrayList<Role>();
		this.user = userRepository.save(new CustomUser("tester", "tester", "tester@example.com", "test", "test", roles));
	}

	@Before
	public void clearNumericPhenotypes() {
		this.numericPhenotypeService.deleteAll();
	}

	@Test
	public void it_returns_patient_numeric_phenotype_with_associated_value() throws GenotypeDecoderException {
		Map<Long, String> values = new HashMap<Long, String>();
		values.put(1L, "Value 1");
		values.put(2L, "Value 2");
		values.put(3L, "Value 3");

		CategoricPhenotypeDTO phenotypeRequest = new CategoricPhenotypeDTO("Categoric Phenotype Test", values);
		Long phenotypeId = this.categoricPhenotypeService.create(phenotypeRequest).getId();
		CategoricPhenotypeValueDTO patientPhenotype = new CategoricPhenotypeValueDTO(phenotypeId, 1L);

		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO patientRequest = new PatientRequestDTO(userId, name, surname, dni, email);
		patientRequest.addCategoricPhenotype(patientPhenotype);
		Long patientId = this.patientService.create(patientRequest).getId();
		PatientDTO patient = this.patientService.find(patientId);

		Assert.assertNotNull(patient);
		Assert.assertEquals(1, patient.getCategoricPhenotypes().size());

		patientPhenotype = patient.getCategoricPhenotypes().stream().findAny().get();
		Assert.assertEquals(phenotypeId, patientPhenotype.getPhenotypeId());
		Assert.assertEquals("Value 1", patientPhenotype.getValue());
	}

	@Test
	public void it_returns_patient_categoric_phenotype_with_associated_value() throws GenotypeDecoderException {
		NumericPhenotypeDTO phenotypeRequest = new NumericPhenotypeDTO("Numeric Phenotype Test");
		Long phenotypeId = this.numericPhenotypeService.create(phenotypeRequest).getId();
		NumericPhenotypeValueDTO patientPhenotype = new NumericPhenotypeValueDTO(phenotypeId, 5L);

		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO patientRequest = new PatientRequestDTO(userId, name, surname, dni, email);
		patientRequest.addNumericPhenotype(patientPhenotype);
		Long patientId = this.patientService.create(patientRequest).getId();
		PatientDTO patient = this.patientService.find(patientId);

		Assert.assertNotNull(patient);
		Assert.assertEquals(1, patient.getNumericPhenotypes().size());

		patientPhenotype = patient.getNumericPhenotypes().stream().findAny().get();
		Assert.assertEquals(phenotypeId, patientPhenotype.getPhenotypeId());
		Assert.assertEquals((Long) 5L, patientPhenotype.getValue());
	}

	@Test
	public void it_returns_patient_list() throws GenotypeDecoderException {
		Collection<PatientDTO> patients;

		patients = this.patientService.list().collect(Collectors.toList());

		Assert.assertTrue("Patients list should be empty", patients.isEmpty());

		Long userId = this.user.getId();

		for (Integer i = 1; i <= 8; i++) {
			PatientRequestDTO request;
			String name = "Patient " + i;
			String dni = StringUtils.repeat(i.toString(), 8);

			request = new PatientRequestDTO(userId, name, name, dni, "user" + i + "@example.com");
			this.patientService.create(request);
		}

		patients = this.patientService.list().collect(Collectors.toList());

		Assert.assertFalse("Patients list should not be empty", patients.isEmpty());
		Assert.assertEquals(8, patients.size());
	}

	@Test
	public void it_returns_patient_count() throws GenotypeDecoderException {
		Assert.assertEquals((Integer) 0, this.patientService.count());

		Long userId = this.user.getId();

		for (Integer i = 1; i <= 8; i++) {
			PatientRequestDTO request;
			String name = "Patient " + i;
			String dni = StringUtils.repeat(i.toString(), 8);

			request = new PatientRequestDTO(userId, name, name, dni, "user" + i + "@example.com");
			this.patientService.create(request);
		}

		Assert.assertEquals((Integer) 8, this.patientService.count());
	}

	@Test
	public void it_finds_patient_by_id() throws GenotypeDecoderException {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);
		Long patientId = this.patientService.create(request).getId();
		PatientDTO patient = this.patientService.find(patientId);

		Assert.assertNotNull(patient);
		Assert.assertEquals(name, patient.getName());
		Assert.assertEquals(surname, patient.getSurname());
		Assert.assertEquals(dni, patient.getDni());
		Assert.assertEquals(email, patient.getEmail());
	}

	@Test
	public void it_throws_exception_when_patient_id_does_not_exist() {
		thrown.expect(EntityNotFoundException.class);

		this.patientService.find(1L);
		this.patientService.find(2L);
	}

	@Test
	public void it_finds_patient_by_dni() throws GenotypeDecoderException {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);
		this.patientService.create(request).getId();
		PatientDTO patient = this.patientService.findByDNI(dni);

		Assert.assertNotNull(patient);
		Assert.assertEquals(name, patient.getName());
		Assert.assertEquals(surname, patient.getSurname());
		Assert.assertEquals(dni, patient.getDni());
		Assert.assertEquals(email, patient.getEmail());
	}

	@Test
	public void it_throws_exception_when_patient_dni_does_not_exist() {
		thrown.expect(EntityNotFoundException.class);

		this.patientService.findByDNI("");
		this.patientService.findByDNI("123");
		this.patientService.findByDNI("12345678");
	}

	@Test
	public void it_finds_patient_by_name_and_surname() throws GenotypeDecoderException {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);
		this.patientService.create(request).getId();
		PatientDTO patient = this.patientService.findByNameAndSurname(name, surname);

		Assert.assertNotNull(patient);
		Assert.assertEquals(name, patient.getName());
		Assert.assertEquals(surname, patient.getSurname());
		Assert.assertEquals(dni, patient.getDni());
		Assert.assertEquals(email, patient.getEmail());
	}

	@Test
	public void it_throws_exception_when_patient_name_and_surname_does_not_exist() {
		thrown.expect(EntityNotFoundException.class);

		this.patientService.findByNameAndSurname("", "");
		this.patientService.findByNameAndSurname("a", "");
		this.patientService.findByNameAndSurname("", "");
		this.patientService.findByNameAndSurname("a", "a");
	}

	@Test
	public void it_deletes_patient() throws GenotypeDecoderException {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email);
		Long patientId = this.patientService.create(request).getId();

		PatientDTO patient;

		patient = this.patientService.find(patientId);
		Assert.assertNotNull(patient);

		thrown.expect(EntityNotFoundException.class);

		this.patientService.delete(patientId);
		patient = this.patientService.find(patientId);
		Assert.assertNull(patient);
	}

	@Test
	public void it_throws_exception_when_deleting_non_existing_patient() {
		thrown.expect(EntityNotFoundException.class);

		this.patientService.delete(1L);
		this.patientService.delete(2L);
	}

	@Test
	public void it_saves_new_patient() throws GenotypeDecoderException {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";
		String genotype = "rs111 ac\nrs1122 at";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email, genotype);
		PatientDTO patient = this.patientService.create(request);

		Assert.assertNotNull(patient);
		Assert.assertEquals(name, patient.getName());
		Assert.assertEquals(surname, patient.getSurname());
		Assert.assertEquals(dni, patient.getDni());
		Assert.assertEquals(email, patient.getEmail());
		Assert.assertEquals(2, patient.getGenotype().size());
	}

	@Test
	public void it_updates_the_patient_genotype() throws GenotypeDecoderException {
		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO createRequest = new PatientRequestDTO(userId, name, surname, dni, email);
		PatientDTO patient = this.patientService.create(createRequest);

		Assert.assertEquals(0, patient.getGenotype().size());

		String genotype = "rs111 ac\nrs1122 at";
		PatientRequestDTO updateRequest = new PatientRequestDTO(userId, name, surname, dni, email, genotype);
		PatientDTO updatedPatient = this.patientService.update(patient.getId(), updateRequest);

		Assert.assertEquals(2, updatedPatient.getGenotype().size());
	}

	@Test
	public void it_updates_existing_patient() throws GenotypeDecoderException {
		Long userId = this.user.getId();
		PatientRequestDTO createRequest = new PatientRequestDTO(userId, "Name", "Surname", "37058719", "test@example.com");
		PatientDTO patient = this.patientService.create(createRequest);

		String name = "Changed Name";
		String surname = "Changed Surname";
		String dni = "12345678";
		String email = "changed@example.com";

		PatientRequestDTO updateRequest = new PatientRequestDTO(userId, name, surname, dni, email);
		PatientDTO updatedPatient = this.patientService.update(patient.getId(), updateRequest);

		Assert.assertEquals(name, updatedPatient.getName());
		Assert.assertEquals(surname, updatedPatient.getSurname());
		Assert.assertEquals(dni, updatedPatient.getDni());
		Assert.assertEquals(email, updatedPatient.getEmail());
	}

	@Test
	public void it_does_not_allow_empty_name() throws GenotypeDecoderException {
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
	public void it_does_not_allow_empty_surname() throws GenotypeDecoderException {
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
	public void it_does_not_allow_empty_dni() throws GenotypeDecoderException {
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
	public void it_does_not_allow_invalid_dni() throws GenotypeDecoderException {
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
	public void it_does_not_allow_invalid_email() throws GenotypeDecoderException {
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
	public void it_does_not_change_registrant_user() throws GenotypeDecoderException {
		List<Role> roles = new ArrayList<Role>();
		CustomUser anotherUser = this.userRepository
				.save(new CustomUser("other", "other", "other@example.com", "other", "other", roles));

		Long userId = this.user.getId();
		String name = "Name";
		String surname = "Surname";
		String dni = "37058719";
		String email = "test@example.com";

		PatientRequestDTO createRequest = new PatientRequestDTO(userId, name, surname, dni, email);
		PatientDTO patient = this.patientService.create(createRequest);

		PatientRequestDTO updateRequest = new PatientRequestDTO(anotherUser.getId(), name, surname, dni, email);
		PatientDTO updatedPatient = this.patientService.update(patient.getId(), updateRequest);

		Assert.assertEquals(userId, patient.getUser().getId());
		Assert.assertEquals(userId, updatedPatient.getUser().getId());
	}
}
