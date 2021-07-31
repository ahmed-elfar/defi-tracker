package com.xyvo.defi.web;

import com.xyvo.defi.IntegrationTestConfig;
import com.xyvo.defi.domain.profile.User;
import com.xyvo.defi.repository.UserRepo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTestConfig
class ParallelUserRequestsTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    private static Stream<Long> getUsersIds() {
        return LongStream.range(7, 7 + 30).mapToObj(l-> l);
    }
    private static Stream<String> getUsersName() {
        return getUsersIds().map(l -> "User" + l);
    }

    @Execution(value = ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "[{index}]-{arguments}")
    @MethodSource("getUsersName")
    void createUsersConcurrently(String userName) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON).param("userName", userName))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User user = userRepo.findByName(userName);
        assertThat(user.getId(), Matchers.is(user.getSettings().getId()));
    }

    @Execution(value = ExecutionMode.CONCURRENT)
    @ParameterizedTest(name = "[{index}]-id:{arguments}")
    @MethodSource("getUsersIds")
    void deleteUsersConcurrently(long id) throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/user")
                        .contentType(MediaType.APPLICATION_JSON).param("id", Long.toString(id)))
                .andExpect(status().isNoContent());
        assertThat(userRepo.findById(id).isEmpty(), Matchers.is(true));
    }

}
