package org.four.wish.repository;

import org.four.wish.domain.Serv;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Serv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServRepository extends JpaRepository<Serv,Long> {
    
}
