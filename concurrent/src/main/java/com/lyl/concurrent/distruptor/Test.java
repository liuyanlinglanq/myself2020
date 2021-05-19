package com.lyl.concurrent.distruptor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) throws IOException {
        Consumer<StringEvent>[] consumers = new Consumer[] {
            new StringConsumer(), new StringConsumer()
        };
        Producer<StringEvent, String>[] producers = new Producer[] {
                new StringProducer(), new StringProducer()
        };
        AnotherDistruptor<StringEvent, String> distruptor = new AnotherDistruptor<>(
                4096, producers, consumers, new StringEventFactory(),
                new BlockStrategy());
        List<String> lines = Files.readAllLines(Paths.get("/Users/liuyanling" +
                "/Downloads/f2.py"));
        Random random = new Random();
        distruptor.start();
        for (String line : lines) {
            producers[random.nextInt(2)].produce(line);
        }
        distruptor.stop();
    }
}