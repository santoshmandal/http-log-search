package com.jpmorgan.log.aggregator.httplogsearch.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name="LOGLINE")
public class LogLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;
    @Basic(optional = false)
    @Column(name = "auser")
    String auser;

    @Basic(optional = false)
    @Column(name = "amethod")
    String amethod;

    @Basic(optional = false)
    @Column(name = "code")
    int code;

    @Basic(optional = false)
    @Column(name = "message")
    String message;

    @Basic(optional = false)
    @Column(name = "logdate")
    Date logdate;

    public LogLine(String auser, String amethod, int code, String message, Date logdate) {
        this.auser = auser;
        this.amethod = amethod;
        this.code = code;
        this.message = message;
        this.logdate = logdate;
    }

    public LogLine(ParsedLine parsedLine) {
        this.auser = parsedLine.getUser();
        this.amethod = parsedLine.getMethod();
        this.code = parsedLine.getCode();
        this.message = parsedLine.getLogLine();
        this.logdate = parsedLine.getDateTIme();
    }

    public LogLine() {

    }

    public String getAuser() {
        return auser;
    }

    public void setAuser(String auser) {
        this.auser = auser;
    }

    public String getAmethod() {
        return amethod;
    }

    public void setAmethod(String amethod) {
        this.amethod = amethod;
    }

    public int getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "LogLine{" +
                "id=" + id +
                ", auser='" + auser + '\'' +
                ", amethod='" + amethod + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
