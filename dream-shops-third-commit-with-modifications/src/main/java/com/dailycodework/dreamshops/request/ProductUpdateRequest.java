package com.dailycodework.dreamshops.request;

import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Vehicle;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private Vehicle vehicle;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private String brand;
}
