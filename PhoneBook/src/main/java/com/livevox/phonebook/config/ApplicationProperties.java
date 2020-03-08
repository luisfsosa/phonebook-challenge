/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Phonebook.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
}
