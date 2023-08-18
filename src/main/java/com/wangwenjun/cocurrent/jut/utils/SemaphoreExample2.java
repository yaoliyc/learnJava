package com.wangwenjun.cocurrent.jut.utils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Created on 2023/8/18.
 *
 * @author DESKTOP-7BFIUE4
 */
public class SemaphoreExample2 {

    public static void main(String[] args) {

        final TryLock tryLock = new TryLock();
        new Thread(()->{
            boolean gotLock = tryLock.tryLock();
            if (!gotLock){
                System.out.println(currentThread()+" cant't get the lock, will do other thing.");
                return;
            }
            try {
                simulateWork();
            } finally {
                tryLock.unlock();
            }
        }).start();

        boolean gotLock = tryLock.tryLock();
        if (!gotLock){
            System.out.println(currentThread()+" cant't get the lock, will do other thing.");
        } else {
            try {
                simulateWork();
            } finally {
                tryLock.unlock();
            }
        }
    }

    private static void simulateWork() {
        try {
            System.out.println(currentThread()+" get the lock and do working.");
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TryLock {
        private final Semaphore semaphore = new Semaphore(1);

        public boolean tryLock(){
            return semaphore.tryAcquire();
        }

        public void unlock(){
            semaphore.release();
            System.out.println(currentThread()+" release lock");
        }
    }



}
