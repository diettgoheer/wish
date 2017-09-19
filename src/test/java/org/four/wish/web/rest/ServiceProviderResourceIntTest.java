package org.four.wish.web.rest;

import org.four.wish.WishApp;

import org.four.wish.domain.ServiceProvider;
import org.four.wish.repository.ServiceProviderRepository;
import org.four.wish.repository.search.ServiceProviderSearchRepository;
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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServiceProviderResource REST controller.
 *
 * @see ServiceProviderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WishApp.class)
public class ServiceProviderResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ENTERPRISE_CREATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENTERPRISE_CREATED_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_LICENSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_LICENSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_REPRESENTATIVE = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_REPRESENTATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_GENERAL_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_GENERAL_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNICAL_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_QUALITY_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_QUALITY_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_QUALITY_MANAGER_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_QUALITY_MANAGER_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_CITY = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_ACCOUNT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BUSINESS_LICENSE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BUSINESS_LICENSE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BUSINESS_LICENSE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BUSINESS_LICENSE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BUSINESS_SCOPE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_SCOPE = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_SCOPE_PRE_LICENSE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_SCOPE_PRE_LICENSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_BUSINESS_YEARS = 1;
    private static final Integer UPDATED_BUSINESS_YEARS = 2;

    private static final String DEFAULT_CHARACTER = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTER = "BBBBBBBBBB";

    private static final String DEFAULT_SCALE = "AAAAAAAAAA";
    private static final String UPDATED_SCALE = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUBSIDIARY = false;
    private static final Boolean UPDATED_SUBSIDIARY = true;

    private static final Boolean DEFAULT_HAVE_SUBSIDIARY = false;
    private static final Boolean UPDATED_HAVE_SUBSIDIARY = true;

    private static final String DEFAULT_QUALITY_CERTIFICAION_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_QUALITY_CERTIFICAION_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATION_ORG = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATION_ORG = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CERTIFICATION_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CERTIFICATION_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_EMPLOYEES_NUMBER = 1;
    private static final Integer UPDATED_EMPLOYEES_NUMBER = 2;

    private static final Integer DEFAULT_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE = 1;
    private static final Integer UPDATED_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE = 2;

    private static final Integer DEFAULT_EMPLOYEES_TECHNICAL = 1;
    private static final Integer UPDATED_EMPLOYEES_TECHNICAL = 2;

    private static final String DEFAULT_EMPLOYEES_CERTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEES_CERTIFICATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_REGISTERED_CAPITAL = 1;
    private static final Integer UPDATED_REGISTERED_CAPITAL = 2;

    private static final Boolean DEFAULT_DEVELOP_TEAM = false;
    private static final Boolean UPDATED_DEVELOP_TEAM = true;

    private static final Boolean DEFAULT_TEST_TEAM = false;
    private static final Boolean UPDATED_TEST_TEAM = true;

    private static final Boolean DEFAULT_COMPLAINTS = false;
    private static final Boolean UPDATED_COMPLAINTS = true;

    private static final Boolean DEFAULT_TRAINING = false;
    private static final Boolean UPDATED_TRAINING = true;

    private static final Boolean DEFAULT_TRAINING_DOCUMENTS = false;
    private static final Boolean UPDATED_TRAINING_DOCUMENTS = true;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private ServiceProviderSearchRepository serviceProviderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceProviderMockMvc;

    private ServiceProvider serviceProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServiceProviderResource serviceProviderResource = new ServiceProviderResource(serviceProviderRepository, serviceProviderSearchRepository);
        this.restServiceProviderMockMvc = MockMvcBuilders.standaloneSetup(serviceProviderResource)
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
    public static ServiceProvider createEntity(EntityManager em) {
        ServiceProvider serviceProvider = new ServiceProvider()
            .name(DEFAULT_NAME)
            .enterpriseCreatedTime(DEFAULT_ENTERPRISE_CREATED_TIME)
            .description(DEFAULT_DESCRIPTION)
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .zipCode(DEFAULT_ZIP_CODE)
            .organizationCode(DEFAULT_ORGANIZATION_CODE)
            .businessLicenseNumber(DEFAULT_BUSINESS_LICENSE_NUMBER)
            .legalRepresentative(DEFAULT_LEGAL_REPRESENTATIVE)
            .generalManager(DEFAULT_GENERAL_MANAGER)
            .technicalManager(DEFAULT_TECHNICAL_MANAGER)
            .qualityManager(DEFAULT_QUALITY_MANAGER)
            .qualityManagerContact(DEFAULT_QUALITY_MANAGER_CONTACT)
            .businessName(DEFAULT_BUSINESS_NAME)
            .businessBank(DEFAULT_BUSINESS_BANK)
            .businessCity(DEFAULT_BUSINESS_CITY)
            .businessAccount(DEFAULT_BUSINESS_ACCOUNT)
            .businessLicense(DEFAULT_BUSINESS_LICENSE)
            .businessLicenseContentType(DEFAULT_BUSINESS_LICENSE_CONTENT_TYPE)
            .businessScope(DEFAULT_BUSINESS_SCOPE)
            .businessScopePreLicense(DEFAULT_BUSINESS_SCOPE_PRE_LICENSE)
            .businessYears(DEFAULT_BUSINESS_YEARS)
            .character(DEFAULT_CHARACTER)
            .scale(DEFAULT_SCALE)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .subsidiary(DEFAULT_SUBSIDIARY)
            .haveSubsidiary(DEFAULT_HAVE_SUBSIDIARY)
            .qualityCertificaionSystem(DEFAULT_QUALITY_CERTIFICAION_SYSTEM)
            .certificationOrg(DEFAULT_CERTIFICATION_ORG)
            .certificationTime(DEFAULT_CERTIFICATION_TIME)
            .employeesNumber(DEFAULT_EMPLOYEES_NUMBER)
            .employeesBachelorDegreeOrAbove(DEFAULT_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE)
            .employeesTechnical(DEFAULT_EMPLOYEES_TECHNICAL)
            .employeesCertification(DEFAULT_EMPLOYEES_CERTIFICATION)
            .registeredCapital(DEFAULT_REGISTERED_CAPITAL)
            .developTeam(DEFAULT_DEVELOP_TEAM)
            .testTeam(DEFAULT_TEST_TEAM)
            .complaints(DEFAULT_COMPLAINTS)
            .training(DEFAULT_TRAINING)
            .trainingDocuments(DEFAULT_TRAINING_DOCUMENTS)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE);
        return serviceProvider;
    }

    @Before
    public void initTest() {
        serviceProviderSearchRepository.deleteAll();
        serviceProvider = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceProvider() throws Exception {
        int databaseSizeBeforeCreate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider
        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isCreated());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceProvider testServiceProvider = serviceProviderList.get(serviceProviderList.size() - 1);
        assertThat(testServiceProvider.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServiceProvider.getEnterpriseCreatedTime()).isEqualTo(DEFAULT_ENTERPRISE_CREATED_TIME);
        assertThat(testServiceProvider.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceProvider.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testServiceProvider.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testServiceProvider.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testServiceProvider.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testServiceProvider.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testServiceProvider.getOrganizationCode()).isEqualTo(DEFAULT_ORGANIZATION_CODE);
        assertThat(testServiceProvider.getBusinessLicenseNumber()).isEqualTo(DEFAULT_BUSINESS_LICENSE_NUMBER);
        assertThat(testServiceProvider.getLegalRepresentative()).isEqualTo(DEFAULT_LEGAL_REPRESENTATIVE);
        assertThat(testServiceProvider.getGeneralManager()).isEqualTo(DEFAULT_GENERAL_MANAGER);
        assertThat(testServiceProvider.getTechnicalManager()).isEqualTo(DEFAULT_TECHNICAL_MANAGER);
        assertThat(testServiceProvider.getQualityManager()).isEqualTo(DEFAULT_QUALITY_MANAGER);
        assertThat(testServiceProvider.getQualityManagerContact()).isEqualTo(DEFAULT_QUALITY_MANAGER_CONTACT);
        assertThat(testServiceProvider.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testServiceProvider.getBusinessBank()).isEqualTo(DEFAULT_BUSINESS_BANK);
        assertThat(testServiceProvider.getBusinessCity()).isEqualTo(DEFAULT_BUSINESS_CITY);
        assertThat(testServiceProvider.getBusinessAccount()).isEqualTo(DEFAULT_BUSINESS_ACCOUNT);
        assertThat(testServiceProvider.getBusinessLicense()).isEqualTo(DEFAULT_BUSINESS_LICENSE);
        assertThat(testServiceProvider.getBusinessLicenseContentType()).isEqualTo(DEFAULT_BUSINESS_LICENSE_CONTENT_TYPE);
        assertThat(testServiceProvider.getBusinessScope()).isEqualTo(DEFAULT_BUSINESS_SCOPE);
        assertThat(testServiceProvider.getBusinessScopePreLicense()).isEqualTo(DEFAULT_BUSINESS_SCOPE_PRE_LICENSE);
        assertThat(testServiceProvider.getBusinessYears()).isEqualTo(DEFAULT_BUSINESS_YEARS);
        assertThat(testServiceProvider.getCharacter()).isEqualTo(DEFAULT_CHARACTER);
        assertThat(testServiceProvider.getScale()).isEqualTo(DEFAULT_SCALE);
        assertThat(testServiceProvider.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testServiceProvider.isSubsidiary()).isEqualTo(DEFAULT_SUBSIDIARY);
        assertThat(testServiceProvider.isHaveSubsidiary()).isEqualTo(DEFAULT_HAVE_SUBSIDIARY);
        assertThat(testServiceProvider.getQualityCertificaionSystem()).isEqualTo(DEFAULT_QUALITY_CERTIFICAION_SYSTEM);
        assertThat(testServiceProvider.getCertificationOrg()).isEqualTo(DEFAULT_CERTIFICATION_ORG);
        assertThat(testServiceProvider.getCertificationTime()).isEqualTo(DEFAULT_CERTIFICATION_TIME);
        assertThat(testServiceProvider.getEmployeesNumber()).isEqualTo(DEFAULT_EMPLOYEES_NUMBER);
        assertThat(testServiceProvider.getEmployeesBachelorDegreeOrAbove()).isEqualTo(DEFAULT_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE);
        assertThat(testServiceProvider.getEmployeesTechnical()).isEqualTo(DEFAULT_EMPLOYEES_TECHNICAL);
        assertThat(testServiceProvider.getEmployeesCertification()).isEqualTo(DEFAULT_EMPLOYEES_CERTIFICATION);
        assertThat(testServiceProvider.getRegisteredCapital()).isEqualTo(DEFAULT_REGISTERED_CAPITAL);
        assertThat(testServiceProvider.isDevelopTeam()).isEqualTo(DEFAULT_DEVELOP_TEAM);
        assertThat(testServiceProvider.isTestTeam()).isEqualTo(DEFAULT_TEST_TEAM);
        assertThat(testServiceProvider.isComplaints()).isEqualTo(DEFAULT_COMPLAINTS);
        assertThat(testServiceProvider.isTraining()).isEqualTo(DEFAULT_TRAINING);
        assertThat(testServiceProvider.isTrainingDocuments()).isEqualTo(DEFAULT_TRAINING_DOCUMENTS);
        assertThat(testServiceProvider.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testServiceProvider.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the ServiceProvider in Elasticsearch
        ServiceProvider serviceProviderEs = serviceProviderSearchRepository.findOne(testServiceProvider.getId());
        assertThat(serviceProviderEs).isEqualToComparingFieldByField(testServiceProvider);
    }

    @Test
    @Transactional
    public void createServiceProviderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider with an existing ID
        serviceProvider.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setName(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setDescription(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganizationCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setOrganizationCode(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusinessLicenseNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setBusinessLicenseNumber(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegalRepresentativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setLegalRepresentative(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneralManagerIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceProviderRepository.findAll().size();
        // set the field null
        serviceProvider.setGeneralManager(null);

        // Create the ServiceProvider, which fails.

        restServiceProviderMockMvc.perform(post("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isBadRequest());

        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceProviders() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get all the serviceProviderList
        restServiceProviderMockMvc.perform(get("/api/service-providers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enterpriseCreatedTime").value(hasItem(DEFAULT_ENTERPRISE_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].organizationCode").value(hasItem(DEFAULT_ORGANIZATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].businessLicenseNumber").value(hasItem(DEFAULT_BUSINESS_LICENSE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].legalRepresentative").value(hasItem(DEFAULT_LEGAL_REPRESENTATIVE.toString())))
            .andExpect(jsonPath("$.[*].generalManager").value(hasItem(DEFAULT_GENERAL_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].technicalManager").value(hasItem(DEFAULT_TECHNICAL_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].qualityManager").value(hasItem(DEFAULT_QUALITY_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].qualityManagerContact").value(hasItem(DEFAULT_QUALITY_MANAGER_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].businessBank").value(hasItem(DEFAULT_BUSINESS_BANK.toString())))
            .andExpect(jsonPath("$.[*].businessCity").value(hasItem(DEFAULT_BUSINESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].businessAccount").value(hasItem(DEFAULT_BUSINESS_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].businessLicenseContentType").value(hasItem(DEFAULT_BUSINESS_LICENSE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].businessLicense").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUSINESS_LICENSE))))
            .andExpect(jsonPath("$.[*].businessScope").value(hasItem(DEFAULT_BUSINESS_SCOPE.toString())))
            .andExpect(jsonPath("$.[*].businessScopePreLicense").value(hasItem(DEFAULT_BUSINESS_SCOPE_PRE_LICENSE.toString())))
            .andExpect(jsonPath("$.[*].businessYears").value(hasItem(DEFAULT_BUSINESS_YEARS)))
            .andExpect(jsonPath("$.[*].character").value(hasItem(DEFAULT_CHARACTER.toString())))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].subsidiary").value(hasItem(DEFAULT_SUBSIDIARY.booleanValue())))
            .andExpect(jsonPath("$.[*].haveSubsidiary").value(hasItem(DEFAULT_HAVE_SUBSIDIARY.booleanValue())))
            .andExpect(jsonPath("$.[*].qualityCertificaionSystem").value(hasItem(DEFAULT_QUALITY_CERTIFICAION_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].certificationOrg").value(hasItem(DEFAULT_CERTIFICATION_ORG.toString())))
            .andExpect(jsonPath("$.[*].certificationTime").value(hasItem(DEFAULT_CERTIFICATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].employeesNumber").value(hasItem(DEFAULT_EMPLOYEES_NUMBER)))
            .andExpect(jsonPath("$.[*].employeesBachelorDegreeOrAbove").value(hasItem(DEFAULT_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE)))
            .andExpect(jsonPath("$.[*].employeesTechnical").value(hasItem(DEFAULT_EMPLOYEES_TECHNICAL)))
            .andExpect(jsonPath("$.[*].employeesCertification").value(hasItem(DEFAULT_EMPLOYEES_CERTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].registeredCapital").value(hasItem(DEFAULT_REGISTERED_CAPITAL)))
            .andExpect(jsonPath("$.[*].developTeam").value(hasItem(DEFAULT_DEVELOP_TEAM.booleanValue())))
            .andExpect(jsonPath("$.[*].testTeam").value(hasItem(DEFAULT_TEST_TEAM.booleanValue())))
            .andExpect(jsonPath("$.[*].complaints").value(hasItem(DEFAULT_COMPLAINTS.booleanValue())))
            .andExpect(jsonPath("$.[*].training").value(hasItem(DEFAULT_TRAINING.booleanValue())))
            .andExpect(jsonPath("$.[*].trainingDocuments").value(hasItem(DEFAULT_TRAINING_DOCUMENTS.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);

        // Get the serviceProvider
        restServiceProviderMockMvc.perform(get("/api/service-providers/{id}", serviceProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceProvider.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enterpriseCreatedTime").value(DEFAULT_ENTERPRISE_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.organizationCode").value(DEFAULT_ORGANIZATION_CODE.toString()))
            .andExpect(jsonPath("$.businessLicenseNumber").value(DEFAULT_BUSINESS_LICENSE_NUMBER.toString()))
            .andExpect(jsonPath("$.legalRepresentative").value(DEFAULT_LEGAL_REPRESENTATIVE.toString()))
            .andExpect(jsonPath("$.generalManager").value(DEFAULT_GENERAL_MANAGER.toString()))
            .andExpect(jsonPath("$.technicalManager").value(DEFAULT_TECHNICAL_MANAGER.toString()))
            .andExpect(jsonPath("$.qualityManager").value(DEFAULT_QUALITY_MANAGER.toString()))
            .andExpect(jsonPath("$.qualityManagerContact").value(DEFAULT_QUALITY_MANAGER_CONTACT.toString()))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME.toString()))
            .andExpect(jsonPath("$.businessBank").value(DEFAULT_BUSINESS_BANK.toString()))
            .andExpect(jsonPath("$.businessCity").value(DEFAULT_BUSINESS_CITY.toString()))
            .andExpect(jsonPath("$.businessAccount").value(DEFAULT_BUSINESS_ACCOUNT.toString()))
            .andExpect(jsonPath("$.businessLicenseContentType").value(DEFAULT_BUSINESS_LICENSE_CONTENT_TYPE))
            .andExpect(jsonPath("$.businessLicense").value(Base64Utils.encodeToString(DEFAULT_BUSINESS_LICENSE)))
            .andExpect(jsonPath("$.businessScope").value(DEFAULT_BUSINESS_SCOPE.toString()))
            .andExpect(jsonPath("$.businessScopePreLicense").value(DEFAULT_BUSINESS_SCOPE_PRE_LICENSE.toString()))
            .andExpect(jsonPath("$.businessYears").value(DEFAULT_BUSINESS_YEARS))
            .andExpect(jsonPath("$.character").value(DEFAULT_CHARACTER.toString()))
            .andExpect(jsonPath("$.scale").value(DEFAULT_SCALE.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.subsidiary").value(DEFAULT_SUBSIDIARY.booleanValue()))
            .andExpect(jsonPath("$.haveSubsidiary").value(DEFAULT_HAVE_SUBSIDIARY.booleanValue()))
            .andExpect(jsonPath("$.qualityCertificaionSystem").value(DEFAULT_QUALITY_CERTIFICAION_SYSTEM.toString()))
            .andExpect(jsonPath("$.certificationOrg").value(DEFAULT_CERTIFICATION_ORG.toString()))
            .andExpect(jsonPath("$.certificationTime").value(DEFAULT_CERTIFICATION_TIME.toString()))
            .andExpect(jsonPath("$.employeesNumber").value(DEFAULT_EMPLOYEES_NUMBER))
            .andExpect(jsonPath("$.employeesBachelorDegreeOrAbove").value(DEFAULT_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE))
            .andExpect(jsonPath("$.employeesTechnical").value(DEFAULT_EMPLOYEES_TECHNICAL))
            .andExpect(jsonPath("$.employeesCertification").value(DEFAULT_EMPLOYEES_CERTIFICATION.toString()))
            .andExpect(jsonPath("$.registeredCapital").value(DEFAULT_REGISTERED_CAPITAL))
            .andExpect(jsonPath("$.developTeam").value(DEFAULT_DEVELOP_TEAM.booleanValue()))
            .andExpect(jsonPath("$.testTeam").value(DEFAULT_TEST_TEAM.booleanValue()))
            .andExpect(jsonPath("$.complaints").value(DEFAULT_COMPLAINTS.booleanValue()))
            .andExpect(jsonPath("$.training").value(DEFAULT_TRAINING.booleanValue()))
            .andExpect(jsonPath("$.trainingDocuments").value(DEFAULT_TRAINING_DOCUMENTS.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceProvider() throws Exception {
        // Get the serviceProvider
        restServiceProviderMockMvc.perform(get("/api/service-providers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);
        serviceProviderSearchRepository.save(serviceProvider);
        int databaseSizeBeforeUpdate = serviceProviderRepository.findAll().size();

        // Update the serviceProvider
        ServiceProvider updatedServiceProvider = serviceProviderRepository.findOne(serviceProvider.getId());
        updatedServiceProvider
            .name(UPDATED_NAME)
            .enterpriseCreatedTime(UPDATED_ENTERPRISE_CREATED_TIME)
            .description(UPDATED_DESCRIPTION)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .address(UPDATED_ADDRESS)
            .zipCode(UPDATED_ZIP_CODE)
            .organizationCode(UPDATED_ORGANIZATION_CODE)
            .businessLicenseNumber(UPDATED_BUSINESS_LICENSE_NUMBER)
            .legalRepresentative(UPDATED_LEGAL_REPRESENTATIVE)
            .generalManager(UPDATED_GENERAL_MANAGER)
            .technicalManager(UPDATED_TECHNICAL_MANAGER)
            .qualityManager(UPDATED_QUALITY_MANAGER)
            .qualityManagerContact(UPDATED_QUALITY_MANAGER_CONTACT)
            .businessName(UPDATED_BUSINESS_NAME)
            .businessBank(UPDATED_BUSINESS_BANK)
            .businessCity(UPDATED_BUSINESS_CITY)
            .businessAccount(UPDATED_BUSINESS_ACCOUNT)
            .businessLicense(UPDATED_BUSINESS_LICENSE)
            .businessLicenseContentType(UPDATED_BUSINESS_LICENSE_CONTENT_TYPE)
            .businessScope(UPDATED_BUSINESS_SCOPE)
            .businessScopePreLicense(UPDATED_BUSINESS_SCOPE_PRE_LICENSE)
            .businessYears(UPDATED_BUSINESS_YEARS)
            .character(UPDATED_CHARACTER)
            .scale(UPDATED_SCALE)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .subsidiary(UPDATED_SUBSIDIARY)
            .haveSubsidiary(UPDATED_HAVE_SUBSIDIARY)
            .qualityCertificaionSystem(UPDATED_QUALITY_CERTIFICAION_SYSTEM)
            .certificationOrg(UPDATED_CERTIFICATION_ORG)
            .certificationTime(UPDATED_CERTIFICATION_TIME)
            .employeesNumber(UPDATED_EMPLOYEES_NUMBER)
            .employeesBachelorDegreeOrAbove(UPDATED_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE)
            .employeesTechnical(UPDATED_EMPLOYEES_TECHNICAL)
            .employeesCertification(UPDATED_EMPLOYEES_CERTIFICATION)
            .registeredCapital(UPDATED_REGISTERED_CAPITAL)
            .developTeam(UPDATED_DEVELOP_TEAM)
            .testTeam(UPDATED_TEST_TEAM)
            .complaints(UPDATED_COMPLAINTS)
            .training(UPDATED_TRAINING)
            .trainingDocuments(UPDATED_TRAINING_DOCUMENTS)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);

        restServiceProviderMockMvc.perform(put("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceProvider)))
            .andExpect(status().isOk());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeUpdate);
        ServiceProvider testServiceProvider = serviceProviderList.get(serviceProviderList.size() - 1);
        assertThat(testServiceProvider.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServiceProvider.getEnterpriseCreatedTime()).isEqualTo(UPDATED_ENTERPRISE_CREATED_TIME);
        assertThat(testServiceProvider.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceProvider.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testServiceProvider.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testServiceProvider.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testServiceProvider.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testServiceProvider.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testServiceProvider.getOrganizationCode()).isEqualTo(UPDATED_ORGANIZATION_CODE);
        assertThat(testServiceProvider.getBusinessLicenseNumber()).isEqualTo(UPDATED_BUSINESS_LICENSE_NUMBER);
        assertThat(testServiceProvider.getLegalRepresentative()).isEqualTo(UPDATED_LEGAL_REPRESENTATIVE);
        assertThat(testServiceProvider.getGeneralManager()).isEqualTo(UPDATED_GENERAL_MANAGER);
        assertThat(testServiceProvider.getTechnicalManager()).isEqualTo(UPDATED_TECHNICAL_MANAGER);
        assertThat(testServiceProvider.getQualityManager()).isEqualTo(UPDATED_QUALITY_MANAGER);
        assertThat(testServiceProvider.getQualityManagerContact()).isEqualTo(UPDATED_QUALITY_MANAGER_CONTACT);
        assertThat(testServiceProvider.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testServiceProvider.getBusinessBank()).isEqualTo(UPDATED_BUSINESS_BANK);
        assertThat(testServiceProvider.getBusinessCity()).isEqualTo(UPDATED_BUSINESS_CITY);
        assertThat(testServiceProvider.getBusinessAccount()).isEqualTo(UPDATED_BUSINESS_ACCOUNT);
        assertThat(testServiceProvider.getBusinessLicense()).isEqualTo(UPDATED_BUSINESS_LICENSE);
        assertThat(testServiceProvider.getBusinessLicenseContentType()).isEqualTo(UPDATED_BUSINESS_LICENSE_CONTENT_TYPE);
        assertThat(testServiceProvider.getBusinessScope()).isEqualTo(UPDATED_BUSINESS_SCOPE);
        assertThat(testServiceProvider.getBusinessScopePreLicense()).isEqualTo(UPDATED_BUSINESS_SCOPE_PRE_LICENSE);
        assertThat(testServiceProvider.getBusinessYears()).isEqualTo(UPDATED_BUSINESS_YEARS);
        assertThat(testServiceProvider.getCharacter()).isEqualTo(UPDATED_CHARACTER);
        assertThat(testServiceProvider.getScale()).isEqualTo(UPDATED_SCALE);
        assertThat(testServiceProvider.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testServiceProvider.isSubsidiary()).isEqualTo(UPDATED_SUBSIDIARY);
        assertThat(testServiceProvider.isHaveSubsidiary()).isEqualTo(UPDATED_HAVE_SUBSIDIARY);
        assertThat(testServiceProvider.getQualityCertificaionSystem()).isEqualTo(UPDATED_QUALITY_CERTIFICAION_SYSTEM);
        assertThat(testServiceProvider.getCertificationOrg()).isEqualTo(UPDATED_CERTIFICATION_ORG);
        assertThat(testServiceProvider.getCertificationTime()).isEqualTo(UPDATED_CERTIFICATION_TIME);
        assertThat(testServiceProvider.getEmployeesNumber()).isEqualTo(UPDATED_EMPLOYEES_NUMBER);
        assertThat(testServiceProvider.getEmployeesBachelorDegreeOrAbove()).isEqualTo(UPDATED_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE);
        assertThat(testServiceProvider.getEmployeesTechnical()).isEqualTo(UPDATED_EMPLOYEES_TECHNICAL);
        assertThat(testServiceProvider.getEmployeesCertification()).isEqualTo(UPDATED_EMPLOYEES_CERTIFICATION);
        assertThat(testServiceProvider.getRegisteredCapital()).isEqualTo(UPDATED_REGISTERED_CAPITAL);
        assertThat(testServiceProvider.isDevelopTeam()).isEqualTo(UPDATED_DEVELOP_TEAM);
        assertThat(testServiceProvider.isTestTeam()).isEqualTo(UPDATED_TEST_TEAM);
        assertThat(testServiceProvider.isComplaints()).isEqualTo(UPDATED_COMPLAINTS);
        assertThat(testServiceProvider.isTraining()).isEqualTo(UPDATED_TRAINING);
        assertThat(testServiceProvider.isTrainingDocuments()).isEqualTo(UPDATED_TRAINING_DOCUMENTS);
        assertThat(testServiceProvider.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testServiceProvider.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the ServiceProvider in Elasticsearch
        ServiceProvider serviceProviderEs = serviceProviderSearchRepository.findOne(testServiceProvider.getId());
        assertThat(serviceProviderEs).isEqualToComparingFieldByField(testServiceProvider);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceProvider() throws Exception {
        int databaseSizeBeforeUpdate = serviceProviderRepository.findAll().size();

        // Create the ServiceProvider

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServiceProviderMockMvc.perform(put("/api/service-providers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceProvider)))
            .andExpect(status().isCreated());

        // Validate the ServiceProvider in the database
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);
        serviceProviderSearchRepository.save(serviceProvider);
        int databaseSizeBeforeDelete = serviceProviderRepository.findAll().size();

        // Get the serviceProvider
        restServiceProviderMockMvc.perform(delete("/api/service-providers/{id}", serviceProvider.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean serviceProviderExistsInEs = serviceProviderSearchRepository.exists(serviceProvider.getId());
        assertThat(serviceProviderExistsInEs).isFalse();

        // Validate the database is empty
        List<ServiceProvider> serviceProviderList = serviceProviderRepository.findAll();
        assertThat(serviceProviderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchServiceProvider() throws Exception {
        // Initialize the database
        serviceProviderRepository.saveAndFlush(serviceProvider);
        serviceProviderSearchRepository.save(serviceProvider);

        // Search the serviceProvider
        restServiceProviderMockMvc.perform(get("/api/_search/service-providers?query=id:" + serviceProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceProvider.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].enterpriseCreatedTime").value(hasItem(DEFAULT_ENTERPRISE_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].organizationCode").value(hasItem(DEFAULT_ORGANIZATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].businessLicenseNumber").value(hasItem(DEFAULT_BUSINESS_LICENSE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].legalRepresentative").value(hasItem(DEFAULT_LEGAL_REPRESENTATIVE.toString())))
            .andExpect(jsonPath("$.[*].generalManager").value(hasItem(DEFAULT_GENERAL_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].technicalManager").value(hasItem(DEFAULT_TECHNICAL_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].qualityManager").value(hasItem(DEFAULT_QUALITY_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].qualityManagerContact").value(hasItem(DEFAULT_QUALITY_MANAGER_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].businessBank").value(hasItem(DEFAULT_BUSINESS_BANK.toString())))
            .andExpect(jsonPath("$.[*].businessCity").value(hasItem(DEFAULT_BUSINESS_CITY.toString())))
            .andExpect(jsonPath("$.[*].businessAccount").value(hasItem(DEFAULT_BUSINESS_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].businessLicenseContentType").value(hasItem(DEFAULT_BUSINESS_LICENSE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].businessLicense").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUSINESS_LICENSE))))
            .andExpect(jsonPath("$.[*].businessScope").value(hasItem(DEFAULT_BUSINESS_SCOPE.toString())))
            .andExpect(jsonPath("$.[*].businessScopePreLicense").value(hasItem(DEFAULT_BUSINESS_SCOPE_PRE_LICENSE.toString())))
            .andExpect(jsonPath("$.[*].businessYears").value(hasItem(DEFAULT_BUSINESS_YEARS)))
            .andExpect(jsonPath("$.[*].character").value(hasItem(DEFAULT_CHARACTER.toString())))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].subsidiary").value(hasItem(DEFAULT_SUBSIDIARY.booleanValue())))
            .andExpect(jsonPath("$.[*].haveSubsidiary").value(hasItem(DEFAULT_HAVE_SUBSIDIARY.booleanValue())))
            .andExpect(jsonPath("$.[*].qualityCertificaionSystem").value(hasItem(DEFAULT_QUALITY_CERTIFICAION_SYSTEM.toString())))
            .andExpect(jsonPath("$.[*].certificationOrg").value(hasItem(DEFAULT_CERTIFICATION_ORG.toString())))
            .andExpect(jsonPath("$.[*].certificationTime").value(hasItem(DEFAULT_CERTIFICATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].employeesNumber").value(hasItem(DEFAULT_EMPLOYEES_NUMBER)))
            .andExpect(jsonPath("$.[*].employeesBachelorDegreeOrAbove").value(hasItem(DEFAULT_EMPLOYEES_BACHELOR_DEGREE_OR_ABOVE)))
            .andExpect(jsonPath("$.[*].employeesTechnical").value(hasItem(DEFAULT_EMPLOYEES_TECHNICAL)))
            .andExpect(jsonPath("$.[*].employeesCertification").value(hasItem(DEFAULT_EMPLOYEES_CERTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].registeredCapital").value(hasItem(DEFAULT_REGISTERED_CAPITAL)))
            .andExpect(jsonPath("$.[*].developTeam").value(hasItem(DEFAULT_DEVELOP_TEAM.booleanValue())))
            .andExpect(jsonPath("$.[*].testTeam").value(hasItem(DEFAULT_TEST_TEAM.booleanValue())))
            .andExpect(jsonPath("$.[*].complaints").value(hasItem(DEFAULT_COMPLAINTS.booleanValue())))
            .andExpect(jsonPath("$.[*].training").value(hasItem(DEFAULT_TRAINING.booleanValue())))
            .andExpect(jsonPath("$.[*].trainingDocuments").value(hasItem(DEFAULT_TRAINING_DOCUMENTS.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceProvider.class);
        ServiceProvider serviceProvider1 = new ServiceProvider();
        serviceProvider1.setId(1L);
        ServiceProvider serviceProvider2 = new ServiceProvider();
        serviceProvider2.setId(serviceProvider1.getId());
        assertThat(serviceProvider1).isEqualTo(serviceProvider2);
        serviceProvider2.setId(2L);
        assertThat(serviceProvider1).isNotEqualTo(serviceProvider2);
        serviceProvider1.setId(null);
        assertThat(serviceProvider1).isNotEqualTo(serviceProvider2);
    }
}
