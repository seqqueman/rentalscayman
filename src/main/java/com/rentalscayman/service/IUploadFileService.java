package com.rentalscayman.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
    public Resource loadImage(String photoName) throws MalformedURLException;

    public String copyImage(MultipartFile file) throws IOException;

    public boolean deleteImage(String photoName);

    public Path getPath(String photoName);
}
