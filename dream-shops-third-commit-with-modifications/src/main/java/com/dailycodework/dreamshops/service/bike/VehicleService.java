package com.dailycodework.dreamshops.service.bike;

import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Vehicle;
import com.dailycodework.dreamshops.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {
    private final VehicleRepository vehicleRepository;
    
    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Vehicle not found!"));
    }

   
    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        return  Optional.of(vehicle).filter( c -> !(vehicleRepository.existsByName(c.getName()) 
        		&& vehicleRepository.existsByVehicleBrand(c.getVehicleBrand()) 
        		&& vehicleRepository.existsByVehicleModel(c.getVehicleModel())   ))
                .map(vehicleRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(vehicle.getVehicleModel()+" already exists"));
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle, Long id) {
        return Optional.ofNullable(getVehicleById(id)).map(oldVehicle -> {
        	oldVehicle.setName(vehicle.getName());
        	oldVehicle.setVehicleBrand(vehicle.getVehicleBrand());
        	oldVehicle.setVehicleModel(vehicle.getVehicleModel());
            return vehicleRepository.save(oldVehicle);
        }) .orElseThrow(()-> new ResourceNotFoundException("Vehicle not found!"));
    }


    @Override
    public void deleteVehicleById(Long id) {
        vehicleRepository.findById(id)
                .ifPresentOrElse(vehicleRepository::delete, () -> {
                    throw new ResourceNotFoundException("Vehicle not found!");
                });

    }

	@Override
	public List<Vehicle> getVehicleByName(String name) {
	    return vehicleRepository.findByName(name);
	}

	@Override
	public List<Vehicle> getVehicleBrandByName(String name) {
	    return vehicleRepository.findByVehicleBrand(name);
	}
	
	@Override
	public List<Vehicle> getVehicleModelByName(String name) {
	    return vehicleRepository.findByVehicleModel(name);
	}

}
