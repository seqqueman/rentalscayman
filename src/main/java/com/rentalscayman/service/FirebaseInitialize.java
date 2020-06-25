package com.rentalscayman.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FirebaseInitialize {
    @Value("${application.gcpmine.bucket-name}")
    private String bucketName;

    @PostConstruct
    private void initialize() throws IOException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./rentalscayman.json");

        //		FileInputStream serviceAccount =
        //	            new FileInputStream("src/main/resources/rentalscayman.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket(bucketName)
            .setDatabaseUrl("https://rentalscayman-b4342.firebaseio.com")
            .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public Firestore getFirebase() {
        return FirestoreClient.getFirestore();
    }
}
