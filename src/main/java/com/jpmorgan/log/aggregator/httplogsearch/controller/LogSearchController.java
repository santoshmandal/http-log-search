package com.jpmorgan.log.aggregator.httplogsearch.controller;

import com.jpmorgan.log.aggregator.httplogsearch.model.LogLine;
import com.jpmorgan.log.aggregator.httplogsearch.model.ParsedLine;
import com.jpmorgan.log.aggregator.httplogsearch.repository.LogLineRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.jpmorgan.log.aggregator.httplogsearch.service.LogLineService;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/")
@EnableAutoConfiguration
@Slf4j
public class LogSearchController {
    private final LogLineService LogLineService;

    Logger logger = LoggerFactory.getLogger(LogSearchController.class);

    public LogSearchController(LogLineService LogLineService){
        this.LogLineService = LogLineService;
    }

    @RequestMapping(value="/logs", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getLogsByUserAndMethodAndCode(@RequestParam(required = false) String user,
                                                                      @RequestParam(required = false) String method,
                                                                      @RequestParam(required = false) String code,
                                                                      @RequestParam(required = false) String page){

        logger.info("Query paramas : [" + method + ", " + user + "," + code + "," + page +"]");

        //[Pending] - Validate parameters - especially HttpStatus code and HttpMethod for valid values


        int httpCode = 0;

        if( code != null ){
            try{
               httpCode = Integer.parseInt(code);
            }catch(Exception e){
                httpCode = -1;
                logger.error(e.getMessage());
            }
        }

        int pageCount = 0;
        if( page != null ){
            try{
                pageCount = Integer.parseInt(page);
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        }
        if( pageCount < 0 ){
            pageCount = 0;
        }

        List<String> results = LogLineService.fetchLogs(user,method,httpCode, pageCount);
        return new ResponseEntity<List<String>>(results,HttpStatus.OK);
    }

    @PostMapping(path= "/createlogs", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> addEmployee(@RequestBody List<String> logs)
    {
        List<LogLine> logLinesList = new ArrayList<>();
        for( String log : logs ) {
            //logger.info("" + parsedLine);
            logLinesList.add(new LogLine(new ParsedLine(log)));
        }
        this.LogLineService.saveAll(logLinesList);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
