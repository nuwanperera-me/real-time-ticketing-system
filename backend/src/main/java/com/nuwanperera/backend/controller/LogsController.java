package com.nuwanperera.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuwanperera.backend.utils.LogAppender;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/logs")
public class LogsController {

  @GetMapping
  public ResponseEntity<List<String>> getLogs() {
    return ResponseEntity.ok(LogAppender.getLogs());
  }
}
