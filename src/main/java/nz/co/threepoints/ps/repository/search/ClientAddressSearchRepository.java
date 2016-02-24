package nz.co.threepoints.ps.repository.search;

import nz.co.threepoints.ps.domain.ClientAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ClientAddress entity.
 */
public interface ClientAddressSearchRepository extends ElasticsearchRepository<ClientAddress, Long> {
}
