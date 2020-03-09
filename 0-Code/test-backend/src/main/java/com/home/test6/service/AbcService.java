package com.home.test6.service;

import com.home.test6.domain.Abc;
import com.home.test6.repository.AbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Abc.
 */
@Service
@Transactional
public class AbcService {

    private final Logger log = LoggerFactory.getLogger(AbcService.class);

    private final AbcRepository abcRepository;

    public AbcService(AbcRepository abcRepository) {
        this.abcRepository = abcRepository;
    }

    /**
     * Save a abc.
     *
     * @param abc the entity to save
     * @return the persisted entity
     */
    public Abc save(Abc abc) {
        log.debug("Request to save Abc : {}", abc);        return abcRepository.save(abc);
    }

    /**
     * Get all the abcs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Abc> findAll(Pageable pageable) {
        log.debug("Request to get all Abcs");
        return abcRepository.findAll(pageable);
    }


    /**
     * Get one abc by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Abc> findOne(Long id) {
        log.debug("Request to get Abc : {}", id);
        return abcRepository.findById(id);
    }

    /**
     * Delete the abc by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Abc : {}", id);
        abcRepository.deleteById(id);
    }
}
