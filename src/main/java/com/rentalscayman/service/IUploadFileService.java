package com.rentalscayman.service;

import com.google.cloud.storage.Blob;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
    public Resource loadImage(String photoName) throws MalformedURLException;

    public Blob copyImage(MultipartFile file) throws IOException, Exception;

    public boolean deleteImage(String photoName);

    public Path getPath(String photoName);
}
