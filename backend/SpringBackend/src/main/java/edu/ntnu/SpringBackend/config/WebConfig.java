package edu.ntnu.SpringBackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for the application.
 * <p>
 * This class is responsible for configuring web-related settings,
 * including resource handlers for serving static resources.
 * </p>
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * Configures resource handlers for serving static resources.
   * This method maps the "/images/**" URL pattern to the "file:/app/uploads/" directory.
   *
   * @param registry the ResourceHandlerRegistry to configure
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/images/**")
            .addResourceLocations("file:/app/uploads/")
            .setCachePeriod(0);
  }
}
