package org.four.wish.repository;

import org.four.wish.domain.Work;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Work entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkRepository extends JpaRepository<Work,Long> {
    
    @Query("select distinct work from Work work left join fetch work.projects left join fetch work.servs")
    List<Work> findAllWithEagerRelationships();

    @Query("select work from Work work left join fetch work.projects left join fetch work.servs where work.id =:id")
    Work findOneWithEagerRelationships(@Param("id") Long id);
    
}
