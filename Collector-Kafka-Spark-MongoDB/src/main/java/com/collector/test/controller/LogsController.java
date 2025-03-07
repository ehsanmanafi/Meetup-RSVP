package com.collector.test.controller;

import com.collector.test.entity.Logs;
import com.collector.test.service.LogsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/v1")
public class LogsController {
    private final LogsService logsService;

    public LogsController(LogsService logsService) {
        this.logsService = logsService;
    }
    @GetMapping("/logs")
    public ResponseEntity<Flux<Logs>> getAllLogs(){
        return ResponseEntity.status(HttpStatus.OK).body(logsService.getAllLogs());
    }
}
