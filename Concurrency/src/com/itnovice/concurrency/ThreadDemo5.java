package com.itnovice.concurrency;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadDemo5 {
    public static void main(String[] args) {
        ExecutorService ex = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3"
        );

        System.out.println("Invoke all callables ...");
        try {
            ex.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    }).forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Invoke any callables ...");
        try {
            String result = ex.invokeAny(callables);
            System.out.println(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
