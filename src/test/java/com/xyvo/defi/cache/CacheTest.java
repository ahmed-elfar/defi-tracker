package com.xyvo.defi.cache;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(
        classes = {CacheTest.MockCacheUser.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class CacheTest {

    @TestConfiguration
    static class MockCacheUser {

        // A mock instead of the real proxy
        @Bean
        public CacheUserInterface mockCacheUserClass() {
            return Mockito.mock(CacheUserInterface.class);
        }
    }

    // Your cache user interface
    interface CacheUserInterface {
        @Cacheable(cacheNames = CACHE_NAME, key = "#id", unless="#result == null")
        Object findById(Long id);
    }

    private static final String CACHE_NAME = "TestOnlyCache";
    final CacheUserInterface cacheUserClass;
    final CacheManager cacheManager;

    @Autowired
    public CacheTest(CacheUserInterface cacheUserClass, CacheManager cacheManager) {
        this.cacheUserClass = cacheUserClass;
        this.cacheManager = cacheManager;
    }

    @Test
    void methodInvocationShouldBeCached() {
        Object first  = new Object();
        Object second = new Object();

        // Set up the mock to return *different* objects for the first and second call
        Mockito.doReturn(first, second).when(cacheUserClass).findById(Mockito.any(Long.class));

        // First invocation returns object returned by the method
        Object result = cacheUserClass.findById(1L);
        assertThat(result, is(first));

        // Second invocation should return cached value, *not* second (as set up above)
        result = cacheUserClass.findById(1L);
        assertThat(result, is(first));

        //Verify findById method was invoked once
        Mockito.verify(cacheUserClass, Mockito.times(1)).findById(Mockito.any(Long.class));
        assertThat(cacheManager.getCache(CACHE_NAME).get(1L), is(notNullValue()));

        // Third invocation with different key is triggers the second invocation of the cache test method
        result = cacheUserClass.findById(2L);
        assertThat(result, is(second));
        assertThat(cacheManager.getCache(CACHE_NAME).get(2L), is(notNullValue()));

        // Verify findById method was invoked once for id #1
        Mockito.verify(cacheUserClass, Mockito.atMostOnce()).findById(1L);
    }
}
