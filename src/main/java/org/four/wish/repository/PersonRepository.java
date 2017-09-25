package org.four.wish.repository;

import org.four.wish.domain.Person;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    @Query("select distinct person from Person person where person.email = :email")
    List<Person> findByEmail(@Param("email") String email);

    @Query("select distinct person from Person person where person.user = ?#{principal.username}")
    List<Person> findByPersonIsCurrentUser();

    @Query("select distinct person from Person person left join Circle circle on circle.person.id = person.id where circle.userLogin = ?#{principal.username}")
    List<Person> findByFriendIsCurrentUser();

    @Query("select distinct person from Person person left join Circle circle on circle.friendLogin = person.user where circle.userLogin = ?#{principal.username}")
    List<Person> findAllFriendByCurrentUser();
}
