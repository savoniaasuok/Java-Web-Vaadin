package com.sunsensor.group.user.domain;

import com.sunsensor.group.base.domain.AbstractEntity;
import com.sunsensor.group.sensor.domain.Sensor;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // esim. "USER", "ADMIN"

    @ManyToMany
    @JoinTable(name = "user_sensor",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id"))
    private Set<Sensor> sensors = new HashSet<>();

    @Override
    public @Nullable Long getId() {
        return id;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Set<Sensor> getSensors() { return sensors; }
    public void setSensors(Set<Sensor> sensors) { this.sensors = sensors; }
}
