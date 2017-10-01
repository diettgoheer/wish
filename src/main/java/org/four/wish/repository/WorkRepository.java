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

    @Query("select distinct work from Work work left join fetch work.projects left join fetch work.servs where work.wm.user = ?#{principal.username} order by work.startDate desc")
    List<Work> findAllWithEagerRelationshipsByCurrentUser();

    @Query("select distinct work from Work work left join fetch work.projects left join fetch work.servs where work.wm.user = :login")
    List<Work> findAllWithEagerRelationshipsByPersonLogin(@Param("login") String login);

    @Query("select distinct work from Work work left join fetch work.projects project left join fetch project.teams person left join fetch work.servs where person.user = ?#{principal.username}")
    List<Work> findAllWithEagerRelationshipsByCurrentUserIsProjectTeam();

    @Query("select distinct work from Work work left join fetch work.projects project left join fetch work.servs where project.id = :id")
    List<Work> findAllWithEagerRelationshipsByProject(@Param("id") Long id);

    @Query("select work from Work work left join fetch work.projects left join fetch work.servs where work.id =:id")
    Work findOneWithEagerRelationships(@Param("id") Long id);

}
