package com.jpmorgan.log.aggregator.httplogsearch.service;

import com.jpmorgan.log.aggregator.httplogsearch.model.LogLine;

import java.util.List;

public interface LogLineService {
    void saveAll(List<LogLine> logLineList);

    List<String> fetchLogs(String user, String method, int code, int pageCount);
}
