package org.four.wish.web.rest;

import org.four.wish.WishApp;

import org.four.wish.domain.BillingCard;
import org.four.wish.repository.BillingCardRepository;
import org.four.wish.repository.search.BillingCardSearchRepository;
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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BillingCardResource REST controller.
 *
 * @see BillingCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WishApp.class)
public class BillingCardResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Double DEFAULT_AB = 1D;
    private static final Double UPDATED_AB = 2D;

    private static final Double DEFAULT_MAXAB = 1D;
    private static final Double UPDATED_MAXAB = 2D;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private BillingCardRepository billingCardRepository;

    @Autowired
    private BillingCardSearchRepository billingCardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBillingCardMockMvc;

    private BillingCard billingCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillingCardResource billingCardResource = new BillingCardResource(billingCardRepository, billingCardSearchRepository);
        this.restBillingCardMockMvc = MockMvcBuilders.standaloneSetup(billingCardResource)
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
    public static BillingCard createEntity(EntityManager em) {
        BillingCard billingCard = new BillingCard()
            .user(DEFAULT_USER)
            .ab(DEFAULT_AB)
            .maxab(DEFAULT_MAXAB)
            .remark(DEFAULT_REMARK)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS);
        return billingCard;
    }

    @Before
    public void initTest() {
        billingCardSearchRepository.deleteAll();
        billingCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillingCard() throws Exception {
        int databaseSizeBeforeCreate = billingCardRepository.findAll().size();

        // Create the BillingCard
        restBillingCardMockMvc.perform(post("/api/billing-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingCard)))
            .andExpect(status().isCreated());

        // Validate the BillingCard in the database
        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeCreate + 1);
        BillingCard testBillingCard = billingCardList.get(billingCardList.size() - 1);
        assertThat(testBillingCard.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testBillingCard.getAb()).isEqualTo(DEFAULT_AB);
        assertThat(testBillingCard.getMaxab()).isEqualTo(DEFAULT_MAXAB);
        assertThat(testBillingCard.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testBillingCard.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBillingCard.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBillingCard.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBillingCard.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBillingCard.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testBillingCard.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testBillingCard.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBillingCard.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the BillingCard in Elasticsearch
        BillingCard billingCardEs = billingCardSearchRepository.findOne(testBillingCard.getId());
        assertThat(billingCardEs).isEqualToComparingFieldByField(testBillingCard);
    }

    @Test
    @Transactional
    public void createBillingCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingCardRepository.findAll().size();

        // Create the BillingCard with an existing ID
        billingCard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingCardMockMvc.perform(post("/api/billing-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingCard)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingCardRepository.findAll().size();
        // set the field null
        billingCard.setUser(null);

        // Create the BillingCard, which fails.

        restBillingCardMockMvc.perform(post("/api/billing-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingCard)))
            .andExpect(status().isBadRequest());

        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingCardRepository.findAll().size();
        // set the field null
        billingCard.setAb(null);

        // Create the BillingCard, which fails.

        restBillingCardMockMvc.perform(post("/api/billing-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingCard)))
            .andExpect(status().isBadRequest());

        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingCardRepository.findAll().size();
        // set the field null
        billingCard.setName(null);

        // Create the BillingCard, which fails.

        restBillingCardMockMvc.perform(post("/api/billing-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingCard)))
            .andExpect(status().isBadRequest());

        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillingCards() throws Exception {
        // Initialize the database
        billingCardRepository.saveAndFlush(billingCard);

        // Get all the billingCardList
        restBillingCardMockMvc.perform(get("/api/billing-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].ab").value(hasItem(DEFAULT_AB.doubleValue())))
            .andExpect(jsonPath("$.[*].maxab").value(hasItem(DEFAULT_MAXAB.doubleValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getBillingCard() throws Exception {
        // Initialize the database
        billingCardRepository.saveAndFlush(billingCard);

        // Get the billingCard
        restBillingCardMockMvc.perform(get("/api/billing-cards/{id}", billingCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billingCard.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.ab").value(DEFAULT_AB.doubleValue()))
            .andExpect(jsonPath("$.maxab").value(DEFAULT_MAXAB.doubleValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBillingCard() throws Exception {
        // Get the billingCard
        restBillingCardMockMvc.perform(get("/api/billing-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillingCard() throws Exception {
        // Initialize the database
        billingCardRepository.saveAndFlush(billingCard);
        billingCardSearchRepository.save(billingCard);
        int databaseSizeBeforeUpdate = billingCardRepository.findAll().size();

        // Update the billingCard
        BillingCard updatedBillingCard = billingCardRepository.findOne(billingCard.getId());
        updatedBillingCard
            .user(UPDATED_USER)
            .ab(UPDATED_AB)
            .maxab(UPDATED_MAXAB)
            .remark(UPDATED_REMARK)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);

        restBillingCardMockMvc.perform(put("/api/billing-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBillingCard)))
            .andExpect(status().isOk());

        // Validate the BillingCard in the database
        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeUpdate);
        BillingCard testBillingCard = billingCardList.get(billingCardList.size() - 1);
        assertThat(testBillingCard.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testBillingCard.getAb()).isEqualTo(UPDATED_AB);
        assertThat(testBillingCard.getMaxab()).isEqualTo(UPDATED_MAXAB);
        assertThat(testBillingCard.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testBillingCard.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBillingCard.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBillingCard.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBillingCard.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBillingCard.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testBillingCard.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testBillingCard.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBillingCard.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the BillingCard in Elasticsearch
        BillingCard billingCardEs = billingCardSearchRepository.findOne(testBillingCard.getId());
        assertThat(billingCardEs).isEqualToComparingFieldByField(testBillingCard);
    }

    @Test
    @Transactional
    public void updateNonExistingBillingCard() throws Exception {
        int databaseSizeBeforeUpdate = billingCardRepository.findAll().size();

        // Create the BillingCard

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBillingCardMockMvc.perform(put("/api/billing-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billingCard)))
            .andExpect(status().isCreated());

        // Validate the BillingCard in the database
        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBillingCard() throws Exception {
        // Initialize the database
        billingCardRepository.saveAndFlush(billingCard);
        billingCardSearchRepository.save(billingCard);
        int databaseSizeBeforeDelete = billingCardRepository.findAll().size();

        // Get the billingCard
        restBillingCardMockMvc.perform(delete("/api/billing-cards/{id}", billingCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean billingCardExistsInEs = billingCardSearchRepository.exists(billingCard.getId());
        assertThat(billingCardExistsInEs).isFalse();

        // Validate the database is empty
        List<BillingCard> billingCardList = billingCardRepository.findAll();
        assertThat(billingCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBillingCard() throws Exception {
        // Initialize the database
        billingCardRepository.saveAndFlush(billingCard);
        billingCardSearchRepository.save(billingCard);

        // Search the billingCard
        restBillingCardMockMvc.perform(get("/api/_search/billing-cards?query=id:" + billingCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].ab").value(hasItem(DEFAULT_AB.doubleValue())))
            .andExpect(jsonPath("$.[*].maxab").value(hasItem(DEFAULT_MAXAB.doubleValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingCard.class);
        BillingCard billingCard1 = new BillingCard();
        billingCard1.setId(1L);
        BillingCard billingCard2 = new BillingCard();
        billingCard2.setId(billingCard1.getId());
        assertThat(billingCard1).isEqualTo(billingCard2);
        billingCard2.setId(2L);
        assertThat(billingCard1).isNotEqualTo(billingCard2);
        billingCard1.setId(null);
        assertThat(billingCard1).isNotEqualTo(billingCard2);
    }
}
