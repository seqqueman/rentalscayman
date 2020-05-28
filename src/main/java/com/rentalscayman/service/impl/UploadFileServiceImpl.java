package com.rentalscayman.service.impl;

import com.rentalscayman.service.IUploadFileService;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
    private static final String UPLOADS = "uploads";

    @Override
    public Resource loadImage(String photoName) throws MalformedURLException {
        Path rutaArchivo = getPath(photoName);
        Resource recurso = null;

        recurso = new UrlResource(rutaArchivo.toUri());

        if (!recurso.exists() && !recurso.isReadable()) {
            rutaArchivo = Paths.get("src/main/resources/static/images").resolve("generic.png").toAbsolutePath();
            recurso = new UrlResource(rutaArchivo.toUri());
        }
        return recurso;
    }

    @Override
    public String copyImage(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
        Path rutaArchivo = getPath(nombreArchivo);

        Files.copy(archivo.getInputStream(), rutaArchivo);
        return nombreArchivo;
    }

    @Override
    public boolean deleteImage(String photoName) {
        if (null != photoName && photoName.length() > 0) {
            Path rutaFotoAnterior = getPath(photoName);
            File ficheroFotoAnterior = rutaFotoAnterior.toFile();
            if (ficheroFotoAnterior.exists() && ficheroFotoAnterior.canRead()) {
                ficheroFotoAnterior.delete();
                return true;
            }
        }

        return false;
    }

    @Override
    public Path getPath(String photoName) {
        return Paths.get(UPLOADS).resolve(photoName).toAbsolutePath();
    }
}
