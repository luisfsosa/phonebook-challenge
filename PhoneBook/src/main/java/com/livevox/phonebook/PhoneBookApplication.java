/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms. 
 * 
 * Phonebook Test
 */
package com.livevox.phonebook;

import com.lfsa.luisfsosa.config.LfsaConstants;
import com.lfsa.luisfsosa.config.LfsaProperties;
import com.livevox.phonebook.config.ApplicationProperties;
import com.livevox.phonebook.config.DefaultProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

/**
 * The Class PhoneBookApplication.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, LfsaProperties.class})
public class PhoneBookApplication {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(PhoneBookApplication.class);

	/** The env. */
	private final Environment env;

	/**
	 * Instantiates a new phone book application.
	 *
	 * @param env the env
	 */
	public PhoneBookApplication(Environment env) {
		this.env = env;
	}

	/**
	 * Initializes phonebook.
	 * <p>
	 * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
	 */
	@PostConstruct
	public void initApplication() {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains(LfsaConstants.SPRING_PROFILE_DEVELOPMENT)) {
			log.error("You have misconfigured your application! It should not run " +
					"with both the 'dev' and 'prod' profiles at the same time.");
		}
	}

	/**
	 * Main method, used to run the application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PhoneBookApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	/**
	 * Log application startup.
	 *
	 * @param env the env
	 */
	private static void logApplicationStartup(Environment env) {
		String protocol = "http";
		String serverPort = env.getProperty("server.port");
		String contextPath = env.getProperty("server.servlet.context-path");
		if (StringUtils.isBlank(contextPath)) {
			contextPath = "/";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\t{}://localhost:{}{}\n\t" +
						"External: \t{}://{}:{}{}\n\t" +
						"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol,
				serverPort,
				contextPath,
				protocol,
				hostAddress,
				serverPort,
				contextPath,
				env.getActiveProfiles());
	}


}
