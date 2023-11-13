package com.example.weatherAPI;

import jakarta.persistence.*;

@Entity
@Table
public class Record {
    @Id
    @SequenceGenerator(name = "record_sequence",sequenceName = "record_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "record_sequence")
    private Long id;
    private Float value;
    private Boolean raining;
    private long time;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sensor_id", nullable=false)
    private Sensor sensor;

    public long getTime() {
        return time;
    }


    public void setTime(long time) {
        this.time = time;
    }


    public Record() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
