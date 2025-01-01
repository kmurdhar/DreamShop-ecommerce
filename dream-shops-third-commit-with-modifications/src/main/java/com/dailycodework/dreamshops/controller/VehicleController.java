package com.dailycodework.dreamshops.controller;


import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Vehicle;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.bike.IVehicleService;
import com.dailycodework.dreamshops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/vehicle")
public class VehicleController {
	
    private final IVehicleService vehicleService; 

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllVehicles() {
        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicles();
            return  ResponseEntity.ok(new ApiResponse("Found!", vehicles));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addVehicle(@RequestBody Vehicle name) {
        try {
        	Vehicle theVehicle = vehicleService.addVehicle(name);
            return  ResponseEntity.ok(new ApiResponse("Success", theVehicle));
        } catch (AlreadyExistsException e) {
           return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/vehicle/{id}/vehicle")
    public ResponseEntity<ApiResponse> getVehicleById(@PathVariable Long id){
        try {
        	Vehicle theVehicle = vehicleService.getVehicleById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", theVehicle));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/vehicle/{name}/name")
    public ResponseEntity<ApiResponse> getVehicleByName(@PathVariable String name){
        try {
        	Vehicle theVehicle = vehicleService.getVehicleByName(name);
            return  ResponseEntity.ok(new ApiResponse("Found", theVehicle));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @GetMapping("/vehicle/{vehicleBrand}/vehicleBrand")
    public ResponseEntity<ApiResponse> getVehicleBrandByName(@PathVariable String vehicleBrand){
        try {
        	Vehicle theVehicle = vehicleService.getVehicleBrandByName(vehicleBrand);
            return  ResponseEntity.ok(new ApiResponse("Found", theVehicle));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    
    @GetMapping("/vehicle/{vehicleModel}/vehicleModel")
    public ResponseEntity<ApiResponse> getVehicleModelByName(@PathVariable String vehicleModel){
        try {
        	Vehicle theVehicle = vehicleService.getVehicleModelByName(vehicleModel);
            return  ResponseEntity.ok(new ApiResponse("Found", theVehicle));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/vehicle/{id}/delete")
    public ResponseEntity<ApiResponse> deleteVehicle(@PathVariable Long id){
        try {
        	vehicleService.deleteVehicleById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/vehicle/{id}/update")
    public ResponseEntity<ApiResponse> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        try {
        	Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle, id);
            return ResponseEntity.ok(new ApiResponse("Update success!", updatedVehicle));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
