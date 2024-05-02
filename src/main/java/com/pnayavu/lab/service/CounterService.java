package com.pnayavu.lab.service;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterService {
  private static final AtomicInteger counter = new AtomicInteger(0);

  public static synchronized int increment() {
    return counter.incrementAndGet();
  }
}
