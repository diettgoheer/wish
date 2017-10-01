package org.four.wish.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.four.wish.domain.Serv;
import org.four.wish.domain.Transaction;
import org.four.wish.domain.Work;

import org.four.wish.repository.PersonRepository;
import org.four.wish.repository.TransactionRepository;
import org.four.wish.repository.WorkRepository;
import org.four.wish.repository.search.WorkSearchRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Work.
 */
@RestController
@RequestMapping("/api")
public class WorkResource {

    private final Logger log = LoggerFactory.getLogger(WorkResource.class);

    private static final String ENTITY_NAME = "work";

    private final WorkRepository workRepository;

    private final WorkSearchRepository workSearchRepository;

    private final PersonRepository personRepository;

    private final TransactionResource transactionResource;

    public WorkResource( TransactionResource transactionResource, PersonRepository personRepository, WorkRepository workRepository, WorkSearchRepository workSearchRepository) {
        this.transactionResource = transactionResource;
        this.workRepository = workRepository;
        this.workSearchRepository = workSearchRepository;
        this.personRepository = personRepository;
    }

    /**
     * POST  /works : Create a new work.
     *
     * @param work the work to create
     * @return the ResponseEntity with status 201 (Created) and with body the new work, or with status 400 (Bad Request) if the work has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/works")
    @Timed
    public ResponseEntity<Work> createWork(@Valid @RequestBody Work work) throws URISyntaxException {
        log.debug("REST request to save Work : {}", work);
        if (work.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new work cannot already have an ID")).body(null);
        }
        work.setWm(personRepository.findByPersonIsCurrentUser().get(0));
        work.setStartDate(LocalDate.now());
        Work result = workRepository.save(work);
        workSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getName().toString()))
            .body(result);
    }

    /**
     * PUT  /works : Updates an existing work.
     *
     * @param work the work to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated work,
     * or with status 400 (Bad Request) if the work is not valid,
     * or with status 500 (Internal Server Error) if the work couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/works")
    @Timed
    public ResponseEntity<Work> updateWork(@Valid @RequestBody Work work) throws URISyntaxException {
        log.debug("REST request to update Work : {}", work);
        if (work.getId() == null) {
            return createWork(work);
        }
        if(work.getStatus() == "ss"){
            Transaction transaction = new Transaction();
            transaction.setFromUser(work.getWs().getUser());
            transaction.setToUser(work.getBuyServ().getSm().getUser());
            transaction.setAmount(work.getTotalPrice());
            transaction.setName("事务交易");
            transaction.setWork(work);
            transactionResource.createTransaction(transaction);
        }

        Work result = workRepository.save(work);
        workSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, work.getName().toString()))
            .body(result);
    }

    @PostMapping("/works/transaction")
    @Timed
    public ResponseEntity<Work> finishWork(@Valid @RequestBody Work work) throws URISyntaxException {
        log.debug("REST request to finish Work : {}", work);
        if (work.getId() == null) {
            return createWork(work);
        }

        Double amount = work.getTotalPrice();
        Double sAmount = amount;
        int i = 0;
        List<Serv> servs = new ArrayList<>();
        Serv serv = work.getBuyServ();
        servs.add(serv);
        while (serv.getFather()!=null) {
            serv = serv.getFather();
            servs.add(serv);
            i++;
        }
        int j = i;
        while(i>=0){
            Double mAmount = 0.;
            if(i==0){
                mAmount = sAmount;
            } else {
                mAmount = amount * Math.exp(Math.log(0.618) * (i * 2 + 1));
                sAmount = sAmount - mAmount;
            }
            Transaction transaction = new Transaction();
            transaction.setFromPerson(work.getWs());
            if (i==0)
                transaction.setToPerson(servs.get(j).getSm());
            else
                transaction.setToPerson(servs.get(i-1).getSm());
            transaction.setAmount(mAmount);
            transaction.setName("事务交易");
            transaction.setWork(work);
            transactionResource.createTransaction(transaction);
            i--;
        }


        work.setEndDate(LocalDate.now());
        Work result = workRepository.save(work);
        workSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, work.getName().toString()))
            .body(result);
    }

    /**
     * GET  /works : get all the works.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of works in body
     */
    @GetMapping("/works")
    @Timed
    public List<Work> getAllWorks() {
        log.debug("REST request to get all Works");
        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN"))
            return workRepository.findAllWithEagerRelationships();
        else
            return workRepository.findAllWithEagerRelationshipsByCurrentUserIsProjectTeam();
    }

   /**
     * GET  /users/{login}/works : get all the works by user login.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of works in body
     */
    @GetMapping("/users/{login}/works")
    @Timed
    public List<Work> getAllWorksByPersonLogin(@PathVariable String login) {
        log.debug("REST request to get all Works");
        return workRepository.findAllWithEagerRelationshipsByPersonLogin(login);
    }

    /**
     * GET  /projects/{id}/ords : get all the works By project.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of works in body
     */
    @GetMapping("/projects/{id}/ords")
    @Timed
    public List<Work> getAllWorksByProject(@PathVariable Long id) {
        log.debug("REST request to get all Works By Project");
        return workRepository.findAllWithEagerRelationshipsByProject(id);
    }

    /**
     * GET  /works/:id : get the "id" work.
     *
     * @param id the id of the work to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the work, or with status 404 (Not Found)
     */
    @GetMapping("/works/{id}")
    @Timed
    public ResponseEntity<Work> getWork(@PathVariable Long id) {
        log.debug("REST request to get Work : {}", id);
        Work work = workRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(work));
    }

    /**
     * DELETE  /works/:id : delete the "id" work.
     *
     * @param id the id of the work to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/works/{id}")
    @Timed
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        log.debug("REST request to delete Work : {}", id);
        workRepository.delete(id);
        workSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/works?query=:query : search for the work corresponding
     * to the query.
     *
     * @param query the query of the work search
     * @return the result of the search
     */
    @GetMapping("/_search/works")
    @Timed
    public List<Work> searchWorks(@RequestParam String query) {
        log.debug("REST request to search Works for query {}", query);
        return StreamSupport
            .stream(workSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
