/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.web.util;

import java.text.MessageFormat;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The Class PaginationUtil.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public final class PaginationUtil {
    
    /** The Constant HEADER_X_TOTAL_COUNT. */
    private static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
    
    /** The Constant HEADER_LINK_FORMAT. */
    private static final String HEADER_LINK_FORMAT = "<{0}>; rel=\"{1}\"";

    /**
     * Instantiates a new pagination util.
     */
    private PaginationUtil() {
    }

    /**
     * Generate pagination http headers.
     *
     * @param <T> the generic type
     * @param uriBuilder the uri builder
     * @param page the page
     * @return the http headers
     */
    public static <T> HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();
        StringBuilder link = new StringBuilder();
        if (pageNumber < page.getTotalPages() - 1) {
            link.append(prepareLink(uriBuilder, pageNumber + 1, pageSize, "next")).append(",");
        }

        if (pageNumber > 0) {
            link.append(prepareLink(uriBuilder, pageNumber - 1, pageSize, "prev")).append(",");
        }

        link.append(prepareLink(uriBuilder, page.getTotalPages() - 1, pageSize, "last")).append(",").append(prepareLink(uriBuilder, 0, pageSize, "first"));
        headers.add("Link", link.toString());
        return headers;
    }

    /**
     * Prepare link.
     *
     * @param uriBuilder the uri builder
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param relType the rel type
     * @return the string
     */
    private static String prepareLink(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize, String relType) {
        return MessageFormat.format("<{0}>; rel=\"{1}\"", preparePageUri(uriBuilder, pageNumber, pageSize), relType);
    }

    /**
     * Prepare page uri.
     *
     * @param uriBuilder the uri builder
     * @param pageNumber the page number
     * @param pageSize the page size
     * @return the string
     */
    private static String preparePageUri(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize) {
        return uriBuilder.replaceQueryParam("page", new Object[]{Integer.toString(pageNumber)}).replaceQueryParam("size", new Object[]{Integer.toString(pageSize)}).toUriString().replace(",", "%2C").replace(";", "%3B");
    }
}

