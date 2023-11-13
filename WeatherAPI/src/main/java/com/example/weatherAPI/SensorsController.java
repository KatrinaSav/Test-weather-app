package com.example.weatherAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/sensors")
public class SensorsController {
    private final SensorService sensorService;
    @Autowired
    public SensorsController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping("")
    public ResponseEntity<?> postSensor(@RequestBody Sensor sensor){
        return sensorService.addSensor(sensor);
    }

    @GetMapping("")
    public List<Sensor> getActiveSensors(){
        return sensorService.getActiveSensors();
    }

    @GetMapping("/{key}/measurements")
    public List<Record> getLatestRecords(@PathVariable("key")String key){
        return sensorService.getLatestRecords(key);
    }

    @GetMapping("/measurements")
    public List<Record> getActualRecords(){
        return sensorService.getActualRecords();
    }

    @PostMapping("/{key}/measurements")
    public ResponseEntity<?> addRecord(@PathVariable("key")String key, @RequestBody Record record){
         return sensorService.addRecord(key,record);
    }


}
