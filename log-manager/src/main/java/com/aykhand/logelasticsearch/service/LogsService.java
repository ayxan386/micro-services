package com.aykhand.logelasticsearch.service;

import com.aykhand.logelasticsearch.model.LogModel;

public interface LogsService {
  Iterable<LogModel> getAll();

  Iterable<LogModel> getAll(int page, int pageSize);

  String deleteAll();
}
