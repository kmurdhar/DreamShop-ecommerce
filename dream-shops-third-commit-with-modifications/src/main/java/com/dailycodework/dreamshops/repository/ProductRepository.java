package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Locale.Category;
import java.util.stream.Collectors;

public interface ProductRepository extends JpaRepository<Product, Long> {
 
   

    //List<Product> findByCategoryAndVehicle(Category category,  Vehicle vehicle);
    @Query(nativeQuery = true, value = "SELECT p FROM Product p where p.category_id=:categoryId")
    List<Product>  findByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query(nativeQuery = true, value = "SELECT * FROM product  where vehicle_id=:vehicleid")
    List<Product>  findByVehicle(@Param("vehicleid")  Long vehicleid);
    
    
    @Query(nativeQuery = true, value = "SELECT * FROM product  where vehicle_id=:vehicleid and category_id=:categoryId")
    List<Product>  checkProductExist(@Param("categoryId")  Long categoryId,@Param("vehicleid")  Long vehicleid);
  
  
    List<Product> findByBrand(String brand);
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "select * from product where category_id in (:categoryId) ")
    List<Product>  findAllByCategoryId(@Param("categoryId")  List<Long>  categoryId);
    
    @Query(nativeQuery = true, value = "select * from product where vehicle_id in (:vehicleId) ")
    List<Product>  findAllByVehicleId(@Param("vehicleId")  List<Long>  vehicleId);
    
    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);

  //  boolean existsByNameAndBrand(String name, String brand);
    
}
