package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.model.ShopImage;
import com.dailycodework.dreamshops.model.UserImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopImageRepository extends JpaRepository<ShopImage, Long> {
    List<ShopImage> findByUserId(Long id);
}
