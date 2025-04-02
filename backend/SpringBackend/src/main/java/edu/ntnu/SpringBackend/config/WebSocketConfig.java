package edu.ntnu.SpringBackend.config;

import edu.ntnu.SpringBackend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws-chat")
            .setAllowedOrigins("*")  // Configure proper origins in production
            .withSockJS();  // Add SockJS fallback for browsers

    registry.addEndpoint("/ws-chat")
            .setAllowedOrigins("*");
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic");
    registry.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {
      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
          String token = extractTokenFromHeader(accessor);
          if (token != null) {
            String userEmail = jwtService.extractUsername(token);
            if (userEmail != null) {
              UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
              if (jwtService.isTokenValid(token, userDetails)) {
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                accessor.setUser(auth);
              }
            }
          }
        }
        return message;
      }

      private String extractTokenFromHeader(StompHeaderAccessor accessor) {
        // Check Authorization header
        List<String> authorization = accessor.getNativeHeader("Authorization");
        if (authorization != null && !authorization.isEmpty()) {
          String bearerToken = authorization.get(0);
          if (bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
          }
          return bearerToken; // In case just the token is provided
        }

        // Check for token in query string
        String fullUrl = accessor.getFirstNativeHeader("simpConnectUrl");
        if (fullUrl != null && fullUrl.contains("?")) {
          String query = fullUrl.split("\\?")[1];
          if (query.contains("token=")) {
            String[] params = query.split("&");
            for (String param : params) {
              if (param.startsWith("token=")) {
                return param.substring(6);
              }
            }
          }
        }

        return null;
      }
    });
  }
}
