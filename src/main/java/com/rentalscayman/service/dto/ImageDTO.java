package com.rentalscayman.service.dto;

import javax.annotation.Nullable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {
    @Nullable
    private Long id;

    private String img;

    private String name;

    private String imgContentType;

    @Nullable
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }
}
