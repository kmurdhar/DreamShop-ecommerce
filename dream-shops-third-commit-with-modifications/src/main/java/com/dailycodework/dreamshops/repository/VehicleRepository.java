package com.dailycodework.dreamshops.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.model.Vehicle; 

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	  
	List<Vehicle> findByName(String name);
	List<Vehicle> findByVehicleBrand(String vehicleBrand);
	List<Vehicle> findByVehicleModel(String vehicleModel);
	Vehicle findByNameAndVehicleBrandAndVehicleModel(String name,String vehicleBrand,String vehicleModel);
  boolean existsByVehicleBrand(String vehicleBrand);
  boolean existsByName(String name);
  boolean existsByVehicleModel(String vehicleModel);
  boolean existsByVehicleBrandAndVehicleModel(String vehicleBrand,String vehicleModel);
  boolean existsByNameAndVehicleBrandAndVehicleModel(String name,String vehicleBrand,String vehicleModel);
}
