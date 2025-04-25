package com.sunsensor.group.sensor.service;

import com.sunsensor.group.sensor.domain.Weather;
import com.sunsensor.group.sensor.domain.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository) {
        this.repository = repository;
    }

    public List<Weather> findAll() {
        return repository.findAll();
    }

    public Optional<Weather> findById(Long id) {
        return repository.findById(id);
    }

    public Weather save(Weather weather) {
        return repository.save(weather);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
