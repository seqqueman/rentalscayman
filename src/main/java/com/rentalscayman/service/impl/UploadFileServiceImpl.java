package com.rentalscayman.service.impl;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import com.rentalscayman.service.FirebaseInitialize;
import com.rentalscayman.service.IUploadFileService;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {
    private static final String UPLOADS = "uploads";

    private String[] extensions = { "jpg", "jpeg", "png", "bmp" };

    @Autowired
    private FirebaseInitialize fbStorage;

    @Value("${application.gcpmine.bucket-name}")
    private String bucketName;

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
    public Blob copyImage(MultipartFile archivo) throws Exception {
        if (FilenameUtils.isExtension(archivo.getOriginalFilename().toLowerCase(), extensions)) {
            String nombreArchivo =
                UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "_" + archivo.getOriginalFilename().replace(" ", "");
            //        Map<String, String> metadata = new HashMap<String, String>();
            //        metadata.put("firebaseStorageDownloadTokens", token);
            //        Path rutaArchivo = getPath(nombreArchivo);
            //
            //        Files.copy(archivo.getInputStream(), rutaArchivo);

            String contentType = "image/" + FilenameUtils.getExtension(archivo.getOriginalFilename()).toLowerCase();

            Bucket bucket = StorageClient.getInstance().bucket(bucketName);
            // bucket.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            Blob myblob = bucket.create(nombreArchivo, archivo.getInputStream(), contentType);
            //			URL ur = myblob.signUrl(500, TimeUnit.DAYS, SignUrlOption.withPathStyle());
            myblob.updateAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            //       myblob.toBuilder().setMetadata(metadata);
            //			System.out.println(ur.toString());

            return myblob;
        }
        throw new Exception("The file must be a image");
    }

    //    public static void makeObjectPublic(String projectId, String bucketName, String objectName) {
    //        // String projectId = "your-project-id";
    //        // String bucketName = "your-bucket-name";
    //        // String objectName = "your-object-name";
    //        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    //        BlobId blobId = BlobId.of(bucketName, objectName);
    //        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
    //
    //        System.out.println(
    //            "Object " + objectName + " in bucket " + bucketName + " was made publicly readable");
    //      }

    @Override
    public boolean deleteImage(String photoName) {
        if (null != photoName && photoName.length() > 0) {
            //            Path rutaFotoAnterior = getPath(photoName);
            //            File ficheroFotoAnterior = rutaFotoAnterior.toFile();
            //            if (ficheroFotoAnterior.exists() && ficheroFotoAnterior.canRead()) {
            //                ficheroFotoAnterior.delete();
            //                return true;
            //            }
            Storage storage = StorageOptions.getDefaultInstance().getService();
            BlobId blobId = BlobId.of(bucketName, photoName);
            return storage.delete(blobId);
        }

        return false;
    }

    @Override
    public Path getPath(String photoName) {
        return Paths.get(UPLOADS).resolve(photoName).toAbsolutePath();
    }
}
