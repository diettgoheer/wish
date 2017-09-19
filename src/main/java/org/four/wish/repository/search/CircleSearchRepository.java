package org.four.wish.repository.search;

import org.four.wish.domain.Circle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Circle entity.
 */
public interface CircleSearchRepository extends ElasticsearchRepository<Circle, Long> {
}
