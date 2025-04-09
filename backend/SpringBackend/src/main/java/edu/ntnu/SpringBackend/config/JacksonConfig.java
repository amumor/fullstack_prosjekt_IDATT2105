package edu.ntnu.SpringBackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson configuration for the application.
 * <p>
 * This class is responsible for configuring the Jackson ObjectMapper
 * to handle Java 8 date and time types, specifically LocalDateTime.
 * </p>
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class JacksonConfig {
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  /**
   * Configures the ObjectMapper to use a custom serializer for LocalDateTime.
   * <p>
   * This method registers a JavaTimeModule with a custom serializer for LocalDateTime
   * and disables the default behavior of writing dates as timestamps.
   * </p>
   *
   * @return the configured ObjectMapper
   */
  @Bean
  public ObjectMapper objectMapper() {
    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(module);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }
}
