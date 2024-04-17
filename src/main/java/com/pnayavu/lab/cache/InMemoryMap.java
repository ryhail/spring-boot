package com.pnayavu.lab.cache;

import com.pnayavu.lab.logging.Logged;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InMemoryMap {
  private static final int INIT_CAPACITY = 50;
  private final Map<String, Object> cache;

  public InMemoryMap() {
    this.cache = new LinkedHashMap<>() {
      @Override
      protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
        return size() > INIT_CAPACITY;
      }
    };
  }

  @Logged
  public void put(String key, Object value) {
    cache.put(key, value);
  }

  @Logged
  public Object get(String key) {
    return cache.get(key);
  }

  public boolean containsKey(String key) {
    return cache.containsKey(key);
  }

  public void remove(String key) {
    cache.remove(key);
  }

  public int size() {
    return cache.size();
  }
}