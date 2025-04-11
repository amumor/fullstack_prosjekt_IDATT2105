package edu.ntnu.SpringBackend.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * GlobalExceptionHandler is a centralized exception handling component
 * that handles various exceptions thrown in the application.
 * It provides custom responses for different types of exceptions,
 * including user not found, bad credentials, and data integrity violations.
 * <p>
 * This class uses Spring's @ControllerAdvice annotation to handle exceptions globally
 * and provides specific methods for handling different types of exceptions.
 *
 * @author Vetle Hodne, Amund Mørk
 * @version 1.0
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger(
          GlobalExceptionHandler.class
  );

  /**
   * Handles NoSuchElementException and returns a 404 Not Found response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 404 status and error message
   */
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
    logger.error("!!! Element not found: {}", ex.getMessage());
    return new ResponseEntity<>("User not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles UsernameNotFoundException and returns a 404 Not Found response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 404 status and error message
   */
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
    logger.error("!!! Username not found: {}", ex.getMessage());
    return new ResponseEntity<>("User not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles NoResourceFoundException and returns a 404 Not Found response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 404 status and error message
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<String> handleObjectNotFoundException(NoResourceFoundException ex, WebRequest request) {
    logger.error("Object not found: {}", ex.getMessage());
    return new ResponseEntity<>("Object not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  /**
   * Handles IllegalArgumentException and returns a 400 Bad Request response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 400 status and error message
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
    logger.error("!!! Bad request: {}", ex.getMessage());
    return new ResponseEntity<>("Bad request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles IllegalStateException and returns a 400 Bad Request response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 400 status and error message
   */
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
    logger.error("!!! State error: {}", ex.getMessage());
    return new ResponseEntity<>("State error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles HttpMessageNotReadableException and returns a 400 Bad Request response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 400 status and error message
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
    logger.error("!!! Invalid input: {}", ex.getMessage());
    return ResponseEntity.badRequest().body("Invalid input: " + ex.getMessage());
  }

  /**
   * Handles ConstraintViolationException and returns a 409 Conflict response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 409 status and error message
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
    logger.error("!!! Database error: {}", ex.getMessage());
    return new ResponseEntity<>("Database constraint error", HttpStatus.CONFLICT);
  }

  /**
   * Handles DataIntegrityViolationException and returns a 409 Conflict response.
   *
   * @param ex the exception
   * @return a ResponseEntity with a 409 status and error message
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
    logger.error("!!! Data integrity violation: {}", ex.getMessage());
    return new ResponseEntity<>("Invalid input: An instance of this field already exists.", HttpStatus.CONFLICT);
  }

  /**
   * Handles BadCredentialsException and returns a 401 Unauthorized response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 401 status and error message
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
    logger.error("!!! Bad credentials: {}", ex.getMessage());
    return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles AccessDeniedException and returns a 403 Forbidden response.
   *
   * @param ex the exception
   * @return a ResponseEntity with a 403 status and error message
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
  }

  /**
   * Handles MethodArgumentNotValidException and returns a 400 Bad Request response.
   * Used for validation errors in request bodies.
   *
   * @param ex the exception
   * @return a ResponseEntity with a 400 status and error message
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
    );
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles all other exceptions and returns a 500 Internal Server Error response.
   *
   * @param ex      the exception
   * @param request the web request
   * @return a ResponseEntity with a 500 status and error message
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
    logger.error("!!! Internal server error: {}: exc: {}", ex.getMessage(), ex.getClass());
    return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}