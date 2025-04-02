package edu.ntnu.SpringBackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * Configures the WebSocket endpoints and message broker.
   * The broker is a server that handles the routing of messages between clients.
   *
   * @param registry the registry for configuring WebSocket endpoints
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws-chat")
            .setAllowedOriginPatterns("*")
            .withSockJS();
  }

  /**
   * Configures the message broker for the WebSocket connection.
   * The message broker is responsible for routing messages to the appropriate subscribers.
   *
   * @param registry the registry for configuring the message broker
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app");
    registry.enableSimpleBroker("/topic");
  }
}
