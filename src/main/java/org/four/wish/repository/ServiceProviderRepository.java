package org.four.wish.repository;

import org.four.wish.domain.ServiceProvider;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ServiceProvider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider,Long> {
    @Query("select distinct sp from ServiceProvider sp left join Circle circle on sp.contactEmail = circle.friendLogin where circle.userLogin = ?#{principal.username}")
    List<ServiceProvider> findAllByFriendIsCurrentUser();
}
