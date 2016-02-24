package nz.co.threepoints.ps.web.rest;

import nz.co.threepoints.ps.Application;
import nz.co.threepoints.ps.domain.ClientAddress;
import nz.co.threepoints.ps.repository.ClientAddressRepository;
import nz.co.threepoints.ps.repository.search.ClientAddressSearchRepository;

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
 * Test class for the ClientAddressResource REST controller.
 *
 * @see ClientAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClientAddressResourceIntTest {

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
    private ClientAddressRepository clientAddressRepository;

    @Inject
    private ClientAddressSearchRepository clientAddressSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClientAddressMockMvc;

    private ClientAddress clientAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientAddressResource clientAddressResource = new ClientAddressResource();
        ReflectionTestUtils.setField(clientAddressResource, "clientAddressSearchRepository", clientAddressSearchRepository);
        ReflectionTestUtils.setField(clientAddressResource, "clientAddressRepository", clientAddressRepository);
        this.restClientAddressMockMvc = MockMvcBuilders.standaloneSetup(clientAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        clientAddress = new ClientAddress();
        clientAddress.setAddressLine1(DEFAULT_ADDRESS_LINE1);
        clientAddress.setAddressLine2(DEFAULT_ADDRESS_LINE2);
        clientAddress.setSuburb(DEFAULT_SUBURB);
        clientAddress.setTown(DEFAULT_TOWN);
        clientAddress.setCity(DEFAULT_CITY);
        clientAddress.setPostcode(DEFAULT_POSTCODE);
        clientAddress.setState(DEFAULT_STATE);
        clientAddress.setCountry(DEFAULT_COUNTRY);
        clientAddress.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createClientAddress() throws Exception {
        int databaseSizeBeforeCreate = clientAddressRepository.findAll().size();

        // Create the ClientAddress

        restClientAddressMockMvc.perform(post("/api/clientAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientAddress)))
                .andExpect(status().isCreated());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddresss = clientAddressRepository.findAll();
        assertThat(clientAddresss).hasSize(databaseSizeBeforeCreate + 1);
        ClientAddress testClientAddress = clientAddresss.get(clientAddresss.size() - 1);
        assertThat(testClientAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE1);
        assertThat(testClientAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE2);
        assertThat(testClientAddress.getSuburb()).isEqualTo(DEFAULT_SUBURB);
        assertThat(testClientAddress.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testClientAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClientAddress.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testClientAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testClientAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testClientAddress.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllClientAddresss() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        // Get all the clientAddresss
        restClientAddressMockMvc.perform(get("/api/clientAddresss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clientAddress.getId().intValue())))
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
    public void getClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

        // Get the clientAddress
        restClientAddressMockMvc.perform(get("/api/clientAddresss/{id}", clientAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(clientAddress.getId().intValue()))
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
    public void getNonExistingClientAddress() throws Exception {
        // Get the clientAddress
        restClientAddressMockMvc.perform(get("/api/clientAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

		int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();

        // Update the clientAddress
        clientAddress.setAddressLine1(UPDATED_ADDRESS_LINE1);
        clientAddress.setAddressLine2(UPDATED_ADDRESS_LINE2);
        clientAddress.setSuburb(UPDATED_SUBURB);
        clientAddress.setTown(UPDATED_TOWN);
        clientAddress.setCity(UPDATED_CITY);
        clientAddress.setPostcode(UPDATED_POSTCODE);
        clientAddress.setState(UPDATED_STATE);
        clientAddress.setCountry(UPDATED_COUNTRY);
        clientAddress.setType(UPDATED_TYPE);

        restClientAddressMockMvc.perform(put("/api/clientAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientAddress)))
                .andExpect(status().isOk());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddresss = clientAddressRepository.findAll();
        assertThat(clientAddresss).hasSize(databaseSizeBeforeUpdate);
        ClientAddress testClientAddress = clientAddresss.get(clientAddresss.size() - 1);
        assertThat(testClientAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE1);
        assertThat(testClientAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE2);
        assertThat(testClientAddress.getSuburb()).isEqualTo(UPDATED_SUBURB);
        assertThat(testClientAddress.getTown()).isEqualTo(UPDATED_TOWN);
        assertThat(testClientAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClientAddress.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testClientAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testClientAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testClientAddress.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.saveAndFlush(clientAddress);

		int databaseSizeBeforeDelete = clientAddressRepository.findAll().size();

        // Get the clientAddress
        restClientAddressMockMvc.perform(delete("/api/clientAddresss/{id}", clientAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientAddress> clientAddresss = clientAddressRepository.findAll();
        assertThat(clientAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
