package edu.ntnu.SpringBackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration class for configuring resource handlers.
 * This class implements the WebMvcConfigurer interface to customize the Spring MVC configuration.
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
        .addResourceLocations("file:/app/uploads/");
  }
}
