package edu.ntnu.SpringBackend.service;

import edu.ntnu.SpringBackend.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BidService {
  private final Logger logger = LoggerFactory.getLogger(BidService.class);
  private final BidRepository bidRepository;


}
