package com.sunsensor.group.sensor.domain;

import com.sunsensor.group.base.domain.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "measurement")
public class Measurement extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Sensor sensor;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    private double lux;
    private double temperature;
    private double humidity;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Weather weather;

    private double latitude;
    private double longitude;

    @Override
    public @Nullable Long getId() {
        return id;
    }

    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public double getLux() { return lux; }
    public void setLux(double lux) { this.lux = lux; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public Weather getWeather() { return weather; }
    public void setWeather(Weather weather) { this.weather = weather; }
}
