package com.collector.test.service;

import com.collector.test.entity.Logs;
import com.collector.test.repository.LogsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@Transactional
public class LogsService {
    private final LogsRepository logsRepository;

    public LogsService(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }
    public void SaveLog(Logs log){
        logsRepository.save(log).subscribe(lg -> System.out.println("data saved in Mongodb " + lg.getLogMessage()) );

    }
    public void SaveLogs(List<Logs> logs){
        logsRepository.saveAll(logs).subscribe(lg -> System.out.println("data saved in Mongodb " + lg.getLogMessage()) );
    }
    public Flux<Logs> getAllLogs(){
        //make it pageable if you want
        return logsRepository.findAll();
    }
}
