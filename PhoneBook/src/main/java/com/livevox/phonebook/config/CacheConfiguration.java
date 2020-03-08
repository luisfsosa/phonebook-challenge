/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.config;

import com.lfsa.luisfsosa.config.LfsaProperties;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


/**
 * The Class CacheConfiguration.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Configuration
@EnableCaching
public class CacheConfiguration {


    /** The jcache configuration. */
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    /**
     * Instantiates a new cache configuration.
     *
     * @param lfsaProperties the lfsa properties
     */
    public CacheConfiguration(LfsaProperties lfsaProperties) {
        LfsaProperties.Cache.Ehcache ehcache = lfsaProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    /**
     * Hibernate properties customizer.
     *
     * @param cacheManager the cache manager
     * @return the hibernate properties customizer
     */
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    /**
     * Cache manager customizer.
     *
     * @return the j cache manager customizer
     */
    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.livevox.phonebook.domain.Contact.class.getName());
        };
    }

    /**
     * Creates the cache.
     *
     * @param cm the cm
     * @param cacheName the cache name
     */
    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
