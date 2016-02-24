package nz.co.threepoints.ps.web.rest;

import com.codahale.metrics.annotation.Timed;
import nz.co.threepoints.ps.domain.CompanyAddress;
import nz.co.threepoints.ps.repository.CompanyAddressRepository;
import nz.co.threepoints.ps.repository.search.CompanyAddressSearchRepository;
import nz.co.threepoints.ps.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CompanyAddress.
 */
@RestController
@RequestMapping("/api")
public class CompanyAddressResource {

    private final Logger log = LoggerFactory.getLogger(CompanyAddressResource.class);
        
    @Inject
    private CompanyAddressRepository companyAddressRepository;
    
    @Inject
    private CompanyAddressSearchRepository companyAddressSearchRepository;
    
    /**
     * POST  /companyAddresss -> Create a new companyAddress.
     */
    @RequestMapping(value = "/companyAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyAddress> createCompanyAddress(@Valid @RequestBody CompanyAddress companyAddress) throws URISyntaxException {
        log.debug("REST request to save CompanyAddress : {}", companyAddress);
        if (companyAddress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("companyAddress", "idexists", "A new companyAddress cannot already have an ID")).body(null);
        }
        CompanyAddress result = companyAddressRepository.save(companyAddress);
        companyAddressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/companyAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("companyAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /companyAddresss -> Updates an existing companyAddress.
     */
    @RequestMapping(value = "/companyAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyAddress> updateCompanyAddress(@Valid @RequestBody CompanyAddress companyAddress) throws URISyntaxException {
        log.debug("REST request to update CompanyAddress : {}", companyAddress);
        if (companyAddress.getId() == null) {
            return createCompanyAddress(companyAddress);
        }
        CompanyAddress result = companyAddressRepository.save(companyAddress);
        companyAddressSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("companyAddress", companyAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /companyAddresss -> get all the companyAddresss.
     */
    @RequestMapping(value = "/companyAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CompanyAddress> getAllCompanyAddresss() {
        log.debug("REST request to get all CompanyAddresss");
        return companyAddressRepository.findAll();
            }

    /**
     * GET  /companyAddresss/:id -> get the "id" companyAddress.
     */
    @RequestMapping(value = "/companyAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyAddress> getCompanyAddress(@PathVariable Long id) {
        log.debug("REST request to get CompanyAddress : {}", id);
        CompanyAddress companyAddress = companyAddressRepository.findOne(id);
        return Optional.ofNullable(companyAddress)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /companyAddresss/:id -> delete the "id" companyAddress.
     */
    @RequestMapping(value = "/companyAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCompanyAddress(@PathVariable Long id) {
        log.debug("REST request to delete CompanyAddress : {}", id);
        companyAddressRepository.delete(id);
        companyAddressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("companyAddress", id.toString())).build();
    }

    /**
     * SEARCH  /_search/companyAddresss/:query -> search for the companyAddress corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/companyAddresss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CompanyAddress> searchCompanyAddresss(@PathVariable String query) {
        log.debug("REST request to search CompanyAddresss for query {}", query);
        return StreamSupport
            .stream(companyAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
