package com.sunsensor.group.sensor.service;

import com.sunsensor.group.sensor.domain.Sensor;
import com.sunsensor.group.sensor.domain.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private final SensorRepository repository;

    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }

    public List<Sensor> findAll() {
        return repository.findAll();
    }

    public Sensor save(Sensor sensor) {
        return repository.save(sensor);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
