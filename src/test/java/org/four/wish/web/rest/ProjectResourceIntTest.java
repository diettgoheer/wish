package org.four.wish.web.rest;

import org.four.wish.WishApp;

import org.four.wish.domain.Project;
import org.four.wish.repository.PersonRepository;
import org.four.wish.repository.ProjectRepository;
import org.four.wish.repository.search.ProjectSearchRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WishApp.class)
public class ProjectResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SIMPLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SIMPLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SPONSOR = "AAAAAAAAAA";
    private static final String UPDATED_SPONSOR = "BBBBBBBBBB";

    private static final String DEFAULT_SCIENCE_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_SCIENCE_FIELD = "BBBBBBBBBB";

    private static final Double DEFAULT_BUDGET = 1D;
    private static final Double UPDATED_BUDGET = 2D;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ANNEXA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANNEXA = TestUtil.createByteArray(500000, "1");
    private static final String DEFAULT_ANNEXA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANNEXA_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ANNEXB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANNEXB = TestUtil.createByteArray(500000, "1");
    private static final String DEFAULT_ANNEXB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANNEXB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ANNEXC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANNEXC = TestUtil.createByteArray(500000, "1");
    private static final String DEFAULT_ANNEXC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANNEXC_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ANNEXD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANNEXD = TestUtil.createByteArray(500000, "1");
    private static final String DEFAULT_ANNEXD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANNEXD_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ANNEXE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ANNEXE = TestUtil.createByteArray(500000, "1");
    private static final String DEFAULT_ANNEXE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ANNEXE_CONTENT_TYPE = "image/png";

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectSearchRepository projectSearchRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectMockMvc;

    private Project project;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjectResource projectResource = new ProjectResource(personRepository,projectRepository, projectSearchRepository);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
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
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .name(DEFAULT_NAME)
            .simpleName(DEFAULT_SIMPLE_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .sponsor(DEFAULT_SPONSOR)
            .scienceField(DEFAULT_SCIENCE_FIELD)
            .budget(DEFAULT_BUDGET)
            .source(DEFAULT_SOURCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .createdTime(DEFAULT_CREATED_TIME)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .annexa(DEFAULT_ANNEXA)
            .annexaContentType(DEFAULT_ANNEXA_CONTENT_TYPE)
            .annexb(DEFAULT_ANNEXB)
            .annexbContentType(DEFAULT_ANNEXB_CONTENT_TYPE)
            .annexc(DEFAULT_ANNEXC)
            .annexcContentType(DEFAULT_ANNEXC_CONTENT_TYPE)
            .annexd(DEFAULT_ANNEXD)
            .annexdContentType(DEFAULT_ANNEXD_CONTENT_TYPE)
            .annexe(DEFAULT_ANNEXE)
            .annexeContentType(DEFAULT_ANNEXE_CONTENT_TYPE);
        return project;
    }

    @Before
    public void initTest() {
        projectSearchRepository.deleteAll();
        project = createEntity(em);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getSimpleName()).isEqualTo(DEFAULT_SIMPLE_NAME);
        assertThat(testProject.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProject.getSponsor()).isEqualTo(DEFAULT_SPONSOR);
        assertThat(testProject.getScienceField()).isEqualTo(DEFAULT_SCIENCE_FIELD);
        assertThat(testProject.getBudget()).isEqualTo(DEFAULT_BUDGET);
        assertThat(testProject.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProject.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProject.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProject.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testProject.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProject.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
        assertThat(testProject.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testProject.getAnnexa()).isEqualTo(DEFAULT_ANNEXA);
        assertThat(testProject.getAnnexaContentType()).isEqualTo(DEFAULT_ANNEXA_CONTENT_TYPE);
        assertThat(testProject.getAnnexb()).isEqualTo(DEFAULT_ANNEXB);
        assertThat(testProject.getAnnexbContentType()).isEqualTo(DEFAULT_ANNEXB_CONTENT_TYPE);
        assertThat(testProject.getAnnexc()).isEqualTo(DEFAULT_ANNEXC);
        assertThat(testProject.getAnnexcContentType()).isEqualTo(DEFAULT_ANNEXC_CONTENT_TYPE);
        assertThat(testProject.getAnnexd()).isEqualTo(DEFAULT_ANNEXD);
        assertThat(testProject.getAnnexdContentType()).isEqualTo(DEFAULT_ANNEXD_CONTENT_TYPE);
        assertThat(testProject.getAnnexe()).isEqualTo(DEFAULT_ANNEXE);
        assertThat(testProject.getAnnexeContentType()).isEqualTo(DEFAULT_ANNEXE_CONTENT_TYPE);

        // Validate the Project in Elasticsearch
        Project projectEs = projectSearchRepository.findOne(testProject.getId());
        assertThat(projectEs).isEqualToComparingFieldByField(testProject);
    }

    @Test
    @Transactional
    public void createProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project with an existing ID
        project.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setName(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSponsorIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setSponsor(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].simpleName").value(hasItem(DEFAULT_SIMPLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sponsor").value(hasItem(DEFAULT_SPONSOR.toString())))
            .andExpect(jsonPath("$.[*].scienceField").value(hasItem(DEFAULT_SCIENCE_FIELD.toString())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].annexaContentType").value(hasItem(DEFAULT_ANNEXA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexa").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXA))))
            .andExpect(jsonPath("$.[*].annexbContentType").value(hasItem(DEFAULT_ANNEXB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexb").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXB))))
            .andExpect(jsonPath("$.[*].annexcContentType").value(hasItem(DEFAULT_ANNEXC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexc").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXC))))
            .andExpect(jsonPath("$.[*].annexdContentType").value(hasItem(DEFAULT_ANNEXD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexd").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXD))))
            .andExpect(jsonPath("$.[*].annexeContentType").value(hasItem(DEFAULT_ANNEXE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexe").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXE))));
    }

    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.simpleName").value(DEFAULT_SIMPLE_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sponsor").value(DEFAULT_SPONSOR.toString()))
            .andExpect(jsonPath("$.scienceField").value(DEFAULT_SCIENCE_FIELD.toString()))
            .andExpect(jsonPath("$.budget").value(DEFAULT_BUDGET.doubleValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.annexaContentType").value(DEFAULT_ANNEXA_CONTENT_TYPE))
            .andExpect(jsonPath("$.annexa").value(Base64Utils.encodeToString(DEFAULT_ANNEXA)))
            .andExpect(jsonPath("$.annexbContentType").value(DEFAULT_ANNEXB_CONTENT_TYPE))
            .andExpect(jsonPath("$.annexb").value(Base64Utils.encodeToString(DEFAULT_ANNEXB)))
            .andExpect(jsonPath("$.annexcContentType").value(DEFAULT_ANNEXC_CONTENT_TYPE))
            .andExpect(jsonPath("$.annexc").value(Base64Utils.encodeToString(DEFAULT_ANNEXC)))
            .andExpect(jsonPath("$.annexdContentType").value(DEFAULT_ANNEXD_CONTENT_TYPE))
            .andExpect(jsonPath("$.annexd").value(Base64Utils.encodeToString(DEFAULT_ANNEXD)))
            .andExpect(jsonPath("$.annexeContentType").value(DEFAULT_ANNEXE_CONTENT_TYPE))
            .andExpect(jsonPath("$.annexe").value(Base64Utils.encodeToString(DEFAULT_ANNEXE)));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);
        projectSearchRepository.save(project);
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findOne(project.getId());
        updatedProject
            .name(UPDATED_NAME)
            .simpleName(UPDATED_SIMPLE_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .sponsor(UPDATED_SPONSOR)
            .scienceField(UPDATED_SCIENCE_FIELD)
            .budget(UPDATED_BUDGET)
            .source(UPDATED_SOURCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .createdTime(UPDATED_CREATED_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .annexa(UPDATED_ANNEXA)
            .annexaContentType(UPDATED_ANNEXA_CONTENT_TYPE)
            .annexb(UPDATED_ANNEXB)
            .annexbContentType(UPDATED_ANNEXB_CONTENT_TYPE)
            .annexc(UPDATED_ANNEXC)
            .annexcContentType(UPDATED_ANNEXC_CONTENT_TYPE)
            .annexd(UPDATED_ANNEXD)
            .annexdContentType(UPDATED_ANNEXD_CONTENT_TYPE)
            .annexe(UPDATED_ANNEXE)
            .annexeContentType(UPDATED_ANNEXE_CONTENT_TYPE);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getSimpleName()).isEqualTo(UPDATED_SIMPLE_NAME);
        assertThat(testProject.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProject.getSponsor()).isEqualTo(UPDATED_SPONSOR);
        assertThat(testProject.getScienceField()).isEqualTo(UPDATED_SCIENCE_FIELD);
        assertThat(testProject.getBudget()).isEqualTo(UPDATED_BUDGET);
        assertThat(testProject.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProject.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testProject.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
        assertThat(testProject.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testProject.getAnnexa()).isEqualTo(UPDATED_ANNEXA);
        assertThat(testProject.getAnnexaContentType()).isEqualTo(UPDATED_ANNEXA_CONTENT_TYPE);
        assertThat(testProject.getAnnexb()).isEqualTo(UPDATED_ANNEXB);
        assertThat(testProject.getAnnexbContentType()).isEqualTo(UPDATED_ANNEXB_CONTENT_TYPE);
        assertThat(testProject.getAnnexc()).isEqualTo(UPDATED_ANNEXC);
        assertThat(testProject.getAnnexcContentType()).isEqualTo(UPDATED_ANNEXC_CONTENT_TYPE);
        assertThat(testProject.getAnnexd()).isEqualTo(UPDATED_ANNEXD);
        assertThat(testProject.getAnnexdContentType()).isEqualTo(UPDATED_ANNEXD_CONTENT_TYPE);
        assertThat(testProject.getAnnexe()).isEqualTo(UPDATED_ANNEXE);
        assertThat(testProject.getAnnexeContentType()).isEqualTo(UPDATED_ANNEXE_CONTENT_TYPE);

        // Validate the Project in Elasticsearch
        Project projectEs = projectSearchRepository.findOne(testProject.getId());
        assertThat(projectEs).isEqualToComparingFieldByField(testProject);
    }

    @Test
    @Transactional
    public void updateNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Create the Project

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);
        projectSearchRepository.save(project);
        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Get the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean projectExistsInEs = projectSearchRepository.exists(project.getId());
        assertThat(projectExistsInEs).isFalse();

        // Validate the database is empty
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);
        projectSearchRepository.save(project);

        // Search the project
        restProjectMockMvc.perform(get("/api/_search/projects?query=id:" + project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].simpleName").value(hasItem(DEFAULT_SIMPLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sponsor").value(hasItem(DEFAULT_SPONSOR.toString())))
            .andExpect(jsonPath("$.[*].scienceField").value(hasItem(DEFAULT_SCIENCE_FIELD.toString())))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].annexaContentType").value(hasItem(DEFAULT_ANNEXA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexa").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXA))))
            .andExpect(jsonPath("$.[*].annexbContentType").value(hasItem(DEFAULT_ANNEXB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexb").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXB))))
            .andExpect(jsonPath("$.[*].annexcContentType").value(hasItem(DEFAULT_ANNEXC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexc").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXC))))
            .andExpect(jsonPath("$.[*].annexdContentType").value(hasItem(DEFAULT_ANNEXD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexd").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXD))))
            .andExpect(jsonPath("$.[*].annexeContentType").value(hasItem(DEFAULT_ANNEXE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].annexe").value(hasItem(Base64Utils.encodeToString(DEFAULT_ANNEXE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = new Project();
        project1.setId(1L);
        Project project2 = new Project();
        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);
        project2.setId(2L);
        assertThat(project1).isNotEqualTo(project2);
        project1.setId(null);
        assertThat(project1).isNotEqualTo(project2);
    }
}
