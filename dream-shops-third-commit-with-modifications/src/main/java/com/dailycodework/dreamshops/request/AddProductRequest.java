package com.dailycodework.dreamshops.request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Vehicle;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private Vehicle vehicle;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private String brand;
}
