package com.dailycodework.dreamshops.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.model.Vehicle; 

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	  
	Vehicle findByName(String name);
	Vehicle findByVehicleBrand(String vehicleBrand);
	Vehicle findByVehicleModel(String vehicleModel);
	Vehicle findByNameAndVehicleBrandAndVehicleModel(String name,String vehicleBrand,String vehicleModel);
  boolean existsByVehicleBrand(String vehicleBrand);
  boolean existsByName(String name);
  boolean existsByVehicleModel(String vehicleModel);
  boolean existsByVehicleBrandAndVehicleModel(String vehicleBrand,String vehicleModel);
  boolean existsByNameAndVehicleBrandAndVehicleModel(String name,String vehicleBrand,String vehicleModel);
}
