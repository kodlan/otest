package com.example.OTest.controller;

import com.example.OTest.model.DbSystem;
import com.example.OTest.model.WorkRequest;
import com.example.OTest.service.DbSystemService;
import com.example.OTest.service.WorkRequestService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbSystems/{dbSystemId}/workRequests")
public class WorkRequestController {

  private final DbSystemService dbSystemService;
  private final WorkRequestService workRequestService;

  @Autowired
  public WorkRequestController(DbSystemService dbSystemService, WorkRequestService workRequestService) {
    this.dbSystemService = dbSystemService;
    this.workRequestService = workRequestService;
  }

  @PostMapping
  public ResponseEntity<?> createWorkRequest(@PathVariable Long dbSystemId) {

    Optional<DbSystem> dbSystemOpt = dbSystemService.findById(dbSystemId);
    if (dbSystemOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("DbSystem with ID " + dbSystemId + " does not exist.");
    }

    try {
      WorkRequest newRequest = workRequestService.createWorkRequest(dbSystemId);
      return ResponseEntity.status(HttpStatus.CREATED).body(newRequest);
    } catch (IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<?> getWorkRequests(@PathVariable Long dbSystemId) {

    Optional<DbSystem> dbSystemOpt = dbSystemService.findById(dbSystemId);
    if (dbSystemOpt.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body("DbSystem with ID " + dbSystemId + " does not exist.");
    }

    List<WorkRequest> workRequests = workRequestService.findByDbSystemId(dbSystemId);

    return ResponseEntity.ok(workRequests);
  }

}
