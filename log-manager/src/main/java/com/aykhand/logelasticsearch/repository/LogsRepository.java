package com.aykhand.logelasticsearch.repository;

import com.aykhand.logelasticsearch.model.LogModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends ElasticsearchRepository<LogModel, Long> {
}
