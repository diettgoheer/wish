package org.four.wish.repository;

import org.four.wish.domain.Serv;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Serv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServRepository extends JpaRepository<Serv,Long> {
    @Query("select distinct serv from Serv serv where serv.sm.user = ?#{principal.username}")
    List<Serv> findAllByCurrentUser();

    @Query("select distinct serv from Serv serv where serv.sm.user = :login")
    List<Serv> findAllByPersonLogin(@Param("login") String login);

    @Query("select distinct serv from Serv serv left join Circle circle on serv.sm.user = circle.friendLogin where circle.userLogin = ?#{principal.username}")
    List<Serv> findAllByFriendIsCurrentUser();
}
