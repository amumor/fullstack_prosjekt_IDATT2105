package edu.ntnu.SpringBackend.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class WebSocketMessage {
  private String content;
}
