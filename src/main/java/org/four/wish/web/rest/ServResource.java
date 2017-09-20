package org.four.wish.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.four.wish.domain.Serv;

import org.four.wish.repository.PersonRepository;
import org.four.wish.repository.ServRepository;
import org.four.wish.repository.search.ServSearchRepository;
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
 * REST controller for managing Serv.
 */
@RestController
@RequestMapping("/api")
public class ServResource {

    private final Logger log = LoggerFactory.getLogger(ServResource.class);

    private static final String ENTITY_NAME = "serv";

    private final ServRepository servRepository;

    private final ServSearchRepository servSearchRepository;

    private final PersonRepository personRepository;

    public ServResource(ServRepository servRepository, ServSearchRepository servSearchRepository, PersonRepository personRepository) {
        this.servRepository = servRepository;
        this.servSearchRepository = servSearchRepository;
        this.personRepository = personRepository;
    }

    /**
     * POST  /servs : Create a new serv.
     *
     * @param serv the serv to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serv, or with status 400 (Bad Request) if the serv has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servs")
    @Timed
    public ResponseEntity<Serv> createServ(@Valid @RequestBody Serv serv) throws URISyntaxException {
        log.debug("REST request to save Serv : {}", serv);
        if (serv.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serv cannot already have an ID")).body(null);
        }
        serv.setSm(personRepository.findByPersonIsCurrentUser().get(0));
        Serv result = servRepository.save(serv);
        servSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/servs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servs : Updates an existing serv.
     *
     * @param serv the serv to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serv,
     * or with status 400 (Bad Request) if the serv is not valid,
     * or with status 500 (Internal Server Error) if the serv couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servs")
    @Timed
    public ResponseEntity<Serv> updateServ(@Valid @RequestBody Serv serv) throws URISyntaxException {
        log.debug("REST request to update Serv : {}", serv);
        if (serv.getId() == null) {
            return createServ(serv);
        }
        Serv result = servRepository.save(serv);
        servSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serv.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servs : get all the servs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servs in body
     */
    @GetMapping("/servs")
    @Timed
    public List<Serv> getAllServs() {
        log.debug("REST request to get all Servs");
        return servRepository.findAll();
    }

    /**
     * GET  /users/{login}/servs : get all the servs by user login.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of servs in body
     */
    @GetMapping("/users/{login}/servs")
    @Timed
    public List<Serv> getAllServsByPersonLogin(@PathVariable String login) {
        log.debug("REST request to get all Works");
        return servRepository.findAllByPersonLogin(login);
    }

    /**
     * GET  /servs/:id : get the "id" serv.
     *
     * @param id the id of the serv to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serv, or with status 404 (Not Found)
     */
    @GetMapping("/servs/{id}")
    @Timed
    public ResponseEntity<Serv> getServ(@PathVariable Long id) {
        log.debug("REST request to get Serv : {}", id);
        Serv serv = servRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serv));
    }

    /**
     * DELETE  /servs/:id : delete the "id" serv.
     *
     * @param id the id of the serv to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servs/{id}")
    @Timed
    public ResponseEntity<Void> deleteServ(@PathVariable Long id) {
        log.debug("REST request to delete Serv : {}", id);
        servRepository.delete(id);
        servSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/servs?query=:query : search for the serv corresponding
     * to the query.
     *
     * @param query the query of the serv search
     * @return the result of the search
     */
    @GetMapping("/_search/servs")
    @Timed
    public List<Serv> searchServs(@RequestParam String query) {
        log.debug("REST request to search Servs for query {}", query);
        return StreamSupport
            .stream(servSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
