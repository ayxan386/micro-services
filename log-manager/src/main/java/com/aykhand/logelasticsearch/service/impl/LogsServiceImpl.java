package com.aykhand.logelasticsearch.service.impl;

import com.aykhand.logelasticsearch.model.LogModel;
import com.aykhand.logelasticsearch.repository.LogsRepository;
import com.aykhand.logelasticsearch.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {

  private final LogsRepository repository;

  @Override
  public Iterable<LogModel> getAll() {
    return repository.findAll();
  }
}
