package nz.co.threepoints.ps.web.rest;

import com.codahale.metrics.annotation.Timed;
import nz.co.threepoints.ps.domain.Module;
import nz.co.threepoints.ps.repository.ModuleRepository;
import nz.co.threepoints.ps.repository.search.ModuleSearchRepository;
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
 * REST controller for managing Module.
 */
@RestController
@RequestMapping("/api")
public class ModuleResource {

    private final Logger log = LoggerFactory.getLogger(ModuleResource.class);
        
    @Inject
    private ModuleRepository moduleRepository;
    
    @Inject
    private ModuleSearchRepository moduleSearchRepository;
    
    /**
     * POST  /modules -> Create a new module.
     */
    @RequestMapping(value = "/modules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Module> createModule(@Valid @RequestBody Module module) throws URISyntaxException {
        log.debug("REST request to save Module : {}", module);
        if (module.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("module", "idexists", "A new module cannot already have an ID")).body(null);
        }
        Module result = moduleRepository.save(module);
        moduleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("module", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modules -> Updates an existing module.
     */
    @RequestMapping(value = "/modules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Module> updateModule(@Valid @RequestBody Module module) throws URISyntaxException {
        log.debug("REST request to update Module : {}", module);
        if (module.getId() == null) {
            return createModule(module);
        }
        Module result = moduleRepository.save(module);
        moduleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("module", module.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modules -> get all the modules.
     */
    @RequestMapping(value = "/modules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Module> getAllModules() {
        log.debug("REST request to get all Modules");
        return moduleRepository.findAll();
            }

    /**
     * GET  /modules/:id -> get the "id" module.
     */
    @RequestMapping(value = "/modules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Module> getModule(@PathVariable Long id) {
        log.debug("REST request to get Module : {}", id);
        Module module = moduleRepository.findOne(id);
        return Optional.ofNullable(module)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /modules/:id -> delete the "id" module.
     */
    @RequestMapping(value = "/modules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        log.debug("REST request to delete Module : {}", id);
        moduleRepository.delete(id);
        moduleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("module", id.toString())).build();
    }

    /**
     * SEARCH  /_search/modules/:query -> search for the module corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/modules/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Module> searchModules(@PathVariable String query) {
        log.debug("REST request to search Modules for query {}", query);
        return StreamSupport
            .stream(moduleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
