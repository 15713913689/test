package com.home.test6.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.home.test6.domain.Abc;
import com.home.test6.service.AbcService;
import com.home.test6.web.rest.errors.BadRequestAlertException;
import com.home.test6.web.rest.util.HeaderUtil;
import com.home.test6.web.rest.util.PaginationUtil;
import com.home.test6.service.dto.AbcCriteria;
import com.home.test6.service.AbcQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Abc.
 */
@RestController
@RequestMapping("/api")
public class AbcResource {

    private final Logger log = LoggerFactory.getLogger(AbcResource.class);

    private static final String ENTITY_NAME = "abc";

    private final AbcService abcService;

    private final AbcQueryService abcQueryService;

    public AbcResource(AbcService abcService, AbcQueryService abcQueryService) {
        this.abcService = abcService;
        this.abcQueryService = abcQueryService;
    }

    /**
     * POST  /abcs : Create a new abc.
     *
     * @param abc the abc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new abc, or with status 400 (Bad Request) if the abc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/abcs")
    @Timed
    public ResponseEntity<Abc> createAbc(@RequestBody Abc abc) throws URISyntaxException {
        log.debug("REST request to save Abc : {}", abc);
        if (abc.getId() != null) {
            throw new BadRequestAlertException("A new abc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Abc result = abcService.save(abc);
        return ResponseEntity.created(new URI("/api/abcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /abcs : Updates an existing abc.
     *
     * @param abc the abc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated abc,
     * or with status 400 (Bad Request) if the abc is not valid,
     * or with status 500 (Internal Server Error) if the abc couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/abcs")
    @Timed
    public ResponseEntity<Abc> updateAbc(@RequestBody Abc abc) throws URISyntaxException {
        log.debug("REST request to update Abc : {}", abc);
        if (abc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Abc result = abcService.save(abc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, abc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /abcs : get all the abcs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of abcs in body
     */
    @GetMapping("/abcs")
    @Timed
    public ResponseEntity<List<Abc>> getAllAbcs(AbcCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Abcs by criteria: {}", criteria);
        Page<Abc> page = abcQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/abcs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /abcs/:id : get the "id" abc.
     *
     * @param id the id of the abc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the abc, or with status 404 (Not Found)
     */
    @GetMapping("/abcs/{id}")
    @Timed
    public ResponseEntity<Abc> getAbc(@PathVariable Long id) {
        log.debug("REST request to get Abc : {}", id);
        Optional<Abc> abc = abcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(abc);
    }

    /**
     * DELETE  /abcs/:id : delete the "id" abc.
     *
     * @param id the id of the abc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/abcs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAbc(@PathVariable Long id) {
        log.debug("REST request to delete Abc : {}", id);
        abcService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
