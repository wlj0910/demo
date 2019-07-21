package com.company;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class MyThread extends Thread{
    private int tid;
    public MyThread(int tid){
        this.tid=tid;
    }
    @Override
    public void run() {
        try{

            for(int i=0;i<10;i++){
                Thread.sleep(1000);
                System.out.println(String.format("t0 id=%d:i=%d",tid,i));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class MyThread1 implements Runnable{
    private int tid;
    public MyThread1(int tid){
        this.tid=tid;
    }

    @Override
    public void run() {
        try{
            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                System.out.println(String.format("t0 id=%d:i=%d",tid,i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class MyThread2 implements Callable<Object>{
    private int tid;
    public MyThread2(int tid){
        this.tid=tid;
    }
    @Override
    public Object call() throws Exception {
        try {
            for (int j = 0; j < 10; j++) {
                //Thread.sleep(1000);
                //System.out.println(String.format("t0 id=%d:j=%d",tid,j));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.valueOf(tid);
    }
}

class Consumer implements Runnable{
    private BlockingQueue<String> q;
    public Consumer(BlockingQueue<String> q){
        this.q=q;
    }

    @Override
    public void run() {
        try{
            while(true){
                System.out.println(Thread.currentThread().getName()+":"+q.take());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable{
    private BlockingQueue<String> q;
    public Producer(BlockingQueue<String> q){
        this.q=q;
    }

    @Override
    public void run() {
        try{
            for(int i=0;i<10;i++){
                Thread.sleep(1000);
                q.put(String.valueOf(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class MultiThreadTests {
    public static void testThread(){
        for(int i=0;i<10;i++){
            //new MyThread(i).start();
            //new Thread(new MyThread1(i),"Runnable").start();
            //new Thread(new FutureTask<Object>(new MyThread2(i)),"Callable").start();

            //获取MyThread2（实现Callable接口）的返回值
            // FutureTask<Object> 封装了new MyThread2(i)（Callable对象）中call()方法的返回值
            FutureTask<Object> futureTask= new FutureTask<Object>(new MyThread2(i));
            new Thread(futureTask).start();
            try{
                System.out.println(futureTask.get());
                //future.get(); 等待直到计算完成
                //System.out.println(future.get(100,TimeUnit.MILLISECONDS));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        for(int i=0;i<10;i++){
           //内部类访问：必须final类型
            final int finalI=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{

                        for(int j=0;j<10;j++){
                            //Thread.sleep(1000);
                            //System.out.println(String.format("t1 id=%d:j=%d",finalI,j));
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for(int i=0;i<10;i++){
            //内部类访问：必须final类型
            final int finalI=i;
            new Thread(new FutureTask<Object>(new Callable<Object>() {
                @Override
                public Integer call() {
                    try {
                        for (int j = 0; j < 10; j++) {
                            //Thread.sleep(1000);
                            //System.out.println(String.format("t0 id=%d:j=%d", finalI, j));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return Integer.valueOf(finalI);
                }
            })).start();
        }
    }
    //对象锁必须是同一对象
    private static Object obj=new Object();

    private static void testSynchronized1(){
        synchronized(obj){
            try{

                for(int i=0;i<10;i++){
                    //Thread.sleep(1000);
                    System.out.println(String.format("t2 id=%d",i));
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void testSynchronized2(){
        //synchronized(new Object())   对象锁不是同一个对象
        synchronized(obj){
            try{

                for(int i=0;i<10;i++){
                    //Thread.sleep(1000);
                    System.out.println(String.format("t3 id=%d",i));
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //方法锁
    private static synchronized void testSynchronized3(){
        try{
            for(int i=0;i<10;i++){
                //Thread.sleep(1000);
                System.out.println(String.format("t4 id=%d",i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static synchronized void testSynchronized4(){
        try{
            for(int i=0;i<10;i++){
                //Thread.sleep(1000);
                System.out.println(String.format("t5 id=%d",i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //不加锁
    private static void testSynchronized5(){
        try{
            for(int i=0;i<10;i++){
                //Thread.sleep(1000);
                System.out.println(String.format("t6 id=%d",i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void testSynchronized6(){
        try{
            for(int i=0;i<10;i++){
                //Thread.sleep(1000);
                System.out.println(String.format("t7 id=%d",i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void testSynchronized() {
         for(int i=0;i<10;i++){
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     //testSynchronized1();
                     //testSynchronized2();
                     testSynchronized3();
                     testSynchronized4();
                     //testSynchronized5();
                     //testSynchronized6();
                 }
             }).start();
         }
    }

    public static void testBlockingQueue(){
        BlockingQueue<String> q=new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q),"Consumer1").start();
        new Thread(new Consumer(q),"Consumer2").start();
    }

    public static int count=0;
    public static AtomicInteger atomicInteger=new AtomicInteger(0);
    public static void testWithoutAtomic(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        for(int j=0;j<10;j++){
                            count++;
                            System.out.println("count:" + count);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testWithAtomic(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Thread.sleep(10);
                        for(int j=0;j<10;j++){
                            System.out.println("atomicInteger:" + atomicInteger.incrementAndGet());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testAtomic(){
        testWithoutAtomic();
        //testWithAtomic();
    }

    private static int userId;
    private static ThreadLocal<Integer> threadLocalId=new ThreadLocal<>();
    public static void testThreadLocal(){
        for (int i = 0; i < 10; i++) {
            //内部类访问：必须final类型
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        threadLocalId.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocal:" + threadLocalId.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        for (int i = 0; i < 10; i++) {
            //内部类访问：必须final类型
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        userId = finalI;
                        Thread.sleep(1000);
                        System.out.println("userId:" + userId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void testExecutor(){
        //ExecutorService service= Executors.newSingleThreadExecutor();
        ExecutorService service=Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    try {
                        Thread.sleep(10);
                        System.out.println("Excutor1:" + i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    try {
                        Thread.sleep(10);
                        System.out.println("Excutor2:" + i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        service.shutdown();//前面提交的任务执行完后关闭，新的任务不被接收
       //service.shutdownNow() 结束所有的任务，返回等待执行的任务列表
        while(!service.isTerminated()){
            try{
                Thread.sleep(10);
                System.out.println("Wait for terminated.");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testFuture(){
        ExecutorService service=Executors.newSingleThreadExecutor();
        Future<Integer> future=service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                throw new IllegalArgumentException("异常");
                //return 1;
            }
        });
        service.shutdown();
        //future.get(); 阻塞  future结果还没返回
        try{
            System.out.println(future.get());
            //future.get(); 等待直到计算完成
            //System.out.println(future.get(100,TimeUnit.MILLISECONDS));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        testThread();
        //testSynchronized();
        //testBlockingQueue();
        //testThreadLocal();
        //testExecutor();
        //testAtomic();
        //testFuture();
    }
}
