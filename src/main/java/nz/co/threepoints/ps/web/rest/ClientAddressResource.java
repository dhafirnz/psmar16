package nz.co.threepoints.ps.web.rest;

import com.codahale.metrics.annotation.Timed;
import nz.co.threepoints.ps.domain.ClientAddress;
import nz.co.threepoints.ps.repository.ClientAddressRepository;
import nz.co.threepoints.ps.repository.search.ClientAddressSearchRepository;
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
 * REST controller for managing ClientAddress.
 */
@RestController
@RequestMapping("/api")
public class ClientAddressResource {

    private final Logger log = LoggerFactory.getLogger(ClientAddressResource.class);
        
    @Inject
    private ClientAddressRepository clientAddressRepository;
    
    @Inject
    private ClientAddressSearchRepository clientAddressSearchRepository;
    
    /**
     * POST  /clientAddresss -> Create a new clientAddress.
     */
    @RequestMapping(value = "/clientAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientAddress> createClientAddress(@Valid @RequestBody ClientAddress clientAddress) throws URISyntaxException {
        log.debug("REST request to save ClientAddress : {}", clientAddress);
        if (clientAddress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("clientAddress", "idexists", "A new clientAddress cannot already have an ID")).body(null);
        }
        ClientAddress result = clientAddressRepository.save(clientAddress);
        clientAddressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/clientAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("clientAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clientAddresss -> Updates an existing clientAddress.
     */
    @RequestMapping(value = "/clientAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientAddress> updateClientAddress(@Valid @RequestBody ClientAddress clientAddress) throws URISyntaxException {
        log.debug("REST request to update ClientAddress : {}", clientAddress);
        if (clientAddress.getId() == null) {
            return createClientAddress(clientAddress);
        }
        ClientAddress result = clientAddressRepository.save(clientAddress);
        clientAddressSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("clientAddress", clientAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clientAddresss -> get all the clientAddresss.
     */
    @RequestMapping(value = "/clientAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClientAddress> getAllClientAddresss() {
        log.debug("REST request to get all ClientAddresss");
        return clientAddressRepository.findAll();
            }

    /**
     * GET  /clientAddresss/:id -> get the "id" clientAddress.
     */
    @RequestMapping(value = "/clientAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientAddress> getClientAddress(@PathVariable Long id) {
        log.debug("REST request to get ClientAddress : {}", id);
        ClientAddress clientAddress = clientAddressRepository.findOne(id);
        return Optional.ofNullable(clientAddress)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clientAddresss/:id -> delete the "id" clientAddress.
     */
    @RequestMapping(value = "/clientAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClientAddress(@PathVariable Long id) {
        log.debug("REST request to delete ClientAddress : {}", id);
        clientAddressRepository.delete(id);
        clientAddressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clientAddress", id.toString())).build();
    }

    /**
     * SEARCH  /_search/clientAddresss/:query -> search for the clientAddress corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/clientAddresss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClientAddress> searchClientAddresss(@PathVariable String query) {
        log.debug("REST request to search ClientAddresss for query {}", query);
        return StreamSupport
            .stream(clientAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
