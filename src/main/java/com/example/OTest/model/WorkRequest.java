package com.example.OTest.model;

import java.time.LocalDateTime;

public class WorkRequest {
  private Long id;
  private LocalDateTime createdAt;
  private int processingTime; // simulate processing time

  public WorkRequest() {
  }

  public WorkRequest(Long id, LocalDateTime createdAt, int processingTime) {
    this.id = id;
    this.createdAt = createdAt;
    this.processingTime = processingTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public int getProcessingTime() {
    return processingTime;
  }

  public void setProcessingTime(int processingTime) {
    this.processingTime = processingTime;
  }
}
