package nz.co.threepoints.ps.repository.search;

import nz.co.threepoints.ps.domain.Module;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Module entity.
 */
public interface ModuleSearchRepository extends ElasticsearchRepository<Module, Long> {
}
