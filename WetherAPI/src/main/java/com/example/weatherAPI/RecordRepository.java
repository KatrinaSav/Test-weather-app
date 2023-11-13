package com.example.weatherAPI;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record,Long> {
List<Record> findFirst20BySensorOrderByTimeDesc(Sensor sensor);

    Optional<Record> findFirst1BySensorOrderByTimeDesc(Sensor sensor);


    @Query(value="SELECT * FROM \"record\" WHERE :current_time-\"time\" <=60000", nativeQuery = true)
    List<Record> findActualRecords(@Param("current_time") long current_time);
}
