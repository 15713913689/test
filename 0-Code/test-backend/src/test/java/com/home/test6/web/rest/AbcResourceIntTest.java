package com.home.test6.web.rest;

import com.home.test6.JhipsterApp;

import com.home.test6.domain.Abc;
import com.home.test6.repository.AbcRepository;
import com.home.test6.service.AbcService;
import com.home.test6.web.rest.errors.ExceptionTranslator;
import com.home.test6.service.dto.AbcCriteria;
import com.home.test6.service.AbcQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.home.test6.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AbcResource REST controller.
 *
 * @see AbcResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class AbcResourceIntTest {

    private static final String DEFAULT_AAA = "AAAAAAAAAA";
    private static final String UPDATED_AAA = "BBBBBBBBBB";

    private static final String DEFAULT_BBB = "AAAAAAAAAA";
    private static final String UPDATED_BBB = "BBBBBBBBBB";

    @Autowired
    private AbcRepository abcRepository;

    

    @Autowired
    private AbcService abcService;

    @Autowired
    private AbcQueryService abcQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAbcMockMvc;

    private Abc abc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbcResource abcResource = new AbcResource(abcService, abcQueryService);
        this.restAbcMockMvc = MockMvcBuilders.standaloneSetup(abcResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Abc createEntity(EntityManager em) {
        Abc abc = new Abc()
            .aaa(DEFAULT_AAA)
            .bbb(DEFAULT_BBB);
        return abc;
    }

    @Before
    public void initTest() {
        abc = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbc() throws Exception {
        int databaseSizeBeforeCreate = abcRepository.findAll().size();

        // Create the Abc
        restAbcMockMvc.perform(post("/api/abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abc)))
            .andExpect(status().isCreated());

        // Validate the Abc in the database
        List<Abc> abcList = abcRepository.findAll();
        assertThat(abcList).hasSize(databaseSizeBeforeCreate + 1);
        Abc testAbc = abcList.get(abcList.size() - 1);
        assertThat(testAbc.getAaa()).isEqualTo(DEFAULT_AAA);
        assertThat(testAbc.getBbb()).isEqualTo(DEFAULT_BBB);
    }

    @Test
    @Transactional
    public void createAbcWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abcRepository.findAll().size();

        // Create the Abc with an existing ID
        abc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbcMockMvc.perform(post("/api/abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abc)))
            .andExpect(status().isBadRequest());

        // Validate the Abc in the database
        List<Abc> abcList = abcRepository.findAll();
        assertThat(abcList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAbcs() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get all the abcList
        restAbcMockMvc.perform(get("/api/abcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abc.getId().intValue())))
            .andExpect(jsonPath("$.[*].aaa").value(hasItem(DEFAULT_AAA.toString())))
            .andExpect(jsonPath("$.[*].bbb").value(hasItem(DEFAULT_BBB.toString())));
    }
    

    @Test
    @Transactional
    public void getAbc() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get the abc
        restAbcMockMvc.perform(get("/api/abcs/{id}", abc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(abc.getId().intValue()))
            .andExpect(jsonPath("$.aaa").value(DEFAULT_AAA.toString()))
            .andExpect(jsonPath("$.bbb").value(DEFAULT_BBB.toString()));
    }

    @Test
    @Transactional
    public void getAllAbcsByAaaIsEqualToSomething() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get all the abcList where aaa equals to DEFAULT_AAA
        defaultAbcShouldBeFound("aaa.equals=" + DEFAULT_AAA);

        // Get all the abcList where aaa equals to UPDATED_AAA
        defaultAbcShouldNotBeFound("aaa.equals=" + UPDATED_AAA);
    }

    @Test
    @Transactional
    public void getAllAbcsByAaaIsInShouldWork() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get all the abcList where aaa in DEFAULT_AAA or UPDATED_AAA
        defaultAbcShouldBeFound("aaa.in=" + DEFAULT_AAA + "," + UPDATED_AAA);

        // Get all the abcList where aaa equals to UPDATED_AAA
        defaultAbcShouldNotBeFound("aaa.in=" + UPDATED_AAA);
    }

    @Test
    @Transactional
    public void getAllAbcsByAaaIsNullOrNotNull() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get all the abcList where aaa is not null
        defaultAbcShouldBeFound("aaa.specified=true");

        // Get all the abcList where aaa is null
        defaultAbcShouldNotBeFound("aaa.specified=false");
    }

    @Test
    @Transactional
    public void getAllAbcsByBbbIsEqualToSomething() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get all the abcList where bbb equals to DEFAULT_BBB
        defaultAbcShouldBeFound("bbb.equals=" + DEFAULT_BBB);

        // Get all the abcList where bbb equals to UPDATED_BBB
        defaultAbcShouldNotBeFound("bbb.equals=" + UPDATED_BBB);
    }

    @Test
    @Transactional
    public void getAllAbcsByBbbIsInShouldWork() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get all the abcList where bbb in DEFAULT_BBB or UPDATED_BBB
        defaultAbcShouldBeFound("bbb.in=" + DEFAULT_BBB + "," + UPDATED_BBB);

        // Get all the abcList where bbb equals to UPDATED_BBB
        defaultAbcShouldNotBeFound("bbb.in=" + UPDATED_BBB);
    }

    @Test
    @Transactional
    public void getAllAbcsByBbbIsNullOrNotNull() throws Exception {
        // Initialize the database
        abcRepository.saveAndFlush(abc);

        // Get all the abcList where bbb is not null
        defaultAbcShouldBeFound("bbb.specified=true");

        // Get all the abcList where bbb is null
        defaultAbcShouldNotBeFound("bbb.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAbcShouldBeFound(String filter) throws Exception {
        restAbcMockMvc.perform(get("/api/abcs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abc.getId().intValue())))
            .andExpect(jsonPath("$.[*].aaa").value(hasItem(DEFAULT_AAA.toString())))
            .andExpect(jsonPath("$.[*].bbb").value(hasItem(DEFAULT_BBB.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAbcShouldNotBeFound(String filter) throws Exception {
        restAbcMockMvc.perform(get("/api/abcs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingAbc() throws Exception {
        // Get the abc
        restAbcMockMvc.perform(get("/api/abcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbc() throws Exception {
        // Initialize the database
        abcService.save(abc);

        int databaseSizeBeforeUpdate = abcRepository.findAll().size();

        // Update the abc
        Abc updatedAbc = abcRepository.findById(abc.getId()).get();
        // Disconnect from session so that the updates on updatedAbc are not directly saved in db
        em.detach(updatedAbc);
        updatedAbc
            .aaa(UPDATED_AAA)
            .bbb(UPDATED_BBB);

        restAbcMockMvc.perform(put("/api/abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbc)))
            .andExpect(status().isOk());

        // Validate the Abc in the database
        List<Abc> abcList = abcRepository.findAll();
        assertThat(abcList).hasSize(databaseSizeBeforeUpdate);
        Abc testAbc = abcList.get(abcList.size() - 1);
        assertThat(testAbc.getAaa()).isEqualTo(UPDATED_AAA);
        assertThat(testAbc.getBbb()).isEqualTo(UPDATED_BBB);
    }

    @Test
    @Transactional
    public void updateNonExistingAbc() throws Exception {
        int databaseSizeBeforeUpdate = abcRepository.findAll().size();

        // Create the Abc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAbcMockMvc.perform(put("/api/abcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abc)))
            .andExpect(status().isBadRequest());

        // Validate the Abc in the database
        List<Abc> abcList = abcRepository.findAll();
        assertThat(abcList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbc() throws Exception {
        // Initialize the database
        abcService.save(abc);

        int databaseSizeBeforeDelete = abcRepository.findAll().size();

        // Get the abc
        restAbcMockMvc.perform(delete("/api/abcs/{id}", abc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Abc> abcList = abcRepository.findAll();
        assertThat(abcList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Abc.class);
        Abc abc1 = new Abc();
        abc1.setId(1L);
        Abc abc2 = new Abc();
        abc2.setId(abc1.getId());
        assertThat(abc1).isEqualTo(abc2);
        abc2.setId(2L);
        assertThat(abc1).isNotEqualTo(abc2);
        abc1.setId(null);
        assertThat(abc1).isNotEqualTo(abc2);
    }
}
