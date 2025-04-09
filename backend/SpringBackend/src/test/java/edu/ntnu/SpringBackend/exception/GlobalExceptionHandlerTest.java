package edu.ntnu.SpringBackend.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler;
  private WebRequest webRequest;

  @BeforeEach
  void setup() {
    handler = new GlobalExceptionHandler();
    webRequest = new ServletWebRequest(new MockHttpServletRequest());
  }

  @Test
  void testHandleNoSuchElementException() {
    NoSuchElementException ex = new NoSuchElementException("Not found");
    ResponseEntity<String> response = handler.handleNoSuchElementException(ex, webRequest);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("User not found: Not found", response.getBody());
  }

  @Test
  void testHandleUsernameNotFoundException() {
    UsernameNotFoundException ex = new UsernameNotFoundException("User missing");
    ResponseEntity<String> response = handler.handleUsernameNotFoundException(ex, webRequest);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("User not found: User missing", response.getBody());
  }

  @Test
  void testHandleObjectNotFoundException() {
    NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "static resource");
    ResponseEntity<String> response = handler.handleObjectNotFoundException(ex, webRequest);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Object not found: No static resource static resource.", response.getBody());
  }

  @Test
  void testHandleIllegalArgumentException() {
    IllegalArgumentException ex = new IllegalArgumentException("Illegal arg");
    ResponseEntity<String> response = handler.handleIllegalArgumentException(ex, webRequest);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Bad request: Illegal arg", response.getBody());
  }

  @Test
  void testHandleIllegalStateException() {
    IllegalStateException ex = new IllegalStateException("Illegal state");
    ResponseEntity<String> response = handler.handleIllegalStateException(ex, webRequest);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("State error: Illegal state", response.getBody());
  }

  @Test
  void testHandleHttpMessageNotReadableException() {
    HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Unreadable message");
    ResponseEntity<String> response = handler.handleHttpMessageNotReadableException(ex, webRequest);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid input: Unreadable message", response.getBody());
  }

  @Test
  void testHandleConstraintViolationException() {
    ConstraintViolationException ex = new ConstraintViolationException("Constraint failed", null, "Some Constraint");
    ResponseEntity<String> response = handler.handleConstraintViolationException(ex, webRequest);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("Database constraint error", response.getBody());
  }

  @Test
  void testHandleDataIntegrityViolationException() {
    DataIntegrityViolationException ex = new DataIntegrityViolationException("Duplicate entry");
    ResponseEntity<String> response = handler.handleDataIntegrityViolationException(ex, webRequest);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("Invalid input: An instance of this field already exists.", response.getBody());
  }

  @Test
  void testHandleBadCredentialsException() {
    BadCredentialsException ex = new BadCredentialsException("Bad creds");
    ResponseEntity<String> response = handler.handleBadCredentialsException(ex, webRequest);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Invalid email or password", response.getBody());
  }

  @Test
  void testHandleAccessDenied() {
    AccessDeniedException ex = new AccessDeniedException("Access denied");
    ResponseEntity<String> response = handler.handleAccessDenied(ex);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals("Access denied", response.getBody());
  }

  @Test
  void testHandleGlobalException() {
    Exception ex = new Exception("Unexpected error");
    ResponseEntity<String> response = handler.handleGlobalException(ex, webRequest);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Internal server error", response.getBody());
  }
}
