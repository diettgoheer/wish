package org.four.wish.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.four.wish.domain.ServiceProvider;

import org.four.wish.repository.PersonRepository;
import org.four.wish.repository.ServiceProviderRepository;
import org.four.wish.repository.search.ServiceProviderSearchRepository;
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
 * REST controller for managing ServiceProvider.
 */
@RestController
@RequestMapping("/api")
public class ServiceProviderResource {

    private final Logger log = LoggerFactory.getLogger(ServiceProviderResource.class);

    private static final String ENTITY_NAME = "serviceProvider";

    private final ServiceProviderRepository serviceProviderRepository;

    private final ServiceProviderSearchRepository serviceProviderSearchRepository;

/*    private final PersonRepository personRepository;*/

    public ServiceProviderResource( ServiceProviderRepository serviceProviderRepository, ServiceProviderSearchRepository serviceProviderSearchRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.serviceProviderSearchRepository = serviceProviderSearchRepository;
      /*  this.personRepository = personRepository;*/
    }

    /**
     * POST  /service-providers : Create a new serviceProvider.
     *
     * @param serviceProvider the serviceProvider to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceProvider, or with status 400 (Bad Request) if the serviceProvider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProvider> createServiceProvider(@Valid @RequestBody ServiceProvider serviceProvider) throws URISyntaxException {
        log.debug("REST request to save ServiceProvider : {}", serviceProvider);
        if (serviceProvider.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serviceProvider cannot already have an ID")).body(null);
        }
        ServiceProvider result = serviceProviderRepository.save(serviceProvider);
        serviceProviderSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/service-providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getName().toString()))
            .body(result);
    }

    /**
     * PUT  /service-providers : Updates an existing serviceProvider.
     *
     * @param serviceProvider the serviceProvider to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceProvider,
     * or with status 400 (Bad Request) if the serviceProvider is not valid,
     * or with status 500 (Internal Server Error) if the serviceProvider couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-providers")
    @Timed
    public ResponseEntity<ServiceProvider> updateServiceProvider(@Valid @RequestBody ServiceProvider serviceProvider) throws URISyntaxException {
        log.debug("REST request to update ServiceProvider : {}", serviceProvider);
        if (serviceProvider.getId() == null) {
            return createServiceProvider(serviceProvider);
        }
        ServiceProvider result = serviceProviderRepository.save(serviceProvider);
        serviceProviderSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceProvider.getName().toString()))
            .body(result);
    }

    /**
     * GET  /service-providers : get all the serviceProviders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of serviceProviders in body
     */
    @GetMapping("/service-providers")
    @Timed
    public List<ServiceProvider> getAllServiceProviders() {
        log.debug("REST request to get all ServiceProviders");
        return serviceProviderRepository.findAll();
    }

    /**
     * GET  /service-providers/:id : get the "id" serviceProvider.
     *
     * @param id the id of the serviceProvider to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceProvider, or with status 404 (Not Found)
     */
    @GetMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<ServiceProvider> getServiceProvider(@PathVariable Long id) {
        log.debug("REST request to get ServiceProvider : {}", id);
        ServiceProvider serviceProvider = serviceProviderRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviceProvider));
    }

    /**
     * DELETE  /service-providers/:id : delete the "id" serviceProvider.
     *
     * @param id the id of the serviceProvider to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-providers/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable Long id) {
        log.debug("REST request to delete ServiceProvider : {}", id);
        serviceProviderRepository.delete(id);
        serviceProviderSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/service-providers?query=:query : search for the serviceProvider corresponding
     * to the query.
     *
     * @param query the query of the serviceProvider search
     * @return the result of the search
     */
    @GetMapping("/_search/service-providers")
    @Timed
    public List<ServiceProvider> searchServiceProviders(@RequestParam String query) {
        log.debug("REST request to search ServiceProviders for query {}", query);
        return StreamSupport
            .stream(serviceProviderSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
