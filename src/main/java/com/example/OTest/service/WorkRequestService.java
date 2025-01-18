package com.example.OTest.service;

import com.example.OTest.model.WorkRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class WorkRequestService {
  private final Map<Long, List<WorkRequest>> dbSystemWorkRequests = new HashMap<>();

  public WorkRequest createWorkRequest(Long dbSystemId) {
    Long newId = 1L;

    // Random processing time between 0 and 60 seconds
    int randomProcessingTime = new Random().nextInt(61);

    WorkRequest workRequest = new WorkRequest(
        newId,
        LocalDateTime.now(),
        randomProcessingTime
    );


    List<WorkRequest> reqList = dbSystemWorkRequests.getOrDefault(dbSystemId, new ArrayList<>());
    reqList.add(workRequest);

    dbSystemWorkRequests.put(dbSystemId, reqList);

    return workRequest;
  }

  public List<WorkRequest> findByDbSystemId(Long dbSystemId) {
    return dbSystemWorkRequests.getOrDefault(dbSystemId, Collections.emptyList());
  }

}
