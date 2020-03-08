/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.web.filter;


import com.lfsa.luisfsosa.config.LfsaProperties;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class CachingHttpHeadersFilter.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class CachingHttpHeadersFilter implements Filter {
    
    /** The Constant DEFAULT_DAYS_TO_LIVE. */
    public static final int DEFAULT_DAYS_TO_LIVE = 1461;
    
    /** The Constant DEFAULT_SECONDS_TO_LIVE. */
    public static final long DEFAULT_SECONDS_TO_LIVE;
    
    /** The cache time to live. */
    private long cacheTimeToLive;
    
    /** The lfsa properties. */
    private LfsaProperties lfsaProperties;

    /**
     * Instantiates a new caching http headers filter.
     *
     * @param lfsaProperties the lfsa properties
     */
    public CachingHttpHeadersFilter(LfsaProperties lfsaProperties) {
        this.cacheTimeToLive = DEFAULT_SECONDS_TO_LIVE;
        this.lfsaProperties = lfsaProperties;
    }

    /**
     * Inits the.
     *
     * @param filterConfig the filter config
     * @throws ServletException the servlet exception
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.cacheTimeToLive = TimeUnit.DAYS.toMillis((long)this.lfsaProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    /**
     * Destroy.
     */
    public void destroy() {
    }

    /**
     * Do filter.
     *
     * @param request the request
     * @param response the response
     * @param chain the chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setHeader("Cache-Control", "max-age=" + this.cacheTimeToLive + ", public");
        httpResponse.setHeader("Pragma", "cache");
        httpResponse.setDateHeader("Expires", this.cacheTimeToLive + System.currentTimeMillis());
        chain.doFilter(request, response);
    }

    static {
        DEFAULT_SECONDS_TO_LIVE = TimeUnit.DAYS.toMillis(1461L);
    }
}

