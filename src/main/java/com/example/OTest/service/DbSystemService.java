package com.example.OTest.service;

import com.example.OTest.model.DbSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DbSystemService {
  private static final Map<Long, DbSystem> dbSystems = new HashMap<>();

  public DbSystem createDbSystem() {
    DbSystem dbSystem = new DbSystem(1L, null);

    dbSystems.put(1L, dbSystem);

    return dbSystem;
  }

  public Optional<DbSystem> findById(Long id) {
    return Optional.ofNullable(dbSystems.get(id));
  }

}
