package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    public static final int ARRAYS_NUMBER = 10;
    public static final int ARRAY_SIZE_BOUND = 100;
    public static final int ARRAYS_VALUE_BOUND = 200;


    public static void main(String[] args) throws InterruptedException {

        List<List<Long>> arrays = new ArrayList<>();
        for (int i = 0; i < ARRAYS_NUMBER; i++) {
            List<Long> list = new ArrayList<>();
            for (int j = 0; j < (int) (ARRAY_SIZE_BOUND * Math.random()); j++) {
                list.add((long) (ARRAYS_VALUE_BOUND * Math.random()));
            }
            arrays.add(list);
        }

        LongAdder totalMoney = new LongAdder();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (List<Long> list : arrays) {
            executorService.submit(
                    () -> {
                        Long storeMoney = list.stream()
                                .mapToLong(x -> x)
                                .sum();
                        System.out.println("Store № " + (arrays.indexOf(list) + 1) + " day revenue : " + storeMoney);
                        totalMoney.add(storeMoney);
                    }
            );
        }
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Total day revenue: " + totalMoney.sum());
        executorService.shutdown();
    }
}
