package nz.co.threepoints.ps.repository.search;

import nz.co.threepoints.ps.domain.CompanyAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CompanyAddress entity.
 */
public interface CompanyAddressSearchRepository extends ElasticsearchRepository<CompanyAddress, Long> {
}
