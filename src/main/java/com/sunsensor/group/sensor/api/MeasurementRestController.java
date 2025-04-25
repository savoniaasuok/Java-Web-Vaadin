package com.sunsensor.group.sensor.api;

import com.sunsensor.group.sensor.domain.Measurement;
import com.sunsensor.group.sensor.domain.Sensor;
import com.sunsensor.group.sensor.service.MeasurementService;
import com.sunsensor.group.sensor.service.SensorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementRestController {

    private final MeasurementService measurementService;
    private final SensorService sensorService;

    public MeasurementRestController(MeasurementService measurementService, SensorService sensorService) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @GetMapping
    public List<Measurement> getAllMeasurements(@RequestParam(required = false) Long sensorId) {
        if (sensorId != null) {
            Sensor sensor = sensorService.findAll().stream()
                    .filter(s -> s.getId().equals(sensorId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));
            return measurementService.findAll().stream()
                    .filter(m -> m.getSensor().equals(sensor))
                    .toList();
        }
        return measurementService.findAll();
    }

    @PostMapping
    public Measurement createMeasurement(@RequestBody Measurement measurement) {
        return measurementService.save(measurement);
    }

    @DeleteMapping("/{id}")
    public void deleteMeasurement(@PathVariable Long id) {
        measurementService.delete(id);
    }
}
