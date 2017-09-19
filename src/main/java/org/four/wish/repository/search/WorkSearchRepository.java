package org.four.wish.repository.search;

import org.four.wish.domain.Work;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Work entity.
 */
public interface WorkSearchRepository extends ElasticsearchRepository<Work, Long> {
}
