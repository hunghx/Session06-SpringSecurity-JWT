package ra.academy.config;

import com.google.cloud.storage.Storage;
import org.springframework.context.annotation.Bean;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class StorageConfig {
    @Bean
    public Storage storage() throws IOException {
        InputStream inputStream = new ClassPathResource("firebase-config.json").getInputStream();
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase-config.json");
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build()
                .getService();
    }
}
