package com.sunsensor.group.sensor.api;

import com.sunsensor.group.sensor.domain.Sensor;
import com.sunsensor.group.sensor.service.SensorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorRestController {

    private final SensorService service;

    public SensorRestController(SensorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sensor> getAllSensors() {
        return service.findAll();
    }

    @PostMapping
    public Sensor createSensor(@RequestBody Sensor sensor) {
        return service.save(sensor);
    }

    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable Long id) {
        service.delete(id);
    }
}
