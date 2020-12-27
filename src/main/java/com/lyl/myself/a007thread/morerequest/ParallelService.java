package com.lyl.myself.a007thread.morerequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 同时处理多个请求,这是请求的服务,
 * 服务A,需要1s;
 * 服务B,需要2s
 * 服务C,需要2.5s 改为10s,超时
 */
@Slf4j
@Service
public class ParallelService {

    public String requestA() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            log.info("requestA被打断");
        }
        return "A";
    }

    public String requestB() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            log.info("requestB被打断");
        }
        return "B";
    }

    public String requestC() {
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            log.info("requestC被打断");
        }
        return "C";
    }

}