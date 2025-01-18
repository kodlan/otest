package com.example.OTest.service;

import com.example.OTest.model.DbSystem;
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

}
