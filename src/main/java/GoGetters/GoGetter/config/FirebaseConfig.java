package GoGetters.GoGetter.config;

//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class FirebaseConfig {
//
////    @Bean
////    public FirebaseApp firebaseApp() throws IOException {
////        log.info("Initializing Firebase.");
////        FileInputStream MemberIdnt =
////                new FileInputStream("./firebase.json");
////
////        FirebaseOptions options =  FirebaseOptions.builder()
////                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
////                .build();
////
////        FirebaseApp app = FirebaseApp.initializeApp(options);
////        log.info("FirebaseApp initialized" + app.getName());
////        return app;
////    }
//
//
//    @Bean
//    public FirebaseAuth firebaseAuth() throws IOException {
//        FileInputStream serviceAccount =
//                new FileInputStream("serviceAccountKey.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//        FirebaseApp.initializeApp(options);
//        return FirebaseAuth.getInstance(FirebaseApp.getInstance());
//    }
//}