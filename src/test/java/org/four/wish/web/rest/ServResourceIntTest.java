package org.four.wish.web.rest;

import org.four.wish.WishApp;

import org.four.wish.domain.Serv;
import org.four.wish.repository.ServRepository;
import org.four.wish.repository.search.ServSearchRepository;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServResource REST controller.
 *
 * @see ServResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WishApp.class)
public class ServResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TERMA = "AAAAAAAAAA";
    private static final String UPDATED_TERMA = "BBBBBBBBBB";

    private static final String DEFAULT_TERMB = "AAAAAAAAAA";
    private static final String UPDATED_TERMB = "BBBBBBBBBB";

    private static final String DEFAULT_TERMC = "AAAAAAAAAA";
    private static final String UPDATED_TERMC = "BBBBBBBBBB";

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
    private ServRepository servRepository;

    @Autowired
    private ServSearchRepository servSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServMockMvc;

    private Serv serv;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ServResource servResource = new ServResource(servRepository, servSearchRepository);
        this.restServMockMvc = MockMvcBuilders.standaloneSetup(servResource)
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
    public static Serv createEntity(EntityManager em) {
        Serv serv = new Serv()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .unit(DEFAULT_UNIT)
            .description(DEFAULT_DESCRIPTION)
            .terma(DEFAULT_TERMA)
            .termb(DEFAULT_TERMB)
            .termc(DEFAULT_TERMC)
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
        return serv;
    }

    @Before
    public void initTest() {
        servSearchRepository.deleteAll();
        serv = createEntity(em);
    }

    @Test
    @Transactional
    public void createServ() throws Exception {
        int databaseSizeBeforeCreate = servRepository.findAll().size();

        // Create the Serv
        restServMockMvc.perform(post("/api/servs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serv)))
            .andExpect(status().isCreated());

        // Validate the Serv in the database
        List<Serv> servList = servRepository.findAll();
        assertThat(servList).hasSize(databaseSizeBeforeCreate + 1);
        Serv testServ = servList.get(servList.size() - 1);
        assertThat(testServ.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testServ.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testServ.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testServ.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServ.getTerma()).isEqualTo(DEFAULT_TERMA);
        assertThat(testServ.getTermb()).isEqualTo(DEFAULT_TERMB);
        assertThat(testServ.getTermc()).isEqualTo(DEFAULT_TERMC);
        assertThat(testServ.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testServ.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testServ.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testServ.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testServ.getUpdatedTime()).isEqualTo(DEFAULT_UPDATED_TIME);
        assertThat(testServ.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testServ.getAnnexa()).isEqualTo(DEFAULT_ANNEXA);
        assertThat(testServ.getAnnexaContentType()).isEqualTo(DEFAULT_ANNEXA_CONTENT_TYPE);
        assertThat(testServ.getAnnexb()).isEqualTo(DEFAULT_ANNEXB);
        assertThat(testServ.getAnnexbContentType()).isEqualTo(DEFAULT_ANNEXB_CONTENT_TYPE);
        assertThat(testServ.getAnnexc()).isEqualTo(DEFAULT_ANNEXC);
        assertThat(testServ.getAnnexcContentType()).isEqualTo(DEFAULT_ANNEXC_CONTENT_TYPE);
        assertThat(testServ.getAnnexd()).isEqualTo(DEFAULT_ANNEXD);
        assertThat(testServ.getAnnexdContentType()).isEqualTo(DEFAULT_ANNEXD_CONTENT_TYPE);
        assertThat(testServ.getAnnexe()).isEqualTo(DEFAULT_ANNEXE);
        assertThat(testServ.getAnnexeContentType()).isEqualTo(DEFAULT_ANNEXE_CONTENT_TYPE);

        // Validate the Serv in Elasticsearch
        Serv servEs = servSearchRepository.findOne(testServ.getId());
        assertThat(servEs).isEqualToComparingFieldByField(testServ);
    }

    @Test
    @Transactional
    public void createServWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servRepository.findAll().size();

        // Create the Serv with an existing ID
        serv.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServMockMvc.perform(post("/api/servs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serv)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Serv> servList = servRepository.findAll();
        assertThat(servList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = servRepository.findAll().size();
        // set the field null
        serv.setName(null);

        // Create the Serv, which fails.

        restServMockMvc.perform(post("/api/servs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serv)))
            .andExpect(status().isBadRequest());

        List<Serv> servList = servRepository.findAll();
        assertThat(servList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = servRepository.findAll().size();
        // set the field null
        serv.setPrice(null);

        // Create the Serv, which fails.

        restServMockMvc.perform(post("/api/servs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serv)))
            .andExpect(status().isBadRequest());

        List<Serv> servList = servRepository.findAll();
        assertThat(servList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServs() throws Exception {
        // Initialize the database
        servRepository.saveAndFlush(serv);

        // Get all the servList
        restServMockMvc.perform(get("/api/servs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serv.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].terma").value(hasItem(DEFAULT_TERMA.toString())))
            .andExpect(jsonPath("$.[*].termb").value(hasItem(DEFAULT_TERMB.toString())))
            .andExpect(jsonPath("$.[*].termc").value(hasItem(DEFAULT_TERMC.toString())))
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
    public void getServ() throws Exception {
        // Initialize the database
        servRepository.saveAndFlush(serv);

        // Get the serv
        restServMockMvc.perform(get("/api/servs/{id}", serv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serv.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.terma").value(DEFAULT_TERMA.toString()))
            .andExpect(jsonPath("$.termb").value(DEFAULT_TERMB.toString()))
            .andExpect(jsonPath("$.termc").value(DEFAULT_TERMC.toString()))
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
    public void getNonExistingServ() throws Exception {
        // Get the serv
        restServMockMvc.perform(get("/api/servs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServ() throws Exception {
        // Initialize the database
        servRepository.saveAndFlush(serv);
        servSearchRepository.save(serv);
        int databaseSizeBeforeUpdate = servRepository.findAll().size();

        // Update the serv
        Serv updatedServ = servRepository.findOne(serv.getId());
        updatedServ
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .unit(UPDATED_UNIT)
            .description(UPDATED_DESCRIPTION)
            .terma(UPDATED_TERMA)
            .termb(UPDATED_TERMB)
            .termc(UPDATED_TERMC)
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

        restServMockMvc.perform(put("/api/servs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServ)))
            .andExpect(status().isOk());

        // Validate the Serv in the database
        List<Serv> servList = servRepository.findAll();
        assertThat(servList).hasSize(databaseSizeBeforeUpdate);
        Serv testServ = servList.get(servList.size() - 1);
        assertThat(testServ.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testServ.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testServ.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testServ.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServ.getTerma()).isEqualTo(UPDATED_TERMA);
        assertThat(testServ.getTermb()).isEqualTo(UPDATED_TERMB);
        assertThat(testServ.getTermc()).isEqualTo(UPDATED_TERMC);
        assertThat(testServ.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testServ.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testServ.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testServ.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testServ.getUpdatedTime()).isEqualTo(UPDATED_UPDATED_TIME);
        assertThat(testServ.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testServ.getAnnexa()).isEqualTo(UPDATED_ANNEXA);
        assertThat(testServ.getAnnexaContentType()).isEqualTo(UPDATED_ANNEXA_CONTENT_TYPE);
        assertThat(testServ.getAnnexb()).isEqualTo(UPDATED_ANNEXB);
        assertThat(testServ.getAnnexbContentType()).isEqualTo(UPDATED_ANNEXB_CONTENT_TYPE);
        assertThat(testServ.getAnnexc()).isEqualTo(UPDATED_ANNEXC);
        assertThat(testServ.getAnnexcContentType()).isEqualTo(UPDATED_ANNEXC_CONTENT_TYPE);
        assertThat(testServ.getAnnexd()).isEqualTo(UPDATED_ANNEXD);
        assertThat(testServ.getAnnexdContentType()).isEqualTo(UPDATED_ANNEXD_CONTENT_TYPE);
        assertThat(testServ.getAnnexe()).isEqualTo(UPDATED_ANNEXE);
        assertThat(testServ.getAnnexeContentType()).isEqualTo(UPDATED_ANNEXE_CONTENT_TYPE);

        // Validate the Serv in Elasticsearch
        Serv servEs = servSearchRepository.findOne(testServ.getId());
        assertThat(servEs).isEqualToComparingFieldByField(testServ);
    }

    @Test
    @Transactional
    public void updateNonExistingServ() throws Exception {
        int databaseSizeBeforeUpdate = servRepository.findAll().size();

        // Create the Serv

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServMockMvc.perform(put("/api/servs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serv)))
            .andExpect(status().isCreated());

        // Validate the Serv in the database
        List<Serv> servList = servRepository.findAll();
        assertThat(servList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServ() throws Exception {
        // Initialize the database
        servRepository.saveAndFlush(serv);
        servSearchRepository.save(serv);
        int databaseSizeBeforeDelete = servRepository.findAll().size();

        // Get the serv
        restServMockMvc.perform(delete("/api/servs/{id}", serv.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean servExistsInEs = servSearchRepository.exists(serv.getId());
        assertThat(servExistsInEs).isFalse();

        // Validate the database is empty
        List<Serv> servList = servRepository.findAll();
        assertThat(servList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchServ() throws Exception {
        // Initialize the database
        servRepository.saveAndFlush(serv);
        servSearchRepository.save(serv);

        // Search the serv
        restServMockMvc.perform(get("/api/_search/servs?query=id:" + serv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serv.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].terma").value(hasItem(DEFAULT_TERMA.toString())))
            .andExpect(jsonPath("$.[*].termb").value(hasItem(DEFAULT_TERMB.toString())))
            .andExpect(jsonPath("$.[*].termc").value(hasItem(DEFAULT_TERMC.toString())))
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
        TestUtil.equalsVerifier(Serv.class);
        Serv serv1 = new Serv();
        serv1.setId(1L);
        Serv serv2 = new Serv();
        serv2.setId(serv1.getId());
        assertThat(serv1).isEqualTo(serv2);
        serv2.setId(2L);
        assertThat(serv1).isNotEqualTo(serv2);
        serv1.setId(null);
        assertThat(serv1).isNotEqualTo(serv2);
    }
}
