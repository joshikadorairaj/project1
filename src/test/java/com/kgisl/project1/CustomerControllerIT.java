package com.kgisl.project1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kgisl.project1.entity.Customer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Project1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIT {

    @LocalServerPort
    private int port;

    HttpHeaders headers = new HttpHeaders();

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void createCustomerTest() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");

        HttpEntity<Customer> entity = new HttpEntity<>(customer);

        ResponseEntity<Customer> response = restTemplate.exchange(
                createURLWithPort("/customers"),
                HttpMethod.POST, entity, Customer.class);

        Customer createdCustomer = response.getBody();
        System.out.println("createCustomerTest Actual response: " + createdCustomer);
        assertTrue(createdCustomer != null && createdCustomer.getId() > 0);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void getAllCustomersTest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/customers/"),
                HttpMethod.GET, entity, String.class);
        System.out.println("getAllCustomersTest Actual Response: " + response.getBody());
    }

    @Test
    public void getCustomerByIdTest() {
        int customerIdToRetrieve = 1;
        ResponseEntity<Customer> response = restTemplate.getForEntity(
                createURLWithPort("/customers/" + customerIdToRetrieve),
                Customer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Customer retrievedCustomer = response.getBody();
        System.out.println("getCustomerByIdTest Actual Response:" + retrievedCustomer);
        assertEquals(customerIdToRetrieve, retrievedCustomer.getId());
    }

    @Test
    public void updateCustomerTest() {
        // Assume you have an existing customer with ID 1 in your database
        int customerIdToUpdate = 1;

        // Create an updated customer object
        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("UpdatedFirstName");
        updatedCustomer.setLastName("UpdatedLastName");
        updatedCustomer.setEmail("updated.email@example.com");

        HttpEntity<Customer> requestEntity = new HttpEntity<>(updatedCustomer, null);

        // Make an HTTP PUT request to update the customer
        ResponseEntity<Customer> response = restTemplate.exchange(
                createURLWithPort("/customers/" + customerIdToUpdate),
                HttpMethod.PUT,
                requestEntity,
                Customer.class);

        // Add assertions based on the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Customer updatedCustomerResponse = response.getBody();
        System.out.println("updateCustomerTest Actual Response:" + updatedCustomerResponse);
        assertEquals(customerIdToUpdate, updatedCustomerResponse.getId());
        assertEquals("UpdatedFirstName", updatedCustomerResponse.getFirstName());
        assertEquals("UpdatedLastName", updatedCustomerResponse.getLastName());
        assertEquals("updated.email@example.com", updatedCustomerResponse.getEmail());
        // Add more assertions based on your application logic
    }

    @Test
    public void deleteCustomerTest() {
        // Assume you have an existing customer with ID 1 in your database
        int customerIdToDelete = 3;

        // Make an HTTP DELETE request to delete the customer
        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/customers/" + customerIdToDelete),
                HttpMethod.DELETE,
                null,
                Void.class);

        System.out.println(response.getBody());
        // Add assertions based on the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Optionally, you can check if the customer is deleted by trying to retrieve it
        ResponseEntity<Customer> deletedCustomerResponse = restTemplate.getForEntity(
                createURLWithPort("/customers/" + customerIdToDelete),
                Customer.class);

        assertEquals(HttpStatus.NOT_FOUND, deletedCustomerResponse.getStatusCode());
        assertNull(deletedCustomerResponse.getBody());
    }

    // Add a test method for deleteCustomer based on your application logic

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
