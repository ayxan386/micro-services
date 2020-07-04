package com.aykhand.logelasticsearch.service.impl;

import com.aykhand.logelasticsearch.model.LogModel;
import com.aykhand.logelasticsearch.repository.LogsRepository;
import com.aykhand.logelasticsearch.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {

  private final LogsRepository repository;

  @Override
  public Iterable<LogModel> getAll() {
    return repository.findAll();
  }

  @Override
  public Iterable<LogModel> getAll(int page, int pageSize) {
    return repository.findAll(PageRequest.of(page, pageSize, Sort.by(DESC, "requestTime")));
  }

  @Override
  public String deleteAll() {
    repository.deleteAll();
    return "success";
  }
}
