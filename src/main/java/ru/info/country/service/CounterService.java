package ru.info.country.service;

public interface CounterService {
    void increment(String service, Integer version, boolean cached);
}
