package org.four.wish.repository.search;

import org.four.wish.domain.ServiceProvider;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServiceProvider entity.
 */
public interface ServiceProviderSearchRepository extends ElasticsearchRepository<ServiceProvider, Long> {
}
