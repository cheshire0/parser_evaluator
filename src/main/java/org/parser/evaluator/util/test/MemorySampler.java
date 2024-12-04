package org.parser.evaluator.util.test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemorySampler implements Runnable {

    // Flag to control the sampling loop
    private volatile boolean running = true;
    // Variable to track the maximum memory used during sampling
    private long maxMemoryUsed = 0;
    // MemoryMXBean provides memory-related management information
    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    /**
     * The run method continuously monitors memory usage in a separate thread.
     * It tracks the heap and non-heap memory usage, updating the maximum memory used.
     */
    @Override
    public void run() {
        // Loop to continuously sample memory usage while the 'running' flag is true
        while (running) {
            // Get current heap and non-heap memory usage
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

            // Get the current memory used by the heap
            long currentMemory = heapMemoryUsage.getUsed();
            // Update the maximum memory used if the current usage exceeds it
            maxMemoryUsed = Math.max(maxMemoryUsed, currentMemory);

            // Sleep for a short time to reduce CPU usage during sampling
            busySleep(10);
        }
    }

    /**
     * A busy-wait method that keeps the thread alive for the specified number of nanoseconds.
     * This method helps in controlling the frequency of memory sampling.
     *
     * @param nanos The number of nanoseconds to wait
     */
    public void busySleep(long nanos) {
        long elapsed;
        final long startTime = System.nanoTime();
        // Busy-wait loop that ensures the thread sleeps for the specified duration
        do {
            elapsed = System.nanoTime() - startTime;
        } while (elapsed < nanos);
    }

    /**
     * Stops the memory sampling loop.
     * Once this method is called, the sampling thread will stop running.
     */
    public void stopSampling() {
        running = false;
    }

    /**
     * Retrieves the maximum memory used during the sampling process.
     *
     * @return The maximum memory used (in bytes)
     */
    public long getMaxMemoryUsed() {
        return maxMemoryUsed;
    }
}
