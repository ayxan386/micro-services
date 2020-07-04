package com.aykhand.logelasticsearch.controller;

import com.aykhand.logelasticsearch.model.LogModel;
import com.aykhand.logelasticsearch.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/logs")
@RequiredArgsConstructor
public class LogController {

  private final LogsService logsService;

  @GetMapping(value = "/all")
  public ResponseEntity<Iterable<LogModel>> getAll() {
    return ResponseEntity.ok(logsService.getAll());
  }

  @GetMapping(value = "/paged")
  public ResponseEntity<Iterable<LogModel>> getAll(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "pageSize", defaultValue = "50") int pageSize) {
    return ResponseEntity.ok(logsService.getAll(page, pageSize));
  }

  @DeleteMapping(value = "/all")
  public ResponseEntity<String> deleteAll() {
    return ResponseEntity.ok(logsService.deleteAll());
  }
}
