/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;

/**
 * The Class DefaultProfileUtil.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public final class DefaultProfileUtil {
    
    /** The Constant SPRING_PROFILE_DEFAULT. */
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";


    /**
     * Instantiates a new default profile util.
     */
    private DefaultProfileUtil() {
    }

    /**
     * Adds the default profile.
     *
     * @param app the app
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap();
        defProperties.put("spring.profiles.default", "dev");
        app.setDefaultProperties(defProperties);
    }
}