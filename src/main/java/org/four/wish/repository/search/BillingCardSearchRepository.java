package org.four.wish.repository.search;

import org.four.wish.domain.BillingCard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BillingCard entity.
 */
public interface BillingCardSearchRepository extends ElasticsearchRepository<BillingCard, Long> {
}
