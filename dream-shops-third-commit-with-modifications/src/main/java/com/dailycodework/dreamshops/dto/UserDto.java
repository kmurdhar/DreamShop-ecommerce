package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.Order;
import lombok.Data;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private MultipartFile  yourPhoto;
    private MultipartFile  shopPhoto;
    private String shopName;  
    private List<OrderDto> orders;
    private CartDto cart;
}
