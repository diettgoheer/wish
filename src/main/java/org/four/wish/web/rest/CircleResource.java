package org.four.wish.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.four.wish.domain.Circle;

import org.four.wish.domain.Person;
import org.four.wish.domain.User;
import org.four.wish.repository.CircleRepository;
import org.four.wish.repository.PersonRepository;
import org.four.wish.repository.UserRepository;
import org.four.wish.repository.search.CircleSearchRepository;
import org.four.wish.security.SecurityUtils;

import org.four.wish.service.MailService;
import org.four.wish.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Circle.
 */
@RestController
@RequestMapping("/api")
public class CircleResource {

    private final Logger log = LoggerFactory.getLogger(CircleResource.class);

    private static final String ENTITY_NAME = "circle";

    private final CircleRepository circleRepository;

    private final CircleSearchRepository circleSearchRepository;

    private final PersonRepository personRepository;

    private final UserRepository userRepository;

    private final MailService mailService;

    public CircleResource(MailService mailService, UserRepository userRepository, CircleRepository circleRepository, CircleSearchRepository circleSearchRepository, PersonRepository personRepository) {
        this.circleRepository = circleRepository;
        this.circleSearchRepository = circleSearchRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    /**
     * POST  /circles : Create a new circle.
     *
     * @param circle the circle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new circle, or with status 400 (Bad Request) if the circle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/circles")
    @Timed
    public ResponseEntity<Circle> createCircle(@RequestBody Circle circle) throws URISyntaxException {
        log.debug("REST request to save Circle : {}", circle);
        if (circle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new circle cannot already have an ID")).body(null);
        }
        circle.setUserLogin(SecurityUtils.getCurrentUserLogin());
        Circle opCircle = new Circle();
        opCircle.setUserLogin(circle.getFriendLogin());
        opCircle.setFriendLogin(circle.getUserLogin());
        /*List<Person> persons = personRepository.findByEmail(circle.getFriendLogin());
        if(!persons.isEmpty())
            circle.setPerson(persons.get(0));*/
        /*circle.setUser(userService.getUserWithAuthoritiesByLogin(circle.getUserLogin()).get());
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(circle.getFriendLogin());
        if(user.isPresent())
            circle.setFriend(user.get());*/
        Circle result = circleRepository.save(circle);
        circleSearchRepository.save(result);
        circleRepository.save(opCircle);
        circleSearchRepository.save(opCircle);
        if(!userRepository.findOneByLogin(circle.getFriendLogin()).isPresent()) {
            User user = userRepository.findOneByLogin(circle.getUserLogin()).get();
            mailService.sendIntroductionEmail(circle,user);
        }
        return ResponseEntity.created(new URI("/api/circles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getFriendLogin().toString()))
            .body(result);
    }

    /**
     * PUT  /circles : Updates an existing circle.
     *
     * @param circle the circle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated circle,
     * or with status 400 (Bad Request) if the circle is not valid,
     * or with status 500 (Internal Server Error) if the circle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/circles")
    @Timed
    public ResponseEntity<Circle> updateCircle(@RequestBody Circle circle) throws URISyntaxException {
        log.debug("REST request to update Circle : {}", circle);
        if (circle.getId() == null) {
            return createCircle(circle);
        }

        Circle result = circleRepository.save(circle);
        circleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, circle.getFriendLogin().toString()))
            .body(result);
    }

    /**
     * GET  /circles : get all the circles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of circles in body
     */
    @GetMapping("/circles")
    @Timed
    public List<Circle> getAllCircles() {
        log.debug("REST request to get all Circles");
        if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN"))
            return circleRepository.findAll();
        else
            return circleRepository.findByPersonIsCurrentUser();
    }

    /**
     * GET  /circles/:id : get the "id" circle.
     *
     * @param id the id of the circle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the circle, or with status 404 (Not Found)
     */
    @GetMapping("/circles/{id}")
    @Timed
    public ResponseEntity<Circle> getCircle(@PathVariable Long id) {
        log.debug("REST request to get Circle : {}", id);
        Circle circle = circleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(circle));
    }

    /**
     * DELETE  /circles/:id : delete the "id" circle.
     *
     * @param id the id of the circle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/circles/{id}")
    @Timed
    public ResponseEntity<Void> deleteCircle(@PathVariable Long id) {
        log.debug("REST request to delete Circle : {}", id);
        circleRepository.delete(id);
        circleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/circles?query=:query : search for the circle corresponding
     * to the query.
     *
     * @param query the query of the circle search
     * @return the result of the search
     */
    @GetMapping("/_search/circles")
    @Timed
    public List<Circle> searchCircles(@RequestParam String query) {
        log.debug("REST request to search Circles for query {}", query);
        return StreamSupport
            .stream(circleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
