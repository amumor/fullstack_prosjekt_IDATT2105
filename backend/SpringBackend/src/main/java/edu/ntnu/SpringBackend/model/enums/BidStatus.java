package edu.ntnu.SpringBackend.model.enums;

/**
 * Enum representing the status of a bid.
 * <p>
 * The status can be one of the following:
 * <ul>
 *   <li>PENDING: The bid has been placed but not yet accepted or rejected.</li>
 *   <li>ACCEPTED: The bid has been accepted.</li>
 *   <li>REJECTED: The bid has been rejected.</li>
 *   <li>CANCELED: The bid has been canceled.</li>
 * </ul>
 */
public enum BidStatus {
  PENDING,
  ACCEPTED,
  REJECTED,
  CANCELED
}
