package com.collector.test.repository;

import com.collector.test.entity.Logs;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface LogsRepository extends ReactiveMongoRepository<Logs,String> {

Flux<Logs> findAll();

}
