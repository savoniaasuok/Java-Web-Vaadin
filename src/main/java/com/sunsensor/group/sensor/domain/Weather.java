package com.sunsensor.group.sensor.domain;

import com.sunsensor.group.base.domain.AbstractEntity;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity
@Table(name = "weather")
public class Weather extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double uvIndex;
    private double cloudinessPercent;

    @Override
    public @Nullable Long getId() {
        return id;
    }

    public double getUvIndex() { return uvIndex; }
    public void setUvIndex(double uvIndex) { this.uvIndex = uvIndex; }

    public double getCloudinessPercent() { return cloudinessPercent; }
    public void setCloudinessPercent(double cloudinessPercent) { this.cloudinessPercent = cloudinessPercent; }
}
