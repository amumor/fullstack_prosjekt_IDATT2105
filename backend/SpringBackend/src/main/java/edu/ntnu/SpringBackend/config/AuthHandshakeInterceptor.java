package edu.ntnu.SpringBackend.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;
import java.util.Map;

public class AuthHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
  /*@Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                 WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    // Extract the token from the query parameters
    String token = request.getURI().getQuery(); // You'll need to parse the query string to extract "token"
    // Validate token here (or delegate to your auth service)
    if (token != null && token.contains("token=")) {
      // Example: store token in attributes for later use
      attributes.put("token", token.substring(token.indexOf("token=") + 6));
      return super.beforeHandshake(request, response, wsHandler, attributes);
    }
    return false; // Reject handshake if no valid token
  }*/

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                 WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    List<String> authHeaders = request.getHeaders().get("Authorization");
    if (authHeaders != null && !authHeaders.isEmpty()) {
      String token = authHeaders.get(0);
      // Validate the token
      attributes.put("token", token);
      return super.beforeHandshake(request, response, wsHandler, attributes);
    }
    return false;
  }
}
