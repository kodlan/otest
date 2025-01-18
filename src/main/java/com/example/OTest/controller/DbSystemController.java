package com.example.OTest.controller;

import com.example.OTest.model.DbSystem;
import com.example.OTest.service.DbSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbSystems")
public class DbSystemController {
  private final DbSystemService dbSystemService;

  @Autowired
  public DbSystemController(DbSystemService dbSystemService) {
    this.dbSystemService = dbSystemService;
  }

  @PostMapping
  public ResponseEntity<DbSystem> createDbSystem() {
    DbSystem newDbSystem = dbSystemService.createDbSystem();
    return new ResponseEntity<>(newDbSystem, HttpStatus.CREATED);
  }
}
