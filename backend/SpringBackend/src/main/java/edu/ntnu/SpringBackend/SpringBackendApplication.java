package edu.ntnu.SpringBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBackendApplication is the main entry point for the Spring Boot backend-application.
 * It contains the main method that starts the application.
 * <p>
 * This class is responsible for bootstrapping the Spring application context
 * and launching the application.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class SpringBackendApplication {

	/**
	 * The main method is the entry point of the Spring Boot backend-application.
	 * It starts the application by calling the run method of SpringApplication.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBackendApplication.class, args);
	}

}
