package com.example.weatherAPI;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="sensor")
public class Sensor {
    @Id
    @SequenceGenerator(name = "sensor_sequence",sequenceName = "sensor_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sensor_sequence")
    private Long sensorId;
    private String name;
    private Boolean isActive;

    private String key;

    @OneToMany(mappedBy="sensor")
    @JsonIgnore
    private Set<Record> records;


    public Sensor() {
    }
    public Sensor(Long id, String name, String key) {
        this.sensorId = id;
        this.name = name;
        this.key = key;
    }

    public Sensor(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }



    public Long getId() {
        return sensorId;
    }

    public void setId(Long id) {
        this.sensorId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
