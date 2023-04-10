package com.musala.musalatest.business.dao;

import com.musala.musalatest.business.enums.DroneModel;
import com.musala.musalatest.business.enums.DroneState;
import com.musala.musalatest.business.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone,Long> {

    Optional<Drone> findDroneBySerialNumber(@Param("serialNumber") String serialNumber);

    Optional<Drone> findDroneByModel(@Param("model") DroneModel model);

    List<Drone> findAllByStateEquals(@Param("IDLE") DroneState state);
}
