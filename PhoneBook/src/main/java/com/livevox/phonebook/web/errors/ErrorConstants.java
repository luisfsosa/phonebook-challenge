/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook.web.errors;

import java.net.URI;

/**
 * The Class ErrorConstants.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public final class ErrorConstants {

    /** The Constant ERR_CONCURRENCY_FAILURE. */
    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    
    /** The Constant ERR_VALIDATION. */
    public static final String ERR_VALIDATION = "error.validation";
    
    /** The Constant PROBLEM_BASE_URL. */
    public static final String PROBLEM_BASE_URL = "https:/www.lfsa.com/problem";
    
    /** The Constant DEFAULT_TYPE. */
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    
    /** The Constant CONSTRAINT_VIOLATION_TYPE. */
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");

    /**
     * Instantiates a new error constants.
     */
    private ErrorConstants() {
    }
}
