package com.example.OTest.service;

import com.example.OTest.model.WorkRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkRequestService {
  private final Map<Long, List<WorkRequest>> dbSystemWorkRequests = new ConcurrentHashMap<>();
  private final AtomicLong workRequestIdGenerator = new AtomicLong(1);

  private final DbSystemService dbSystemService;

  @Autowired
  public WorkRequestService(DbSystemService dbSystemService) {
    this.dbSystemService = dbSystemService;
  }

  public synchronized WorkRequest createWorkRequest(Long dbSystemId) {
    dbSystemService.checkLock(dbSystemId);

    if (getTasksInProgress(dbSystemId) >= 5) {
      dbSystemService.lockDbSystem(dbSystemId, LocalDateTime.now().plusMinutes(2));
      throw new IllegalStateException("Maximum concurrent WorkRequests reached. DbSystem is locked for 2 minutes.");
    }

    WorkRequest newRequest = createRequest();
    storeRequest(dbSystemId, newRequest);
    return newRequest;
  }

  public List<WorkRequest> findByDbSystemId(Long dbSystemId) {
    return dbSystemWorkRequests.getOrDefault(dbSystemId, Collections.emptyList());
  }

  private int getTasksInProgress(Long dbSystemId) {
    List<WorkRequest> requests = dbSystemWorkRequests.getOrDefault(dbSystemId, new ArrayList<>());
    // TODO: check tasts in progress
    return requests.size();
  }

  private WorkRequest createRequest() {
    Long newRequestId = workRequestIdGenerator.getAndIncrement();
    int randomProcessingTime = new Random().nextInt(61);  // 0 to 60 seconds

    return new WorkRequest(
        newRequestId,
        LocalDateTime.now(),
        randomProcessingTime
    );
  }

  private void storeRequest(Long dbSystemId, WorkRequest newRequest) {
    List<WorkRequest> requests = dbSystemWorkRequests.getOrDefault(dbSystemId, new ArrayList<>());
    requests.add(newRequest);
    dbSystemWorkRequests.put(dbSystemId, requests);
  }
}
