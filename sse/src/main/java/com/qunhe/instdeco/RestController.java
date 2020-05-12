package com.qunhe.instdeco;

import io.netty.util.internal.ConcurrentSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shengxun
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private Set<SseEmitter> sseEmitters = new HashSet<>();

    private Lock mLock = new ReentrantLock();

    private int messageCount = 0;

    @GetMapping(value = "/sse", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter sseResponse() throws Exception {
        final SseEmitter sseEmitter = new SseEmitter();
        mLock.lock();
        try {
            sseEmitters.add(sseEmitter);
        } finally {
            mLock.unlock();
        }
        return sseEmitter;
    }

    @Scheduled(fixedDelay = 2000)
    public void scheduledMsgEmitter() throws IOException {
        mLock.lock();
        try {
            System.out.println("xx");
            if (!sseEmitters.isEmpty()) {
                ++messageCount;
            } else {
                System.out.println("No active Emitters ");
            }

            System.out.println("Sent Messages : " + messageCount);
            sseEmitters.forEach(emitter -> {
                if (emitter != null) {
                    try {
                        System.out.println("Timeout : " + emitter.getTimeout());
                        emitter.send("MessageCounter : " + messageCount);
                        //emitter.complete();
                        //sseEmitters.remove(emitter);
                    } catch (Exception e) {
                        sseEmitters.remove(emitter);
                    }
                }
            });
        } finally {
            mLock.unlock();
        }
    }
}
