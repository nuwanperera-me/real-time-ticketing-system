package com.nuwanperera.backend.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogAppender extends AbstractAppender {
  private static LogAppender instance;

  private static final List<String> logs = Collections.synchronizedList(new LinkedList<>());
  private final Layout<String> layout;

  private LogAppender() {
    super("LogAppender", null, null, true, null);
    this.layout = PatternLayout.newBuilder()
        .withPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} %-5p [%t]: %m%n")
        .build();
    start();
  }

  public static LogAppender getInstance() {
    if (instance == null) {
      instance = new LogAppender();
    }
    return instance;
  }

  @Override
  public void append(LogEvent event) {
    String formattedLog = layout.toSerializable(event);
    logs.add(formattedLog.trim());
    if (logs.size() > 100) {
      logs.remove(0);
    }
  }

  public static List<String> getLogs() {
    return logs;
  }
}
