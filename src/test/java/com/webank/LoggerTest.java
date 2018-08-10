package com.webank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1() {
        /*
        系统的默认日志级别：info
        1、级别由高到低：error,warn,info,debug,trace
        1、日志级别越高，事态越严重
         */
        logger.debug("debug...");
        logger.info("info...");
        logger.error("error...");
    }
}
