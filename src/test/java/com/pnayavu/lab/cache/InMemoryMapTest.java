package com.pnayavu.lab.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryMapTest  {
  private InMemoryMap cache;
  private static final String key = "Test key";
  private static final String object = "Object";
  @BeforeEach
  void initCache() {
    cache = new InMemoryMap();
  }
  @Test
  void testPutAndContains(){
    cache.put(key, object);
    Assertions.assertTrue(cache.containsKey(key));
  }
  @Test
  void testGet(){
    cache.put(key, object);
    Assertions.assertEquals("Object", cache.get(key));
  }
  @Test
  void testRemove() {
    cache.put(key, object);
    cache.remove(key);
    Assertions.assertFalse(cache.containsKey(key));
  }
  @Test
  void testSize() {
    for (int i = 0; i < 34; i++) {
      cache.put(key + ' ' + i, object + ' ' + i);
    }
    Assertions.assertEquals(34, cache.size());
  }
  @Test
  void testAutoRemove() {
    for (int i = 0; i < 60; i++) {
      cache.put(key + ' ' + i, object + ' ' + i);
    }
    Assertions.assertEquals(50, cache.size());
    Assertions.assertFalse(cache.containsKey(key + ' ' + 9));
  }
}
