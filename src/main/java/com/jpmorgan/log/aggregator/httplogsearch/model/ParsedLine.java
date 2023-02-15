package com.jpmorgan.log.aggregator.httplogsearch.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ParsedLine {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss Z");
    Logger logger = LoggerFactory.getLogger(ParsedLine.class);
    private String ip;
    private String user;
    private Date dateTIme;
    private String method;
    private String uri;
    private String protocol;
    private int code;
    private int dataSize;

    private String logLine;

    public ParsedLine(){

    }

    public ParsedLine(String s){
        //103.38.201.7 - aut [26/Jul/2000 03:46:20 +0000] "GET /followers/34 HTTP/1.0" 403 116
        //Regular expression is best option to use this
        this.logLine = s;
        String dateTImeString = "";
        if( s != null && s.length() > 0 ) {
            int ipAddressIndex = s.indexOf("-");
            if (ipAddressIndex > -1) {
                this.ip = s.substring(0, ipAddressIndex).trim();
            }
            int userIndex = s.indexOf("[");
            if (userIndex > -1) {
                this.user = s.substring(ipAddressIndex + 1, userIndex).trim();
            }
            int dateTimeIndex = s.indexOf("]");
            if (dateTimeIndex > -1) {
                dateTImeString = s.substring(userIndex + 1, dateTimeIndex).trim();
                //Parse date time
                try {
                    dateTIme = format.parse(dateTImeString);
                } catch (ParseException e) {
                    logger.error(e.toString());
                }
            }
            int uriStart = s.indexOf("\"");
            int uriEnd = -1;
            if (uriStart > -1) {
                uriEnd = s.indexOf("\"", uriStart + 1);
                if (uriEnd > uriStart) {
                    String fullUrl = s.substring(uriStart + 1, uriEnd);
                    String[] uriSub = fullUrl.split(" ");
                    if (uriSub != null) {
                        if (uriSub.length > 0) {
                            this.method = uriSub[0];
                        }
                        if (uriSub.length > 1) {
                            this.uri = uriSub[1];
                        }
                        if (uriSub.length > 2) {
                            this.protocol = uriSub[2];
                        }
                    }
                    String lastpart = s.substring(uriEnd + 1).trim();
                    if (lastpart != null && s.length() > 0) {
                        String[] lastSub = lastpart.split(" ");
                        if (lastSub != null) {
                            if (lastSub.length > 0) {
                                try {
                                    this.code = Integer.parseInt(lastSub[0]);
                                }catch(Exception e){
                                    logger.error(e.toString());
                                }
                            }
                            if (lastSub.length > 1) {
                                try {
                                    this.dataSize = Integer.parseInt(lastSub[1]);
                                }catch(Exception e){
                                    logger.error(e.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "{" +
                "logLine:"+this.logLine + "\n"+
                "ip:" + this.ip + ", " +
                "user:" + this.user + ", " +
                "dateTime:" + this.dateTIme + ", " +
                "method:" + this.method + ", " +
                "uri:" + this.uri + ", " +
                "protocol:" + this.protocol + ", " +
                "code:" + this.code + ", " +
                "dataSize:" + this.dataSize +
                "}";
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDateTIme() {
        return dateTIme;
    }

    public void setDateTIme(Date dateTIme) {
        this.dateTIme = dateTIme;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public String getLogLine() {
        return logLine;
    }

    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }
}
