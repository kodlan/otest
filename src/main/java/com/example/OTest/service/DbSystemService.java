package com.example.OTest.service;

import com.example.OTest.model.DbSystem;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class DbSystemService {
  private static final Map<Long, DbSystem> dbSystems = new ConcurrentHashMap<>();
  private final AtomicLong dbSystemIdGenerator = new AtomicLong(1);

  public synchronized DbSystem createDbSystem() {
    Long newId = dbSystemIdGenerator.getAndIncrement();

    DbSystem dbSystem = new DbSystem(newId, null);
    dbSystems.put(newId, dbSystem);

    return dbSystem;
  }

  public Optional<DbSystem> findById(Long id) {
    return Optional.ofNullable(dbSystems.get(id));
  }

  public synchronized DbSystem checkLock(Long dbSystemId) {
    DbSystem dbSystem = findById(dbSystemId)
        .orElseThrow(() -> new IllegalArgumentException("DbSystem not found: " + dbSystemId));

    LocalDateTime now = LocalDateTime.now();

    if (dbSystem.getLockedUntil() != null) {
      if (now.isBefore(dbSystem.getLockedUntil())) {
        // Still locked => reject
        throw new IllegalStateException("DbSystem " + dbSystemId + " is locked until " + dbSystem.getLockedUntil());
      } else {
        // The lock has expired => unlock
        dbSystem.setLockedUntil(null);
        updateDbSystem(dbSystem);
      }
    }

    return dbSystem;
  }

  public synchronized DbSystem updateDbSystem(DbSystem dbSystem) {
    dbSystems.put(dbSystem.getId(), dbSystem);
    return dbSystem;
  }

  public synchronized void lockDbSystem(Long dbSystemId, LocalDateTime lockedUntil) {
    DbSystem dbSystem = findById(dbSystemId)
        .orElseThrow(() -> new IllegalArgumentException("DbSystem not found: " + dbSystemId));

    dbSystem.setLockedUntil(lockedUntil);
    updateDbSystem(dbSystem);
  }
}
