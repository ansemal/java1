package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HeapForThreadsTest {
    static final int maxSize = 1932735283;
    static final int maxSmall = 10;
    static final int maxMedium = 100;
    static final int maxBig = 1000;
    static final int maxHuge = 10000;
    static final int THREADS = 3;

    static AtomicInteger count = new AtomicInteger(0);
    static AtomicInteger allocTime = new AtomicInteger(0);
    static AtomicInteger freeTime = new AtomicInteger(0);
    static AtomicInteger allocated = new AtomicInteger(0);
    static AtomicInteger allocWork = new AtomicInteger(0);
    static AtomicInteger freeWork = new AtomicInteger(0);

    static long lstartAlloc = 0;
    static long lstartFree = 0;

    static class Block {
        public int ptr;
        public int size;

        public Block(int ptr, int size) {
            this.ptr = ptr;
            this.size = size;
        }
    }

    static int getRandomSize(int allocated) {
        int n = Math.abs(ThreadLocalRandom.current().nextInt()%10);
        int size = Math.abs(ThreadLocalRandom.current().nextInt());
        if (n < 6)
            size %= maxSmall;
        else if (n < 8)
            size %= maxMedium;
        else if (n < 9)
            size %= maxBig;
        else
            size %= maxHuge;
        return size > (maxSize-allocated)-1 ? (maxSize-allocated)/2+1 : size+1;
    }

    public static void main(String[] args) throws ru.progwards.java2.lessons.gc.InvalidPointerException, OutOfMemoryException {
        HeapForThreads heapForThreads = new HeapForThreads(maxSize, THREADS);
        long start = System.currentTimeMillis();
        ConcurrentLinkedQueue<Block> blocks = new ConcurrentLinkedQueue<>();

        ExecutorService eservice = Executors.newFixedThreadPool(THREADS);
        CountDownLatch countDownLatch = new CountDownLatch(THREADS);
        for (int i=0; i<THREADS; i++) {
            eservice.submit(new Runnable() {
                @Override
                public void run() {
                    while ((maxSize - allocated.intValue()) > 50000) {
                        int size = getRandomSize(allocated.intValue());
                        allocated.addAndGet(size);
                        count.incrementAndGet();
                        if (allocWork.incrementAndGet() == 1)
                            lstartAlloc = System.currentTimeMillis();
                        int ptr = heapForThreads.malloc(size);
                        if (allocWork.decrementAndGet() == 0)
                            allocTime.addAndGet((int) (System.currentTimeMillis() - lstartAlloc));
                        blocks.offer(new Block(ptr, size));
                        int n = Math.abs(ThreadLocalRandom.current().nextInt() % 25);
                        if (n == 0) {
                            //n = Math.abs(ThreadLocalRandom.current().nextInt()%blocks.size());
                            for (int i = 0; i < 5; i++) {
                                Block block = blocks.poll();
                                if (block == null)
                                    break;
                                if (freeWork.incrementAndGet() == 1)
                                    lstartFree = System.currentTimeMillis();
                                heapForThreads.free(block.ptr);
                                if (freeWork.decrementAndGet() == 0)
                                    freeTime.addAndGet((int) (System.currentTimeMillis() - lstartFree));
                                allocated.addAndGet(-block.size);
                            }
                            //blocks.remove(n);
                        }
                        n = Math.abs(ThreadLocalRandom.current().nextInt() % 100000);
                        if (n == 0)
                            System.out.println(maxSize - allocated.intValue() + " " + Thread.currentThread().getName());
                    }
                    countDownLatch.countDown();
                }
            });
        }
        eservice.shutdown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long stop = System.currentTimeMillis();
        System.out.println("malloc time: "+allocTime+" free time: "+freeTime);
        System.out.println("total time: "+(stop-start)+" count: "+count);
        eservice.shutdown();

    }

}

