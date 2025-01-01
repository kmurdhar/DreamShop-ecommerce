package com.dailycodework.dreamshops.dto;


import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.model.Vehicle;
import com.dailycodework.dreamshops.model.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private Vehicle vehicle;
    private List<ImageDto> images;
}
