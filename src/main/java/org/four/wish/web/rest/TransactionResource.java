package org.four.wish.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.four.wish.domain.BillingCard;
import org.four.wish.domain.Transaction;

import org.four.wish.repository.BillingCardRepository;
import org.four.wish.repository.TransactionRepository;
import org.four.wish.repository.search.TransactionSearchRepository;
import org.four.wish.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Transaction.
 */
@RestController
@RequestMapping("/api")
public class TransactionResource {

    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    private static final String ENTITY_NAME = "transaction";

    private final TransactionRepository transactionRepository;

    private final TransactionSearchRepository transactionSearchRepository;

    private final BillingCardRepository billingCardRepository;

    public TransactionResource(BillingCardRepository billingCardRepository, TransactionRepository transactionRepository, TransactionSearchRepository transactionSearchRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionSearchRepository = transactionSearchRepository;
        this.billingCardRepository = billingCardRepository;
    }

    /**
     * POST  /transactions : Create a new transaction.
     *
     * @param transaction the transaction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transaction, or with status 400 (Bad Request) if the transaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transactions")
    @Timed
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to save Transaction : {}", transaction);
        if (transaction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new transaction cannot already have an ID")).body(null);
        }

        //set fromUser
        transaction.setFromUser(transaction.getFromPerson().getUser());
        //set toUser
        transaction.setToUser(transaction.getToPerson().getUser());
        //set time
        transaction.setTime(ZonedDateTime.now());

        //fromBillingCard
        Optional<BillingCard> fromBillingCard = billingCardRepository.findOneByUser(transaction.getFromUser());
        if (fromBillingCard.isPresent()){
            fromBillingCard.get().setAb(fromBillingCard.get().getAb() - transaction.getAmount());
            billingCardRepository.save(fromBillingCard.get());
        }
        else{
            BillingCard newBillCard = new BillingCard();
            newBillCard.setUser(transaction.getFromUser());
            newBillCard.setAb(0-transaction.getAmount());
            billingCardRepository.save(newBillCard);
        }

        //toBillingCard
        Optional<BillingCard> toBillingCard = billingCardRepository.findOneByUser(transaction.getToUser());
        if (toBillingCard.isPresent()){
            toBillingCard.get().setAb(toBillingCard.get().getAb() + transaction.getAmount());
            billingCardRepository.save(toBillingCard.get());
        }
        else{
            BillingCard newBillCard = new BillingCard();
            newBillCard.setUser(transaction.getToUser());
            newBillCard.setAb(transaction.getAmount());
            billingCardRepository.save(newBillCard);
        }

        Transaction result = transactionRepository.save(transaction);
        transactionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transactions : Updates an existing transaction.
     *
     * @param transaction the transaction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transaction,
     * or with status 400 (Bad Request) if the transaction is not valid,
     * or with status 500 (Internal Server Error) if the transaction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transactions")
    @Timed
    public ResponseEntity<Transaction> updateTransaction(@Valid @RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to update Transaction : {}", transaction);
        if (transaction.getId() == null) {
            return createTransaction(transaction);
        }
        /*Transaction result = transactionRepository.save(transaction);
        transactionSearchRepository.save(result);*/
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transaction.getId().toString()))
            .body(null);
    }

    /**
     * GET  /transactions : get all the transactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactions in body
     */
    @GetMapping("/transactions")
    @Timed
    public List<Transaction> getAllTransactions() {
        log.debug("REST request to get all Transactions");
        return transactionRepository.findAll();
    }

    /**
     * GET  /transactions/:id : get the "id" transaction.
     *
     * @param id the id of the transaction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transaction, or with status 404 (Not Found)
     */
    @GetMapping("/transactions/{id}")
    @Timed
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        log.debug("REST request to get Transaction : {}", id);
        Transaction transaction = transactionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(transaction));
    }

    /**
     * DELETE  /transactions/:id : delete the "id" transaction.
     *
     * @param id the id of the transaction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transactions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.debug("REST request to delete Transaction : {}", id);
        /*transactionRepository.delete(id);
        transactionSearchRepository.delete(id);*/
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/transactions?query=:query : search for the transaction corresponding
     * to the query.
     *
     * @param query the query of the transaction search
     * @return the result of the search
     */
    @GetMapping("/_search/transactions")
    @Timed
    public List<Transaction> searchTransactions(@RequestParam String query) {
        log.debug("REST request to search Transactions for query {}", query);
        return StreamSupport
            .stream(transactionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
