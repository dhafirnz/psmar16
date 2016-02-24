package nz.co.threepoints.ps.web.rest;

import nz.co.threepoints.ps.Application;
import nz.co.threepoints.ps.domain.CompanyAddress;
import nz.co.threepoints.ps.repository.CompanyAddressRepository;
import nz.co.threepoints.ps.repository.search.CompanyAddressSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nz.co.threepoints.ps.domain.enumeration.AddressType;

/**
 * Test class for the CompanyAddressResource REST controller.
 *
 * @see CompanyAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CompanyAddressResourceIntTest {

    private static final String DEFAULT_ADDRESS_LINE1 = "AAAAA";
    private static final String UPDATED_ADDRESS_LINE1 = "BBBBB";
    private static final String DEFAULT_ADDRESS_LINE2 = "AAAAA";
    private static final String UPDATED_ADDRESS_LINE2 = "BBBBB";
    private static final String DEFAULT_SUBURB = "AAAAA";
    private static final String UPDATED_SUBURB = "BBBBB";
    private static final String DEFAULT_TOWN = "AAAAA";
    private static final String UPDATED_TOWN = "BBBBB";
    private static final String DEFAULT_CITY = "AAAAA";
    private static final String UPDATED_CITY = "BBBBB";
    private static final String DEFAULT_POSTCODE = "AAAAA";
    private static final String UPDATED_POSTCODE = "BBBBB";
    private static final String DEFAULT_STATE = "AAAAA";
    private static final String UPDATED_STATE = "BBBBB";
    private static final String DEFAULT_COUNTRY = "AAAAA";
    private static final String UPDATED_COUNTRY = "BBBBB";
    
    private static final AddressType DEFAULT_TYPE = AddressType.PHYSICAL_ADDRESS;
    private static final AddressType UPDATED_TYPE = AddressType.POSTAL_ADDRESS;

    @Inject
    private CompanyAddressRepository companyAddressRepository;

    @Inject
    private CompanyAddressSearchRepository companyAddressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCompanyAddressMockMvc;

    private CompanyAddress companyAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyAddressResource companyAddressResource = new CompanyAddressResource();
        ReflectionTestUtils.setField(companyAddressResource, "companyAddressSearchRepository", companyAddressSearchRepository);
        ReflectionTestUtils.setField(companyAddressResource, "companyAddressRepository", companyAddressRepository);
        this.restCompanyAddressMockMvc = MockMvcBuilders.standaloneSetup(companyAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        companyAddress = new CompanyAddress();
        companyAddress.setAddressLine1(DEFAULT_ADDRESS_LINE1);
        companyAddress.setAddressLine2(DEFAULT_ADDRESS_LINE2);
        companyAddress.setSuburb(DEFAULT_SUBURB);
        companyAddress.setTown(DEFAULT_TOWN);
        companyAddress.setCity(DEFAULT_CITY);
        companyAddress.setPostcode(DEFAULT_POSTCODE);
        companyAddress.setState(DEFAULT_STATE);
        companyAddress.setCountry(DEFAULT_COUNTRY);
        companyAddress.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCompanyAddress() throws Exception {
        int databaseSizeBeforeCreate = companyAddressRepository.findAll().size();

        // Create the CompanyAddress

        restCompanyAddressMockMvc.perform(post("/api/companyAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyAddress)))
                .andExpect(status().isCreated());

        // Validate the CompanyAddress in the database
        List<CompanyAddress> companyAddresss = companyAddressRepository.findAll();
        assertThat(companyAddresss).hasSize(databaseSizeBeforeCreate + 1);
        CompanyAddress testCompanyAddress = companyAddresss.get(companyAddresss.size() - 1);
        assertThat(testCompanyAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE1);
        assertThat(testCompanyAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE2);
        assertThat(testCompanyAddress.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testCompanyAddress.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testCompanyAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCompanyAddress.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testCompanyAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCompanyAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCompanyAddress.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCompanyAddresss() throws Exception {
        // Initialize the database
        companyAddressRepository.saveAndFlush(companyAddress);

        // Get all the companyAddresss
        restCompanyAddressMockMvc.perform(get("/api/companyAddresss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(companyAddress.getId().intValue())))
                .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE1.toString())))
                .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE2.toString())))
                .andExpect(jsonPath("$.[*].suburb").value(hasItem(DEFAULT_SUBURB.toString())))
                .andExpect(jsonPath("$.[*].town").value(hasItem(DEFAULT_TOWN.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCompanyAddress() throws Exception {
        // Initialize the database
        companyAddressRepository.saveAndFlush(companyAddress);

        // Get the companyAddress
        restCompanyAddressMockMvc.perform(get("/api/companyAddresss/{id}", companyAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(companyAddress.getId().intValue()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE2.toString()))
            .andExpect(jsonPath("$.suburb").value(DEFAULT_SUBURB.toString()))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyAddress() throws Exception {
        // Get the companyAddress
        restCompanyAddressMockMvc.perform(get("/api/companyAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyAddress() throws Exception {
        // Initialize the database
        companyAddressRepository.saveAndFlush(companyAddress);

		int databaseSizeBeforeUpdate = companyAddressRepository.findAll().size();

        // Update the companyAddress
        companyAddress.setAddressLine1(UPDATED_ADDRESS_LINE1);
        companyAddress.setAddressLine2(UPDATED_ADDRESS_LINE2);
        companyAddress.setSuburb(UPDATED_SUBURB);
        companyAddress.setTown(UPDATED_TOWN);
        companyAddress.setCity(UPDATED_CITY);
        companyAddress.setPostcode(UPDATED_POSTCODE);
        companyAddress.setState(UPDATED_STATE);
        companyAddress.setCountry(UPDATED_COUNTRY);
        companyAddress.setType(UPDATED_TYPE);

        restCompanyAddressMockMvc.perform(put("/api/companyAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyAddress)))
                .andExpect(status().isOk());

        // Validate the CompanyAddress in the database
        List<CompanyAddress> companyAddresss = companyAddressRepository.findAll();
        assertThat(companyAddresss).hasSize(databaseSizeBeforeUpdate);
        CompanyAddress testCompanyAddress = companyAddresss.get(companyAddresss.size() - 1);
        assertThat(testCompanyAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE1);
        assertThat(testCompanyAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE2);
        assertThat(testCompanyAddress.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testCompanyAddress.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testCompanyAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCompanyAddress.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testCompanyAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCompanyAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCompanyAddress.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteCompanyAddress() throws Exception {
        // Initialize the database
        companyAddressRepository.saveAndFlush(companyAddress);

		int databaseSizeBeforeDelete = companyAddressRepository.findAll().size();

        // Get the companyAddress
        restCompanyAddressMockMvc.perform(delete("/api/companyAddresss/{id}", companyAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyAddress> companyAddresss = companyAddressRepository.findAll();
        assertThat(companyAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
