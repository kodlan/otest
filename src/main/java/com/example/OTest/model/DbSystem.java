package com.example.OTest.model;

import java.time.LocalDateTime;

public class DbSystem {
  private Long id;
  private LocalDateTime lockedUntil;

  public DbSystem() {
  }

  public DbSystem(Long id, LocalDateTime lockedUntil) {
    this.id = id;
    this.lockedUntil = lockedUntil;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getLockedUntil() {
    return lockedUntil;
  }

  public void setLockedUntil(LocalDateTime lockedUntil) {
    this.lockedUntil = lockedUntil;
  }
}
