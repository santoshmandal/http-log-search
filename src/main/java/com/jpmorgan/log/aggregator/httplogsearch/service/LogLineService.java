package com.jpmorgan.log.aggregator.httplogsearch.service;

import com.jpmorgan.log.aggregator.httplogsearch.model.LogLine;

import java.util.List;

public interface LogLineService {
    public void saveAll(List<LogLine> logLineList);

    public List<String> fetchLogs(String user, String method, int code, int pageCount);
}
