package com.xyvo.defi.web;

import com.jayway.jsonpath.JsonPath;
import com.xyvo.defi.IntegrationTestConfig;
import com.xyvo.defi.repository.api.UserRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTestConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserCtlTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    private final static String user3 = "user3";
    private final static String user4 = "user4";
    private final static String user5 = "user5";
    private final static String user6 = "user6";
    //later we use our dto
    private final static String requestBody = "{\"userId\" : %d, \"userName\": \"%s\"}";

    @ParameterizedTest(name = "{argumentsWithNames}")
    @ValueSource(strings = {user3, user4})
    @Order(1)
    void testAdduser(String userName) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON).param("userName", userName))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @Order(2)
    void testGetUser() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders.get("/user")
                        .contentType(MediaType.APPLICATION_JSON).param("id", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName", is(user3)))
                .andReturn().getResponse().getContentAsString();
        assertThat(getContentOf(response, "$.created"), equalTo(getContentOf(response, "$.updated")));
        response = mvc.perform(MockMvcRequestBuilders.get("/user")
                        .contentType(MediaType.APPLICATION_JSON).param("id", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName", is(user4)))
                .andReturn().getResponse().getContentAsString();
        assertThat(getContentOf(response, "$.created"), equalTo(getContentOf(response, "$.updated")));
    }

    @Test
    @Order(3)
    void testUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[2].userName", is(user3)))
                .andExpect(jsonPath("$[3].userName", is(user4)))
                .andExpect(jsonPath("$[5].userName").doesNotExist());
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class UpdateUser {

        @ParameterizedTest(name = "{argumentsWithNames}")
        @CsvSource(value = {"user3_updated,3", "user4_updated,4"})
        @Order(1)
        void updateUser(String userName, long id) throws Exception {
            mvc.perform(MockMvcRequestBuilders.put("/user")
                            .contentType(MediaType.APPLICATION_JSON).content(String.format(requestBody, id, userName)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.userName", is(userName)));
        }

        @ParameterizedTest(name = "{argumentsWithNames}")
        @CsvSource(value = {"user3_updated,3", "user4_updated,4"})
        @Order(2)
        @Transactional
        void
        updateUserNotChanged(String userName, long id) throws Exception {
            Timestamp expected = userRepo.getById(id).removeNanos().getUpdated();
            String response = mvc.perform(MockMvcRequestBuilders.put("/user")
                            .contentType(MediaType.APPLICATION_JSON).content(String.format(requestBody, id, userName)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse().getContentAsString();
            Timestamp actual = Timestamp.valueOf(getContentOf(response, "$.updated"));
            assertThat(actual, equalTo(expected));
        }

        @Execution(value = ExecutionMode.CONCURRENT)
        @ParameterizedTest()
        @ValueSource(longs = {3, 4})
        @Order(3)
        void deleteUser(long id) throws Exception {
            mvc.perform(MockMvcRequestBuilders.delete("/user")
                            .contentType(MediaType.APPLICATION_JSON).param("id", Long.toString(id)))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class PostUser {

        @ParameterizedTest(name = "{argumentsWithNames}")
        @ValueSource(strings = {user5, user6})
        @Order(1)
        void addUser(String userName) throws Exception {
            mvc.perform(MockMvcRequestBuilders.post("/user")
                            .contentType(MediaType.APPLICATION_JSON).param("userName", userName))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        }

        @ParameterizedTest(name = "{argumentsWithNames}")
        @ValueSource(strings = {user5, user6})
        @Order(2)
        void addUserRejected(String userName) throws Exception {
            mvc.perform(MockMvcRequestBuilders.post("/user")
                            .contentType(MediaType.APPLICATION_JSON).param("userName", userName))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Execution(value = ExecutionMode.CONCURRENT)
        @ParameterizedTest()
        @ValueSource(longs = {5, 6})
        @Order(3)
        void deleteUser(long id) throws Exception {
            mvc.perform(MockMvcRequestBuilders.delete("/user")
                            .contentType(MediaType.APPLICATION_JSON).param("id", Long.toString(id)))
                    .andExpect(status().isNoContent());
        }
    }

    private String getContentOf(String response, String key) {
        return JsonPath.parse(response).read(key).toString();
    }

}
