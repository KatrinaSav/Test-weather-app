package com.example.weatherAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final RecordRepository recordRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository, RecordRepository recordRepository) {
        this.sensorRepository = sensorRepository;
        this.recordRepository = recordRepository;
    }
    @Scheduled(fixedDelay = 30000)
    public void checkActivity(){
        Date currentTime = new Date();
        List<Sensor> activeSensors = sensorRepository.findAllByIsActive(true);
        for (Sensor sensor: activeSensors) {
            Optional<Record> latestRecord = recordRepository.findFirst1BySensorOrderByTimeDesc(sensor);
            if(latestRecord.isPresent()) {
                if (currentTime.getTime() - latestRecord.get().getTime() > 60000) {
                    sensor.setActive(false);
                    sensorRepository.save(sensor);
                }
            }
        }
    }

    public ResponseEntity<?> addSensor(Sensor sensor) {
        if(sensorRepository.findByName(sensor.getName()).isPresent())
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(),
                    "Sensor with name '"+sensor.getName() +"' already exist"),
                    HttpStatus.BAD_REQUEST);
        if(sensor.getName().isBlank() || 30 < sensor.getName().length() || sensor.getName().length() < 3)
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(),"Illegal name"),
                    HttpStatus.BAD_REQUEST);
        String uuidAsString = UUID.randomUUID().toString();
        sensor.setKey(uuidAsString);
        sensor.setActive(false);
        sensorRepository.save(sensor);
        return new ResponseEntity<>(Map.of("key",uuidAsString), HttpStatus.OK);
    }

    public ResponseEntity<?> addRecord(String key, Record record){
        if(record.getRaining() == null || record.getValue() ==null)
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(),
                    "Empty value"),
                    HttpStatus.BAD_REQUEST);
        if(record.getValue()>100 || record.getValue()<-100)
            return new ResponseEntity<>(new CustomError(HttpStatus.BAD_REQUEST.value(),
                    "Invalid value field 'value'"),
                    HttpStatus.BAD_REQUEST);
        try{
            Sensor sensor = sensorRepository.findByKey(key).get();
            sensor.setActive(true);
            record.setSensor(sensor);
            Date date = new Date();
            record.setTime(date.getTime());
            recordRepository.save(record);
        }
        catch (Exception e){
            return new ResponseEntity<>(new CustomError(HttpStatus.NOT_FOUND.value(),
                    "Sensor with such a key is not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Sensor> getActiveSensors(){
        return sensorRepository.findAllByIsActive(true);
    }

    public List<Record> getLatestRecords(String key){
        return recordRepository.findFirst20BySensorOrderByTimeDesc(sensorRepository.findByKey(key).get());
    }

    public List<Record> getActualRecords(){
        Date currentTime = new Date();
        return recordRepository.findActualRecords(currentTime.getTime());
    }
}
