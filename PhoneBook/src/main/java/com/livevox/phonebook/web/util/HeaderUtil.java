/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * The Class HeaderUtil.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public final class HeaderUtil {
    
    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    /**
     * Instantiates a new header util.
     */
    private HeaderUtil() {
    }

    /**
     * Creates the alert.
     *
     * @param applicationName the application name
     * @param message the message
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createAlert(String applicationName, String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-alert", message);

        try {
            headers.add("X-" + applicationName + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException var5) {
        }

        return headers;
    }

    /**
     * Creates the entity creation alert.
     *
     * @param applicationName the application name
     * @param enableTranslation the enable translation
     * @param entityName the entity name
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createEntityCreationAlert(String applicationName, boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation ? applicationName + "." + entityName + ".created" : "A new " + entityName + " is created with identifier " + param;
        return createAlert(applicationName, message, param);
    }

    /**
     * Creates the entity update alert.
     *
     * @param applicationName the application name
     * @param enableTranslation the enable translation
     * @param entityName the entity name
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createEntityUpdateAlert(String applicationName, boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation ? applicationName + "." + entityName + ".updated" : "A " + entityName + " is updated with identifier " + param;
        return createAlert(applicationName, message, param);
    }

    /**
     * Creates the entity deletion alert.
     *
     * @param applicationName the application name
     * @param enableTranslation the enable translation
     * @param entityName the entity name
     * @param param the param
     * @return the http headers
     */
    public static HttpHeaders createEntityDeletionAlert(String applicationName, boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation ? applicationName + "." + entityName + ".deleted" : "A " + entityName + " is deleted with identifier " + param;
        return createAlert(applicationName, message, param);
    }

    /**
     * Creates the failure alert.
     *
     * @param applicationName the application name
     * @param enableTranslation the enable translation
     * @param entityName the entity name
     * @param errorKey the error key
     * @param defaultMessage the default message
     * @return the http headers
     */
    public static HttpHeaders createFailureAlert(String applicationName, boolean enableTranslation, String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        String message = enableTranslation ? "error." + errorKey : defaultMessage;
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-error", message);
        headers.add("X-" + applicationName + "-params", entityName);
        return headers;
    }
}

