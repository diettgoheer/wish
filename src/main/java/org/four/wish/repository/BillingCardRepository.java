package org.four.wish.repository;

import org.four.wish.domain.BillingCard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.Optional;

/**
 * Spring Data JPA repository for the BillingCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingCardRepository extends JpaRepository<BillingCard,Long> {
    Optional<BillingCard> findOneByUser(String login);

    @Query("select billingCard from BillingCard billingCard where billingCard.user = ?#{principal.username}")
    BillingCard findOneByCurrentUser();
}
