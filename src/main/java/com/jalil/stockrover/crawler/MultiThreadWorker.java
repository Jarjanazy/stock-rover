package com.jalil.stockrover.crawler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class MultiThreadWorker
{
    public static void runAsyncJobInParallel(int numOfThreads, Runnable job)
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool(numOfThreads);

        CompletableFuture.runAsync(job, forkJoinPool);
    }
}
