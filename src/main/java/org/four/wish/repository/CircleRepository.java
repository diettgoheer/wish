package org.four.wish.repository;

import org.four.wish.domain.Circle;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Circle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CircleRepository extends JpaRepository<Circle,Long> {
    @Query("select circle from Circle circle where circle.userLogin = ?#{principal.username}")
    List<Circle> findByPersonIsCurrentUser();

    Optional<Circle> findAllByUserLoginAndFriendLogin(String userLogin,String friendLogin);
}
