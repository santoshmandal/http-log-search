DROP TABLE IF EXISTS LOGLINE;
CREATE TABLE LOGLINE (
                      id INT AUTO_INCREMENT  PRIMARY KEY,
                      auser VARCHAR(50) NOT NULL,
                      amethod VARCHAR(50) NOT NULL,
                      code INT NOT NULL,
                      message VARCHAR(500) NOT NULL,
                      logdate TIMESTAMP NOT NULL
);