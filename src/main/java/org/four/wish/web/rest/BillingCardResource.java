package org.four.wish.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.four.wish.domain.BillingCard;

import org.four.wish.repository.BillingCardRepository;
import org.four.wish.repository.search.BillingCardSearchRepository;
import org.four.wish.security.SecurityUtils;
import org.four.wish.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BillingCard.
 */
@RestController
@RequestMapping("/api")
public class BillingCardResource {

    private final Logger log = LoggerFactory.getLogger(BillingCardResource.class);

    private static final String ENTITY_NAME = "billingCard";

    private final BillingCardRepository billingCardRepository;

    private final BillingCardSearchRepository billingCardSearchRepository;

    public BillingCardResource(BillingCardRepository billingCardRepository, BillingCardSearchRepository billingCardSearchRepository) {
        this.billingCardRepository = billingCardRepository;
        this.billingCardSearchRepository = billingCardSearchRepository;
    }

    /**
     * POST  /billing-cards : Create a new billingCard.
     *
     * @param billingCard the billingCard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billingCard, or with status 400 (Bad Request) if the billingCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billing-cards")
    @Timed
    public ResponseEntity<BillingCard> createBillingCard(@Valid @RequestBody BillingCard billingCard) throws URISyntaxException {
        log.debug("REST request to save BillingCard : {}", billingCard);
        if (billingCard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new billingCard cannot already have an ID")).body(null);
        }
        BillingCard result = billingCardRepository.save(billingCard);
        billingCardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/billing-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billing-cards : Updates an existing billingCard.
     *
     * @param billingCard the billingCard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billingCard,
     * or with status 400 (Bad Request) if the billingCard is not valid,
     * or with status 500 (Internal Server Error) if the billingCard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billing-cards")
    @Timed
    public ResponseEntity<BillingCard> updateBillingCard(@Valid @RequestBody BillingCard billingCard) throws URISyntaxException {
        log.debug("REST request to update BillingCard : {}", billingCard);
        if (billingCard.getId() == null) {
            return createBillingCard(billingCard);
        }
        BillingCard result = billingCardRepository.save(billingCard);
        billingCardSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billingCard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billing-cards : get all the billingCards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of billingCards in body
     */
    @GetMapping("/billing-cards")
    @Timed
    public List<BillingCard> getAllBillingCards() {
        log.debug("REST request to get all BillingCards");
            return billingCardRepository.findAll();

    }

    @GetMapping("/billing-card")
    @Timed
    public BillingCard getMyBillingCard(){
        log.debug("REST request to get my BillingCard");
        return billingCardRepository.findOneByCurrentUser();
    }

    /**
     * GET  /billing-cards/:id : get the "id" billingCard.
     *
     * @param id the id of the billingCard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billingCard, or with status 404 (Not Found)
     */
    @GetMapping("/billing-cards/{id}")
    @Timed
    public ResponseEntity<BillingCard> getBillingCard(@PathVariable Long id) {
        log.debug("REST request to get BillingCard : {}", id);
        BillingCard billingCard = billingCardRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(billingCard));
    }

    /**
     * DELETE  /billing-cards/:id : delete the "id" billingCard.
     *
     * @param id the id of the billingCard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billing-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteBillingCard(@PathVariable Long id) {
        log.debug("REST request to delete BillingCard : {}", id);
        billingCardRepository.delete(id);
        billingCardSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/billing-cards?query=:query : search for the billingCard corresponding
     * to the query.
     *
     * @param query the query of the billingCard search
     * @return the result of the search
     */
    @GetMapping("/_search/billing-cards")
    @Timed
    public List<BillingCard> searchBillingCards(@RequestParam String query) {
        log.debug("REST request to search BillingCards for query {}", query);
        return StreamSupport
            .stream(billingCardSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
