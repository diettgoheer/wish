package org.four.wish.repository;

import org.four.wish.domain.Project;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select distinct project from Project project left join fetch project.teams")
    List<Project> findAllWithEagerRelationships();

    @Query("select distinct project from Project project left join fetch project.teams person where person.user = ?#{principal.username}")
    List<Project> findAllWithEagerRelationshipsByCurrentUserInTeam();

    @Query("select distinct project from Project project left join fetch project.teams person where project.pm.user = ?#{principal.username}")
    List<Project> findAllWithEagerRelationshipsByCurrentUser();

    @Query("select distinct project from Project project left join fetch project.teams person where project.pm.user = :login")
    List<Project> findAllWithEagerRelationshipsByPersonLogin(@Param("login") String login);

    @Query("select project from Project project left join fetch project.teams where project.id =:id")
    Project findOneWithEagerRelationships(@Param("id") Long id);


}
