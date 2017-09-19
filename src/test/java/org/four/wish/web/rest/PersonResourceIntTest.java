package org.four.wish.web.rest;

import org.four.wish.WishApp;

import org.four.wish.domain.Person;
import org.four.wish.repository.PersonRepository;
import org.four.wish.repository.search.PersonSearchRepository;
import org.four.wish.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.four.wish.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WishApp.class)
public class PersonResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "B@j.ig";
    private static final String UPDATED_EMAIL = "7_@0o.WIP";

    private static final String DEFAULT_TELEPHONE = "014679613985";
    private static final String UPDATED_TELEPHONE = "13598165117";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PIC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PIC = TestUtil.createByteArray(500000, "1");
    private static final String DEFAULT_PIC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PIC_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SAA = "AAAAAAAAAA";
    private static final String UPDATED_SAA = "BBBBBBBBBB";

    private static final String DEFAULT_SAB = "AAAAAAAAAA";
    private static final String UPDATED_SAB = "BBBBBBBBBB";

    private static final String DEFAULT_MAC = "AAAAAAAAAA";
    private static final String UPDATED_MAC = "BBBBBBBBBB";

    private static final String DEFAULT_MAD = "AAAAAAAAAA";
    private static final String UPDATED_MAD = "BBBBBBBBBB";

    private static final String DEFAULT_LAE = "AAAAAAAAAA";
    private static final String UPDATED_LAE = "BBBBBBBBBB";

    private static final String DEFAULT_LAF = "AAAAAAAAAA";
    private static final String UPDATED_LAF = "BBBBBBBBBB";

    private static final String DEFAULT_LAG = "AAAAAAAAAA";
    private static final String UPDATED_LAG = "BBBBBBBBBB";

    private static final String DEFAULT_XLAH = "AAAAAAAAAA";
    private static final String UPDATED_XLAH = "BBBBBBBBBB";

    private static final String DEFAULT_XLAI = "AAAAAAAAAA";
    private static final String UPDATED_XLAI = "BBBBBBBBBB";

    private static final String DEFAULT_XLAJ = "AAAAAAAAAA";
    private static final String UPDATED_XLAJ = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_BB = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BB = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_BC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_BD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_BE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonSearchRepository personSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource(personRepository, personSearchRepository);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .description(DEFAULT_DESCRIPTION)
            .homePage(DEFAULT_HOME_PAGE)
            .pic(DEFAULT_PIC)
            .picContentType(DEFAULT_PIC_CONTENT_TYPE)
            .saa(DEFAULT_SAA)
            .sab(DEFAULT_SAB)
            .mac(DEFAULT_MAC)
            .mad(DEFAULT_MAD)
            .lae(DEFAULT_LAE)
            .laf(DEFAULT_LAF)
            .lag(DEFAULT_LAG)
            .xlah(DEFAULT_XLAH)
            .xlai(DEFAULT_XLAI)
            .xlaj(DEFAULT_XLAJ)
            .ba(DEFAULT_BA)
            .bb(DEFAULT_BB)
            .bc(DEFAULT_BC)
            .bd(DEFAULT_BD)
            .be(DEFAULT_BE)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .user(DEFAULT_USER);
        return person;
    }

    @Before
    public void initTest() {
        personSearchRepository.deleteAll();
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPerson.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testPerson.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPerson.getHomePage()).isEqualTo(DEFAULT_HOME_PAGE);
        assertThat(testPerson.getPic()).isEqualTo(DEFAULT_PIC);
        assertThat(testPerson.getPicContentType()).isEqualTo(DEFAULT_PIC_CONTENT_TYPE);
        assertThat(testPerson.getSaa()).isEqualTo(DEFAULT_SAA);
        assertThat(testPerson.getSab()).isEqualTo(DEFAULT_SAB);
        assertThat(testPerson.getMac()).isEqualTo(DEFAULT_MAC);
        assertThat(testPerson.getMad()).isEqualTo(DEFAULT_MAD);
        assertThat(testPerson.getLae()).isEqualTo(DEFAULT_LAE);
        assertThat(testPerson.getLaf()).isEqualTo(DEFAULT_LAF);
        assertThat(testPerson.getLag()).isEqualTo(DEFAULT_LAG);
        assertThat(testPerson.getXlah()).isEqualTo(DEFAULT_XLAH);
        assertThat(testPerson.getXlai()).isEqualTo(DEFAULT_XLAI);
        assertThat(testPerson.getXlaj()).isEqualTo(DEFAULT_XLAJ);
        assertThat(testPerson.getBa()).isEqualTo(DEFAULT_BA);
        assertThat(testPerson.getBb()).isEqualTo(DEFAULT_BB);
        assertThat(testPerson.getBc()).isEqualTo(DEFAULT_BC);
        assertThat(testPerson.getBd()).isEqualTo(DEFAULT_BD);
        assertThat(testPerson.getBe()).isEqualTo(DEFAULT_BE);
        assertThat(testPerson.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPerson.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerson.getUser()).isEqualTo(DEFAULT_USER);

        // Validate the Person in Elasticsearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToComparingFieldByField(testPerson);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setName(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setEmail(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].homePage").value(hasItem(DEFAULT_HOME_PAGE.toString())))
            .andExpect(jsonPath("$.[*].picContentType").value(hasItem(DEFAULT_PIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pic").value(hasItem(Base64Utils.encodeToString(DEFAULT_PIC))))
            .andExpect(jsonPath("$.[*].saa").value(hasItem(DEFAULT_SAA.toString())))
            .andExpect(jsonPath("$.[*].sab").value(hasItem(DEFAULT_SAB.toString())))
            .andExpect(jsonPath("$.[*].mac").value(hasItem(DEFAULT_MAC.toString())))
            .andExpect(jsonPath("$.[*].mad").value(hasItem(DEFAULT_MAD.toString())))
            .andExpect(jsonPath("$.[*].lae").value(hasItem(DEFAULT_LAE.toString())))
            .andExpect(jsonPath("$.[*].laf").value(hasItem(DEFAULT_LAF.toString())))
            .andExpect(jsonPath("$.[*].lag").value(hasItem(DEFAULT_LAG.toString())))
            .andExpect(jsonPath("$.[*].xlah").value(hasItem(DEFAULT_XLAH.toString())))
            .andExpect(jsonPath("$.[*].xlai").value(hasItem(DEFAULT_XLAI.toString())))
            .andExpect(jsonPath("$.[*].xlaj").value(hasItem(DEFAULT_XLAJ.toString())))
            .andExpect(jsonPath("$.[*].ba").value(hasItem(sameInstant(DEFAULT_BA))))
            .andExpect(jsonPath("$.[*].bb").value(hasItem(sameInstant(DEFAULT_BB))))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(sameInstant(DEFAULT_BC))))
            .andExpect(jsonPath("$.[*].bd").value(hasItem(sameInstant(DEFAULT_BD))))
            .andExpect(jsonPath("$.[*].be").value(hasItem(sameInstant(DEFAULT_BE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.homePage").value(DEFAULT_HOME_PAGE.toString()))
            .andExpect(jsonPath("$.picContentType").value(DEFAULT_PIC_CONTENT_TYPE))
            .andExpect(jsonPath("$.pic").value(Base64Utils.encodeToString(DEFAULT_PIC)))
            .andExpect(jsonPath("$.saa").value(DEFAULT_SAA.toString()))
            .andExpect(jsonPath("$.sab").value(DEFAULT_SAB.toString()))
            .andExpect(jsonPath("$.mac").value(DEFAULT_MAC.toString()))
            .andExpect(jsonPath("$.mad").value(DEFAULT_MAD.toString()))
            .andExpect(jsonPath("$.lae").value(DEFAULT_LAE.toString()))
            .andExpect(jsonPath("$.laf").value(DEFAULT_LAF.toString()))
            .andExpect(jsonPath("$.lag").value(DEFAULT_LAG.toString()))
            .andExpect(jsonPath("$.xlah").value(DEFAULT_XLAH.toString()))
            .andExpect(jsonPath("$.xlai").value(DEFAULT_XLAI.toString()))
            .andExpect(jsonPath("$.xlaj").value(DEFAULT_XLAJ.toString()))
            .andExpect(jsonPath("$.ba").value(sameInstant(DEFAULT_BA)))
            .andExpect(jsonPath("$.bb").value(sameInstant(DEFAULT_BB)))
            .andExpect(jsonPath("$.bc").value(sameInstant(DEFAULT_BC)))
            .andExpect(jsonPath("$.bd").value(sameInstant(DEFAULT_BD)))
            .andExpect(jsonPath("$.be").value(sameInstant(DEFAULT_BE)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        updatedPerson
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .description(UPDATED_DESCRIPTION)
            .homePage(UPDATED_HOME_PAGE)
            .pic(UPDATED_PIC)
            .picContentType(UPDATED_PIC_CONTENT_TYPE)
            .saa(UPDATED_SAA)
            .sab(UPDATED_SAB)
            .mac(UPDATED_MAC)
            .mad(UPDATED_MAD)
            .lae(UPDATED_LAE)
            .laf(UPDATED_LAF)
            .lag(UPDATED_LAG)
            .xlah(UPDATED_XLAH)
            .xlai(UPDATED_XLAI)
            .xlaj(UPDATED_XLAJ)
            .ba(UPDATED_BA)
            .bb(UPDATED_BB)
            .bc(UPDATED_BC)
            .bd(UPDATED_BD)
            .be(UPDATED_BE)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .user(UPDATED_USER);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPerson.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testPerson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPerson.getHomePage()).isEqualTo(UPDATED_HOME_PAGE);
        assertThat(testPerson.getPic()).isEqualTo(UPDATED_PIC);
        assertThat(testPerson.getPicContentType()).isEqualTo(UPDATED_PIC_CONTENT_TYPE);
        assertThat(testPerson.getSaa()).isEqualTo(UPDATED_SAA);
        assertThat(testPerson.getSab()).isEqualTo(UPDATED_SAB);
        assertThat(testPerson.getMac()).isEqualTo(UPDATED_MAC);
        assertThat(testPerson.getMad()).isEqualTo(UPDATED_MAD);
        assertThat(testPerson.getLae()).isEqualTo(UPDATED_LAE);
        assertThat(testPerson.getLaf()).isEqualTo(UPDATED_LAF);
        assertThat(testPerson.getLag()).isEqualTo(UPDATED_LAG);
        assertThat(testPerson.getXlah()).isEqualTo(UPDATED_XLAH);
        assertThat(testPerson.getXlai()).isEqualTo(UPDATED_XLAI);
        assertThat(testPerson.getXlaj()).isEqualTo(UPDATED_XLAJ);
        assertThat(testPerson.getBa()).isEqualTo(UPDATED_BA);
        assertThat(testPerson.getBb()).isEqualTo(UPDATED_BB);
        assertThat(testPerson.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testPerson.getBd()).isEqualTo(UPDATED_BD);
        assertThat(testPerson.getBe()).isEqualTo(UPDATED_BE);
        assertThat(testPerson.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPerson.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerson.getUser()).isEqualTo(UPDATED_USER);

        // Validate the Person in Elasticsearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToComparingFieldByField(testPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);
        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean personExistsInEs = personSearchRepository.exists(person.getId());
        assertThat(personExistsInEs).isFalse();

        // Validate the database is empty
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);

        // Search the person
        restPersonMockMvc.perform(get("/api/_search/people?query=id:" + person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].homePage").value(hasItem(DEFAULT_HOME_PAGE.toString())))
            .andExpect(jsonPath("$.[*].picContentType").value(hasItem(DEFAULT_PIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pic").value(hasItem(Base64Utils.encodeToString(DEFAULT_PIC))))
            .andExpect(jsonPath("$.[*].saa").value(hasItem(DEFAULT_SAA.toString())))
            .andExpect(jsonPath("$.[*].sab").value(hasItem(DEFAULT_SAB.toString())))
            .andExpect(jsonPath("$.[*].mac").value(hasItem(DEFAULT_MAC.toString())))
            .andExpect(jsonPath("$.[*].mad").value(hasItem(DEFAULT_MAD.toString())))
            .andExpect(jsonPath("$.[*].lae").value(hasItem(DEFAULT_LAE.toString())))
            .andExpect(jsonPath("$.[*].laf").value(hasItem(DEFAULT_LAF.toString())))
            .andExpect(jsonPath("$.[*].lag").value(hasItem(DEFAULT_LAG.toString())))
            .andExpect(jsonPath("$.[*].xlah").value(hasItem(DEFAULT_XLAH.toString())))
            .andExpect(jsonPath("$.[*].xlai").value(hasItem(DEFAULT_XLAI.toString())))
            .andExpect(jsonPath("$.[*].xlaj").value(hasItem(DEFAULT_XLAJ.toString())))
            .andExpect(jsonPath("$.[*].ba").value(hasItem(sameInstant(DEFAULT_BA))))
            .andExpect(jsonPath("$.[*].bb").value(hasItem(sameInstant(DEFAULT_BB))))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(sameInstant(DEFAULT_BC))))
            .andExpect(jsonPath("$.[*].bd").value(hasItem(sameInstant(DEFAULT_BD))))
            .andExpect(jsonPath("$.[*].be").value(hasItem(sameInstant(DEFAULT_BE))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = new Person();
        person1.setId(1L);
        Person person2 = new Person();
        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);
        person2.setId(2L);
        assertThat(person1).isNotEqualTo(person2);
        person1.setId(null);
        assertThat(person1).isNotEqualTo(person2);
    }
}
