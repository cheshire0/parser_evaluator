package org.parser.evaluator.util;

import org.parser.evaluator.testers.MemoryTest;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemorySampler implements Runnable {

    private volatile boolean running = true;
    private long maxMemoryUsed = 0;
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    @Override
    public void run() {
        while (running) {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            long currentMemory = heapMemoryUsage.getUsed();
            maxMemoryUsed = Math.max(maxMemoryUsed, currentMemory);
            /*
            try {
                Thread.sleep(1); // Sample every 1ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            */
            busySleep(10);
        }
    }

    public void busySleep(long nanos)
    {
        long elapsed;
        final long startTime = System.nanoTime();
        do {
            elapsed = System.nanoTime() - startTime;
        } while (elapsed < nanos);
    }

    public void stopSampling() {
        running = false;
    }

    public long getMaxMemoryUsed() {
        return maxMemoryUsed;
    }
}

