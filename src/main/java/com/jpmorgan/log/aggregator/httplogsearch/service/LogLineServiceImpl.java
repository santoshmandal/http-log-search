package com.jpmorgan.log.aggregator.httplogsearch.service;

import com.jpmorgan.log.aggregator.httplogsearch.model.LogLine;
import com.jpmorgan.log.aggregator.httplogsearch.repository.LogLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Configuration
public class LogLineServiceImpl implements LogLineService {
    private final LogLineRepository logLineRepository;
    Logger logger = LoggerFactory.getLogger(LogLineServiceImpl.class);

    @Value("${api.limit.records.count}")
    int maxRecords;

    public LogLineServiceImpl(LogLineRepository logLineRepository) {
        this.logLineRepository = logLineRepository;
    }

    public void saveAll(List<LogLine> logLineList) {
        logger.info("Total insert Request received : " + logLineList.size());
        logLineRepository.saveAll(logLineList);
    }

    public List<String> fetchLogs(String user, String method, int code, int pageCount) {
        List<String> result = new ArrayList<>();
        List<LogLine> queryResult = null;
        Pageable page = PageRequest.of(pageCount, maxRecords, Sort.by("logdate").descending());
        if (user == null && method == null && code == 0) {
            queryResult = logLineRepository.findAll(page).getContent();
        } else if (user != null && method != null && code > 0) {
            queryResult = logLineRepository.findByAuserAndAmethodAndCodeOrderByLogdateDesc(user, method, code, page);
        } else if (user != null && method == null && code == 0) {
            queryResult = logLineRepository.findByAuserOrderByLogdateDesc(user, page);
        } else if (user == null && method != null && code == 0) {
            queryResult = logLineRepository.findByAmethodOrderByLogdateDesc(method, page);
        } else if (user == null && method == null && code > 0) {
            queryResult = logLineRepository.findByCodeOrderByLogdateDesc(code, page);
        } else if (user != null && method != null && code == 0) {
            queryResult = logLineRepository.findByAuserAndAmethodOrderByLogdateDesc(user, method, page);
        } else if (user != null && method == null && code > 0) {
            queryResult = logLineRepository.findByAuserAndCodeOrderByLogdateDesc(user, code, page);
        } else if (user == null && method != null && code > 0) {
            queryResult = logLineRepository.findByAmethodAndCodeOrderByLogdateDesc(method, code, page);
        }
        if (queryResult != null) {
            result = queryResult.stream().map(e -> e.getMessage()).collect(
                    Collectors.toList());
        }
        return result;
    }
}
