package com.aykhand.logelasticsearch.controller;

import com.aykhand.logelasticsearch.model.LogModel;
import com.aykhand.logelasticsearch.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logs")
@RequiredArgsConstructor
public class LogController {

  private final LogsService logsService;

  @GetMapping(value = "/all")
  public ResponseEntity<Iterable<LogModel>> getAll() {
    return ResponseEntity.ok(logsService.getAll());
  }

  @DeleteMapping(value = "/all")
  public ResponseEntity<String> deleteAll(){
    return ResponseEntity.ok(logsService.deleteAll());
  }
}
