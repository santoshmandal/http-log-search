package com.jpmorgan.log.aggregator.httplogsearch.repository;

import com.jpmorgan.log.aggregator.httplogsearch.model.LogLine;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogLineRepository extends JpaRepository<LogLine, Long> {
    List<LogLine> findByAuserOrderByLogdateDesc(String name, Pageable page);

    List<LogLine> findByAmethodOrderByLogdateDesc(String method, Pageable page);

    List<LogLine> findByCodeOrderByLogdateDesc(int code, Pageable page);

    List<LogLine> findByAuserAndAmethodOrderByLogdateDesc(String name, String method, Pageable page);

    List<LogLine> findByAuserAndCodeOrderByLogdateDesc(String name, int code, Pageable page);

    List<LogLine> findByAmethodAndCodeOrderByLogdateDesc(String method, int code, Pageable page);

    List<LogLine> findByAuserAndAmethodAndCodeOrderByLogdateDesc(String name, String method, int code, Pageable page);

}
