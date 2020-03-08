/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.lfsa.luisfsosa.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.cors.CorsConfiguration;

/**
 * The Class LfsaProperties.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@ConfigurationProperties(
        prefix = "lfsa",
        ignoreUnknownFields = false
)
@PropertySources({@PropertySource(
        value = {"classpath:git.properties"},
        ignoreResourceNotFound = true
), @PropertySource(
        value = {"classpath:META-INF/build-info.properties"},
        ignoreResourceNotFound = true
)})
public class LfsaProperties {

    /** The cache. */
    private final LfsaProperties.Cache cache = new LfsaProperties.Cache();
    
    /** The cors. */
    private final CorsConfiguration cors = new CorsConfiguration();
    
    /** The http. */
    private final LfsaProperties.Http http = new LfsaProperties.Http();

    /**
     * Instantiates a new lfsa properties.
     */
    public LfsaProperties() {
    }

    /**
     * Gets the cache.
     *
     * @return the cache
     */
    public LfsaProperties.Cache getCache() {
        return this.cache;
    }
    
    /**
     * Gets the cors.
     *
     * @return the cors
     */
    public CorsConfiguration getCors() {
        return this.cors;
    }
    
    /**
     * Gets the http.
     *
     * @return the http
     */
    public LfsaProperties.Http getHttp() {
        return this.http;
    }

    /**
     * The Class Cache.
     *
     * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
     */
    public static class Cache {
        
        /** The ehcache. */
        private final LfsaProperties.Cache.Ehcache ehcache = new LfsaProperties.Cache.Ehcache();

        /**
         * Instantiates a new cache.
         */
        public Cache() {
        }

        /**
         * Gets the ehcache.
         *
         * @return the ehcache
         */
        public LfsaProperties.Cache.Ehcache getEhcache() {
            return this.ehcache;
        }

        /**
         * The Class Ehcache.
         *
         * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
         */
        public static class Ehcache {
            
            /** The time to live seconds. */
            private int timeToLiveSeconds = 3600;
            
            /** The max entries. */
            private long maxEntries = 100L;

            /**
             * Instantiates a new ehcache.
             */
            public Ehcache() {
            }

            /**
             * Gets the time to live seconds.
             *
             * @return the time to live seconds
             */
            public int getTimeToLiveSeconds() {
                return this.timeToLiveSeconds;
            }

            /**
             * Sets the time to live seconds.
             *
             * @param timeToLiveSeconds the new time to live seconds
             */
            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            /**
             * Gets the max entries.
             *
             * @return the max entries
             */
            public long getMaxEntries() {
                return this.maxEntries;
            }

            /**
             * Sets the max entries.
             *
             * @param maxEntries the new max entries
             */
            public void setMaxEntries(long maxEntries) {
                this.maxEntries = maxEntries;
            }
        }
    }

    /**
     * The Class Ehcache.
     *
     * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
     */
    public static class Ehcache {
        
        /** The time to live seconds. */
        private int timeToLiveSeconds = 3600;
        
        /** The max entries. */
        private long maxEntries = 100L;

        /**
         * Instantiates a new ehcache.
         */
        public Ehcache() {
        }

        /**
         * Gets the time to live seconds.
         *
         * @return the time to live seconds
         */
        public int getTimeToLiveSeconds() {
            return this.timeToLiveSeconds;
        }

        /**
         * Sets the time to live seconds.
         *
         * @param timeToLiveSeconds the new time to live seconds
         */
        public void setTimeToLiveSeconds(int timeToLiveSeconds) {
            this.timeToLiveSeconds = timeToLiveSeconds;
        }

        /**
         * Gets the max entries.
         *
         * @return the max entries
         */
        public long getMaxEntries() {
            return this.maxEntries;
        }

        /**
         * Sets the max entries.
         *
         * @param maxEntries the new max entries
         */
        public void setMaxEntries(long maxEntries) {
            this.maxEntries = maxEntries;
        }
    }

    /**
     * The Class Http.
     *
     * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
     */
    public static class Http {
        
        /** The cache. */
        private final LfsaProperties.Http.Cache cache = new LfsaProperties.Http.Cache();

        /**
         * Instantiates a new http.
         */
        public Http() {
        }

        /**
         * Gets the cache.
         *
         * @return the cache
         */
        public LfsaProperties.Http.Cache getCache() {
            return this.cache;
        }

        /**
         * The Class Cache.
         *
         * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
         */
        public static class Cache {
            
            /** The time to live in days. */
            private int timeToLiveInDays = 1461;

            /**
             * Instantiates a new cache.
             */
            public Cache() {
            }

            /**
             * Gets the time to live in days.
             *
             * @return the time to live in days
             */
            public int getTimeToLiveInDays() {
                return this.timeToLiveInDays;
            }

            /**
             * Sets the time to live in days.
             *
             * @param timeToLiveInDays the new time to live in days
             */
            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

}
