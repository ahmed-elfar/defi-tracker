package com.xyvo.defi;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


@Configuration()
@EnableCaching
public class CacheConfigCustom {

    public static class ServiceEventLogger implements CacheEventListener<Object, Object> {

        private static final Logger LOG = LoggerFactory.getLogger(ServiceEventLogger.class);

        @Override
        public void onEvent(CacheEvent cacheEvent) {
            if(LOG.isDebugEnabled()) {
                LOG.info("Cache-Event -> {}, Key = {}, Old-Value = {}, New-Value = {}", cacheEvent.getType(),
                        cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
            }
        }
    }

    public static class HibernateEventLogger implements CacheEventListener<Object, Object> {

        private static final Logger LOG = LoggerFactory.getLogger(HibernateEventLogger.class);

        @Override
        public void onEvent(CacheEvent cacheEvent) {
            if(LOG.isDebugEnabled()) {
                LOG.info("Cache-Event -> {}, Key = {}, Old-Value = {}, New-Value = {}", cacheEvent.getType(),
                        cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
            }
        }
    }

//    public static class EvictionPolicy implements EvictionAdvisor<Long, Object> {
//
//        private static final Logger LOG = LoggerFactory.getLogger(EvictionPolicy.class);
//        @Override
//        public boolean adviseAgainstEviction(Long o, Object o2) {
//            do some logic
//            return false;
//        }
//    }

}
