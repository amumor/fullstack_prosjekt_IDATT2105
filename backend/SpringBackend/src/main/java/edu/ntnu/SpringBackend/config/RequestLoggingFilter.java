package edu.ntnu.SpringBackend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

/**
 * A filter that logs incoming HTTP requests.
 * It logs the request method, URI, content type, and headers.
 * Used for debugging and monitoring purposes.
 */
@Component
public class RequestLoggingFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

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
