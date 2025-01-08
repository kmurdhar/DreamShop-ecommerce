package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.model.UserImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    List<UserImage> findByUserId(Long id);
}
