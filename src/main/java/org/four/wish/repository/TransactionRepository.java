package org.four.wish.repository;

import org.four.wish.domain.Transaction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("select distinct transaction from Transaction transaction where transaction.fromUser = ?#{principal.username} or transaction.toUser  = ?#{principal.username} order by transaction.time desc")
    List<Transaction> findAllByCurrentUser();
}
