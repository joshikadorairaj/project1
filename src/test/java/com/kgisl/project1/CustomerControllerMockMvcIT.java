package com.kgisl.project1;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgisl.project1.Controllers.CustomerController;
import com.kgisl.project1.entity.Customer;
import com.kgisl.project1.service.CustomerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class)
public class CustomerControllerMockMvcIT {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CustomerService customerService;

        public Customer customer1 = new CustomerBuilder().withId(1).withFirstName("gopi").withLastName("T").build();
        public Customer customer2 = new CustomerBuilder().withId(2).withFirstName("ALEX").withLastName("A").build();

        @Test
        public void getAll() throws Exception {
                List<Customer> alist1 = new ArrayList<>();
                alist1.add(customer1);
                alist1.add(customer2);

                when(customerService.getAllCustomers()).thenReturn(alist1);

                mockMvc.perform(get("/customers/").accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json(
                                                "[{'id': 1,'firstName': 'gopi' , 'lastName': 'T' } , {'id': 2,'firstName': 'ALEX' , 'lastName': 'A' }]"));
                System.out.println(alist1.toString());
                System.out.println(
                                "----------------------------------------------getall------------------------------------------------------------------------");

        }

        @Test
        public void GetCustomerById() throws Exception {

                when(customerService.getCustomerById(1)).thenReturn(customer1);

                // Call controller method and verify response
                mockMvc.perform(get("/customers/1"))
                                .andExpect(status().isOk())
                                .andExpect(
                                                content().json("{\"id\":1,\"firstName\":\"gopi\",\"lastName\":\"T\",\"email\":null}"))
                                .andReturn();
        }

        @Test
        public void postmapping() throws Exception {
                List<Customer> alist1 = new ArrayList<Customer>();
                alist1.add(customer1);
                alist1.add(customer2);

                when(customerService.getCustomerById(customer1.getId())).thenReturn(customer1);

                mockMvc.perform(post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(customer1)))
                                .andExpect(status().isCreated());
                System.out.println(
                                "-------------------------------------postmapping------------------------------------------------------------------------");
        }

        public static String asJsonString(final Object obj) {
                try {
                        final ObjectMapper mapper = new ObjectMapper();
                        return mapper.writeValueAsString(obj);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

        @Test
        public void deleteByID() throws Exception {
                // Arrange
                when(customerService.getCustomerById(customer1.getId())).thenReturn(customer1);

                // Act and Assert
                mockMvc.perform(delete("/customers/1", customer1.getId()))
                                .andExpect(status().isNoContent())
                                .andExpect(content().string(""));

                // Verify that deleteCustomer is called with the correct ID
                verify(customerService, times(1)).deleteCustomer(eq(customer1.getId()));

                System.out.println(
                                "-------------------------------DeletebyID------------------------------------------------------------------------");
        }

        @Test
        public void updateCustomer() throws Exception {
                // Mock data for the updated customer
                Customer updatedCustomer = new CustomerBuilder()
                                .withId(1)
                                .withFirstName("UpdatedFirstName")
                                .withLastName("UpdatedLastName")
                                .build();

                // Mocking the service to return the updated customer when found
                when(customerService.updateCustomer(1,
                                updatedCustomer)).thenReturn(updatedCustomer);

                // Perform the update request
                mockMvc.perform(put("/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(updatedCustomer)))
                                // .andExpect(status().isOk())
                                // .andExpect(content().json(asJsonString(updatedCustomer)))
                                .andReturn();
        }

        @Test
        public void testCreateCustomer() throws Exception {

                List<Customer> alist1 = new ArrayList<Customer>();
                alist1.add(customer1);

                when(customerService.createCustomer(customer1)).thenReturn(customer1);
                mockMvc.perform(
                                post("/customers").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"id\":1,\"firstName\":\"Customer 1\",\"lastName\":null,\"email\":null}"))
                                .andExpect(status().isCreated());
        }
}
