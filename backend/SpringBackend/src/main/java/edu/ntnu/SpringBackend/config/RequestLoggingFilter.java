package edu.ntnu.SpringBackend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

/**
 * RequestLoggingFilter is a servlet filter that logs incoming HTTP requests.
 * It logs the request method, URI, content type, and headers.
 * This filter is useful for debugging and monitoring incoming requests to the application.
 * Mainly used for debugging purposes.
 *
 * @author Vetle Hodne
 * @version 1.0
 * @since 1.0
 */
@Component
public class RequestLoggingFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

  /**
   * This method is called for every request to log the request details.
   * It logs the request method, URI, content type, and headers.
   *
   * @param request  the HTTP request
   * @param response the HTTP response
   * @param chain    the filter chain
   * @throws IOException      if an I/O error occurs
   * @throws ServletException if a servlet error occurs
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    if (request instanceof HttpServletRequest httpRequest) {
      logger.info("---- Incoming Request ----");
      logger.info("Method: {}", httpRequest.getMethod());
      logger.info("URI: {}", httpRequest.getRequestURI());

      String contentType = httpRequest.getContentType();
      logger.info("Content-Type: {}", contentType);

      Collections.list(httpRequest.getHeaderNames()).forEach(headerName ->
              logger.info("Header {}: {}", headerName, httpRequest.getHeader(headerName))
      );

      logger.info("--------------------------");
    }

    chain.doFilter(request, response);
  }
}
