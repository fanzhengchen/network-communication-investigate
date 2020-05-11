package com.qunhe.instdeco;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author shengxun
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {
    private SseEmitter event = new SseEmitter();

    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    event.send(SseEmitter.event().data("sse data"));
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @GetMapping(value = "/sse", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter sseResponse() throws Exception {

        event.send(SseEmitter.event().reconnectTime(10000L).id("123"));
        if (mThread.getState() == Thread.State.NEW) {
            mThread.start();
        }
        return event;
    }
}
