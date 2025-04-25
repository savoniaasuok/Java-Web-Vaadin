package com.sunsensor.group.sensor.service;

import com.sunsensor.group.sensor.domain.Measurement;
import com.sunsensor.group.sensor.domain.MeasurementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    private final MeasurementRepository repository;

    public MeasurementService(MeasurementRepository repository) {
        this.repository = repository;
    }

    public List<Measurement> findAll() {
        return repository.findAll();
    }

    public Optional<Measurement> findById(Long id) {
        return repository.findById(id);
    }

    public Measurement save(Measurement measurement) {
        return repository.save(measurement);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
