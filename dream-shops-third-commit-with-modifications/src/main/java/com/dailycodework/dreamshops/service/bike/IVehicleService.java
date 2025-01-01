package com.dailycodework.dreamshops.service.bike;

import com.dailycodework.dreamshops.model.Vehicle;
import com.dailycodework.dreamshops.model.Category;

import java.util.List;

public interface IVehicleService {
	
    Vehicle getVehicleById(Long id);
    Vehicle getVehicleByName(String name);
    Vehicle getVehicleBrandByName(String vehicleBrand);
    Vehicle getVehicleModelByName(String vehicleModel);
    List<Vehicle> getAllVehicles();
    Vehicle addVehicle(Vehicle bike);
    Vehicle updateVehicle(Vehicle bike, Long id);
    void deleteVehicleById(Long id);

}
