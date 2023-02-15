## How to build
Go to http-log-search service and run the maven command

    mvn clean install 
    
    To run locally
    java -jar target/http-log-search*.jar

## Docker Image Build

    docker build --tag=log-search:latest .

## Docker Image Run

    docker run -p 8081:8081 log-search:latest

## Usage
    curl --location --request GET 'http://localhost:8081/v1/logs?user=roberthowell&method=GET&code=200&page=0'
    GET localhost:8081/v1/logs
    Query Params
    user - optinal
    method - optional - Valid HTTP method
    code - optional - Valid HTTP status code
    page - optional - Dafault '0' valid integer greater >= 0
    
    Note: By default above url send 100 records, you need to use the page parameter to get the more results.

## H2 database access
    URL: http://localhost:8081/h2-console/
    Username: sa password: (empty)
    JDBC Url: jdbc:h2:mem:testdb