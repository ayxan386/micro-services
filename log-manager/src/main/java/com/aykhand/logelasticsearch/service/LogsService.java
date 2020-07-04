package com.aykhand.logelasticsearch.service;

import com.aykhand.logelasticsearch.model.LogModel;

public interface LogsService {
  Iterable<LogModel> getAll();

  String deleteAll();
}
