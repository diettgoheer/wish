package org.four.wish.repository.search;

import org.four.wish.domain.Serv;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Serv entity.
 */
public interface ServSearchRepository extends ElasticsearchRepository<Serv, Long> {
}
