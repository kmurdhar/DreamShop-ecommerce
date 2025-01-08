package com.dailycodework.dreamshops.service.image;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.model.ShopImage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IShopImageService {
    ShopImage getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long userId, List<MultipartFile> files);
    void updateImage(MultipartFile file,  Long imageId);
}
